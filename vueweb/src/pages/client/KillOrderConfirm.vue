<template>
  <div class="confirmOrder">
    <!-- 头部 -->
    <div class="top-header">
      <div class="cart-header">
        <div class="logo">
          <router-link to="/">
            <img src="../../assets/img/market.jpg" alt />
          </router-link>
        </div>
        <div class="cart-header-content">
          <p>确认订单</p>
        </div>

      </div>
    </div>
    <!-- 头部END -->

    <!-- 主要内容容器 -->
    <div class="confirm-content">
      <!-- 选择地址 -->
      <div class="section-address">
        <p class="title">收货地址</p>
        <div class="address-body">
          <ul>
            <router-link to>
              <li
                  :class="item.id == confirmAddress ? 'in-section' : ''"
                  v-for="item in newestaddress"
                  :key="item.id"
                  @click="selectAddress(item)"
                  :style="item.isDefault?'background-color:#FDF5E6':''"
              >
                <p style="text-align: center">
                  <span>{{item.receivername}}</span>
                  <span>{{item.receiverphone}}</span>
                  <span>{{item.receiveraddress}}</span>
                </p>
              </li>
            </router-link>
            <li class="add-address" @click="addAddressing">
              <i class="el-icon-circle-plus-outline"></i>
              <!--<p>添加新地址</p>-->
            </li>
          </ul>
        </div>
      </div>
      <!-- 选择地址END -->

      <!-- 商品及优惠券 -->
      <div class="section-goods">
        <p class="title">商品及优惠券</p>
        <div class="goods-list">
          <ul>
            <li v-for="item in checkGoods" :key="item.id">
              <img :src="item.img" />
              <span class="pro-name">{{item.product_name}}</span>
              <span class="pro-color">{{item.specification_name}}</span>
              <span class="pro-size">{{item.size<=0?"---":item.size}}</span>
              <span class="pro-price" style="color:#be4141" v-if="item.preferentiallevel<100&&item.preferentiallevel>0">{{item.price+'元'+'('+'折后价'+(item.price*item.preferentiallevel/100).toFixed(2)+'元)'}}</span>
              <span class="pro-price" v-else>{{item.price+'元'}}</span>
              <span class="pro-num">x {{item.num}}</span>
              <!--<span class="pro-status"></span>-->
              <span class="pro-total">{{item.price * item.num}}元</span>
            </li>
          </ul>
        </div>
      </div>
      <!-- 商品及优惠券END -->

      <!-- 配送方式 -->
      <div class="section-shipment">
        <p class="title">配送方式</p>
        <p class="shipment">包邮</p>
      </div>
      <!-- 配送方式END -->

      <!-- 发票 -->
      <div class="section-invoice">
        <p class="title">发票</p>
        <p class="invoice">电子发票</p>
        <p class="invoice">个人</p>
        <p class="invoice">商品明细</p>
      </div >
      <!-- 发票END -->
      <div class="section-invoice">
        <p class="title">提示</p>
        <p class="invoice">你是尊敬的{{member[level-1]}}会员,享受{{discount[level-1]}}折优惠</p>
      </div>

      <!-- 结算列表 -->
      <div class="section-count">
        <div class="money-box">
          <ul>
            <li>
              <span class="title">商品总价：</span>
              <span class="value">{{parseFloat(totalprice)+parseFloat(preferentialprice)}}元</span>
            </li>
            <li>
              <span class="title">活动优惠：</span>
              <span class="value">-{{preferentialprice}}元</span>
            </li>
            <li>
              <span class="title">会员优惠：</span>
              <span class="value">-{{(totalprice*(1-membership)).toFixed(2)}}元</span>
            </li>
            <li>
              <span class="title">运费：</span>
              <span class="value">0元</span>
            </li>
            <li class="total">
              <span class="title">应付总额：</span>
              <span class="value">
                <span class="total-price">{{(totalprice*membership).toFixed(2)}}</span>元
              </span>
            </li>
          </ul>
        </div>
      </div>
      <!-- 结算列表END -->

      <!-- 结算导航 -->
      <div class="section-bar">
        <div class="btn">
          <button @click="goBack" class="btn-base btn-return">取消</button>
          <button @click="generateOrders" class="btn-base btn-primary">结算</button>
        </div>
      </div>
      <!-- 结算导航END -->
    </div>
    <!--主要内容容器END-->
    <!--新建收货地址弹出框-->
    <el-dialog title="新建收货地址" :visible.sync="addVisible" width="30%">
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="姓名">
          <el-input v-model="form.receivername"></el-input>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model.number="form.receiverphone"></el-input>
        </el-form-item>
        <el-form-item label="地区">
          <v-region type="column" v-model="selected"@values="values"></v-region>
        </el-form-item>
        <el-form-item label="详细地址">
          <el-input type="textarea" rows="5" v-model="form.receiveraddress"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addAddress">确 定</el-button>
        <el-button @click="addVisible = false">取 消</el-button>
      </span>
    </el-dialog>
    <!--新建收货地址弹出框END-->
  </div>
</template>
<script>
import { mapState } from 'vuex';
import {
  getAddresses,
  generateKillOrders,
  addAddress,
  removeFromCart,
  updateSpecificationStock,
  updateActivityRemain,
  getUserData,
  falseToKill
} from "../../api/client";

export default {
  name: 'KillOrderConfirm',
  computed:{
    membership(){
      var temp=1;
      if(this.level==1)
        temp=0.99
      else if(this.level==2)
        temp=0.98
      else if(this.level==3)
        temp=0.95
      else if(this.level==4)
        temp=0.92
      else
        temp=0.9
      return temp;
    },
    preferentialprice(){
      var temp=0;
      for(var i=0;i<this.checkGoods.length;i++){
        temp+=(100-this.checkGoods[i].preferentiallevel)/100*this.checkGoods[i].price*this.checkGoods[i].num;
      }
      return temp.toFixed(2);
    },
    newestaddress(){
      return this.address;
    },
    ...mapState([
      'clientId',
      'ordersToConfirm',
      'clientToken'
    ]),
    totalprice(){
      var tempTotalPrice=0;
      for(var i=0;i<this.checkGoods.length;i++){
        tempTotalPrice+=this.checkGoods[i].preferentiallevel/100*this.checkGoods[i].price*this.checkGoods[i].num;
      }
      return tempTotalPrice.toFixed(2);
    }
  },
  data() {
    return {
      member:["普通","白银","黄金","白金","钻石"],
      discount:["九九","九八","九五","九二","九"],
      product_id:null,
      cartList:[],
      cartIdList:[],
      selected:{},
      // checknum:0,
      checkGoods:"",
      // 选择的地址id
      confirmAddress: 0,
      // 地址列表
      address: [],
      addVisible: false,
      level:1,
      form: {
        id: '',
        userId: '',
        receivername: '',
        receiverphone: '',
        receiverprovince: '',
        receivercity: '',
        receiverdistrict: '',
        receiveraddress: '',
        is_default:0,
      },

    }
  },
  methods: {
    values(data){
      this.form.receiverprovince=data.province.value;
      this.form.receivercity=data.city.value;
      this.form.receiverdistrict=data.area.value;
    },
    addAddressing(){
      this.emptySelected();
      this.emptyform();
      this.addVisible=true;
    },
    emptySelected(){
      this.selected.province=null;
      this.selected.city=null;
      this.selected.area=null;
    },
    emptyform(){
      this.form.receivername=null;
      this.form.receiverphone=null;
      this.form.receiveraddress=null;
      this.form.receivercity=null;
      this.form.receiverprovince=null;
      this.form.receiverdistrict=null;
    },
    // ...mapActions(['deleteShoppingCart']),
    selectAddress(item) {
      this.confirmAddress = item.id
    },
    goBack(){
      var con;
      con=confirm( "您将失去刚抢到的商品，确定取消吗?" ); //在页面上弹出对话框
      if (con== true ){
        var quest={
          goodsId: this.checkGoods[0].goods_id,
          userId: this.clientId
        };
        falseToKill(JSON.stringify(quest)).then((data)=>{
          alert("取消订单成功！");
        });
        this.$router.go(-1);
      }
    },
    getAddresses() {
      getAddresses(this.clientId,this.clientToken)
          .then(res => {
            this.address = res;
            for(var i=0;i<res.length;i++){
              if(res[i].isDefault==true)
                this.confirmAddress=res[i].id;
            }
          })
          .catch(err => {
            alert('获取收货地址失败', err)
          })

    },
    generateOrders() {
      if (this.confirmAddress === 0) {
        alert('请选择收货地址', null);
        return
      }
      var ordersList = [];
      var stockList=[];
      var remainList=[];
      var pay=this.totalprice*this.membership;
      ordersList.push({
        id:null,
        addressId:this.confirmAddress,
        specificationId:this.checkGoods[0].specification_id,
        userId:this.clientId,
        status:1,
        quantity:this.checkGoods[0].num,
        preferentialprice:parseFloat(this.preferentialprice+this.totalprice*(1-this.membership)).toFixed(2),
        totalprice:parseFloat(this.totalprice*this.membership).toFixed(2),
        createtime:null,
        goodsId:this.checkGoods[0].goods_id
      });
      console.log(ordersList);
      const res = generateKillOrders(ordersList);
      res
          .then((data)=>{
            console.log("data1",data)
            const orderId=data;
            /*if(this.cartIdList.length!=undefined)
              removeFromCart({"data":this.cartIdList}).then(()=>{
                console.log("data2",data)
                this.$router.push({
                  path: '/mall/payment',
                  query: {
                    orderIds:data,
                    totalprice:pay
                  }
                })
              })*/
            this.$router.push({
              path: '/mall/payment',
              query: {
                orderIds: data,
                totalprice:pay
              }
            })
          })
          .catch((e)=>{
            alert('下单失败',e);
          })
    },
    addAddress() {
      if(this.form.receiverdistrict==null){
        this.notifyError('请选择地区', '请选择地区')
        return
      }
      this.form.userId = this.$store.getters.clientId
      addAddress(this.qs.stringify(this.form))
          .then(res => {
            this.address = res
            this.addVisible = false
            this.notifySucceed('新建收货地址成功');
          })
          .catch(err => {
            this.notifyError('新建收货地址失败', err)
          })

    }
  },
  mounted(){
    this.getAddresses();
    //初始化数据
    this.checkGoods=JSON.parse(this.ordersToConfirm);
    console.log("初始化数据："+this.checkGoods);
    this.cartIdList=JSON.parse(this.$route.query.data);
    getUserData(this.clientId).then((data)=>{
      this.level=data.level;
    }).catch((e)=>{
      alert(e);
    })
  }
}
</script>
<style scoped>
.confirmOrder {
  background-color: #f5f5f5;
  padding-bottom: 20px;
}
/* 购物车头部CSS */
.confirmOrder .top-header {
  width: 100%;
  background-color: #ffffff;
  margin-bottom: 30px;
  border-bottom: 2px solid #ff6700;
}
.confirmOrder .top-header .cart-header {
  display: flex;
  height: 100px;
  background-color: #ffffff;
  margin-bottom: 20px;
  width: 1225px;
  margin: 0 auto;
  position: relative;
}
.confirmOrder .top-header .cart-header .logo {
  height: 60px;
  width: 100px;
  margin-top: 22px;
  margin-right: 20px;
}
.confirmOrder .top-header .cart-header .logo img {
  height: 60px;
}

.confirmOrder .top-header .cart-header p {
  margin-top: 23px;
  font-size: 28px;
  line-height: 58px;
  float: left;
  font-weight: normal;
  color: #424242;
}

.confirmOrder .top-header .cart-header .cart-header-right {
  display: flex;
  position: absolute;
  right: 0px;
  float: right;
}

.confirmOrder .top-header .cart-header .cart-header-select {
  margin-top: 40px;
}

.confirmOrder .top-header .cart-header .cart-header-select .href {
  font-size: 13px;
  color: #757575;
}
.confirmOrder .top-header .cart-header .cart-header-select .href:hover {
  color: #ff6700;
}
.dropdown-menu:hover {
  color: #ff6700;
  background-color: #f5f5f5;
}
.confirmOrder .top-header .cart-header .cart-header-order {
  margin-top: 40px;
}
.confirmOrder .top-header .cart-header .cart-header-order span {
  color: #c9c7c7;
  margin-left: 20px;
  margin-right: 20px;
}
.confirmOrder .top-header .cart-header .cart-header-order .href {
  font-size: 13px;
  color: #757575;
}
.confirmOrder .top-header .cart-header .cart-header-order .href:hover {
  color: #ff6700;
}

/* 购物车头部CSS END */

/* 主要内容容器CSS */
.confirmOrder .confirm-content {
  width: 1225px;
  margin: 0 auto;
  padding: 48px 0 0;
  background-color: #fff;
}

/* 选择地址CSS */
.confirmOrder .confirm-content .section-address {
  margin: 0 48px;
  overflow: hidden;
}
.confirmOrder .confirm-content .section-address .title {
  color: #333;
  font-size: 18px;
  line-height: 20px;
  margin-bottom: 20px;
}
.confirmOrder .confirm-content .address-body li {
  /*float: left;*/
  color: #333;
  /*width: 210px;*/
  /*height: 178px;*/
  width: 70%;
  height: 40px;
  border: 1px solid #e0e0e0;
  padding: 15px 24px 0;
  /*margin-right: 17px;*/
  /*margin-bottom: 24px;*/
  margin: 0px auto 24px auto;
}
.confirmOrder .confirm-content .address-body .in-section {
  border: 1px solid #ff6700;
}
.confirmOrder .confirm-content .address-body li h2 {
  font-size: 18px;
  font-weight: normal;
  line-height: 30px;
  margin-bottom: 10px;
}
.confirmOrder .confirm-content .address-body li p {
  font-size: 14px;
  color: black;
}
.confirmOrder .confirm-content .address-body li .address {
  padding: 10px 0;
  max-width: 180px;
  max-height: 88px;
  line-height: 22px;
  overflow: hidden;
}
.confirmOrder .confirm-content .address-body .add-address {
  text-align: center;
  line-height: 30px;
}
.confirmOrder .confirm-content .address-body .add-address i {
  position:relative;
  top:-7px;
  font-size: 25px;
  /*padding-top: 50px;*/
  text-align: center;
}
/* 选择地址CSS END */

/* 商品及优惠券CSS */
.confirmOrder .confirm-content .section-goods {
  margin: 0 48px;
}
.confirmOrder .confirm-content .section-goods p.title {
  color: #333;
  font-size: 18px;
  line-height: 40px;
}
.confirmOrder .confirm-content .section-goods .goods-list {
  padding: 5px 0;
  border-top: 1px solid #e0e0e0;
  border-bottom: 1px solid #e0e0e0;
}
.confirmOrder .confirm-content .section-goods .goods-list li {
  padding: 10px 0;
  color: #424242;
  overflow: hidden;
}
.confirmOrder .confirm-content .section-goods .goods-list li img {
  float: left;
  width: 30px;
  height: 30px;
  margin-right: 10px;
}
.confirmOrder .confirm-content .section-goods .goods-list li .pro-name {
  float: left;
  width: 350px;
  line-height: 30px;
  text-align:center;
}
.confirmOrder .confirm-content .section-goods .goods-list li .pro-color {
  float: left;
  width: 150px;
  line-height: 30px;
  text-align:center;
}
.confirmOrder .confirm-content .section-goods .goods-list li .pro-size {
  float: left;
  width: 150px;
  line-height: 30px;
  text-align:center;
}
.confirmOrder .confirm-content .section-goods .goods-list li .pro-price {
  float: left;
  width: 120px;
  line-height: 30px;
  text-align:center;
}
.confirmOrder .confirm-content .section-goods .goods-list li .pro-num {
  float: left;
  width: 120px;
  text-align: center;
  line-height: 30px;
}
/*.confirmOrder .confirm-content .section-goods .goods-list li .pro-status {*/
/*float: left;*/
/*width: 99px;*/
/*height: 30px;*/
/*text-align: center;*/
/*line-height: 30px;*/
/*}*/
.confirmOrder .confirm-content .section-goods .goods-list li .pro-total {
  float: left;
  width: 190px;
  text-align: center;
  color: #ff6700;
  line-height: 30px;
}
/* 商品及优惠券CSS END */

/* 配送方式CSS */
.confirmOrder .confirm-content .section-shipment {
  margin: 0 48px;
  padding: 25px 0;
  border-bottom: 1px solid #e0e0e0;
  overflow: hidden;
}
.confirmOrder .confirm-content .section-shipment .title {
  float: left;
  width: 150px;
  color: #333;
  font-size: 18px;
  line-height: 38px;
}
.confirmOrder .confirm-content .section-shipment .shipment {
  float: left;
  line-height: 38px;
  font-size: 14px;
  color: #ff6700;
}
/* 配送方式CSS END */

/* 发票CSS */
.confirmOrder .confirm-content .section-invoice {
  margin: 0 48px;
  padding: 25px 0;
  border-bottom: 1px solid #e0e0e0;
  overflow: hidden;
}
.confirmOrder .confirm-content .section-invoice .title {
  float: left;
  width: 150px;
  color: #333;
  font-size: 18px;
  line-height: 38px;
}
.confirmOrder .confirm-content .section-invoice .invoice {
  float: left;
  line-height: 38px;
  font-size: 14px;
  margin-right: 20px;
  color: #ff6700;
}
/* 发票CSS END */

/* 结算列表CSS */
.confirmOrder .confirm-content .section-count {
  margin: 0 48px;
  padding: 20px 0;
  overflow: hidden;
}
.confirmOrder .confirm-content .section-count .money-box {
  float: right;
  text-align: right;
}
.confirmOrder .confirm-content .section-count .money-box .title {
  float: left;
  width: 126px;
  height: 30px;
  display: block;
  line-height: 30px;
  color: #757575;
}
.confirmOrder .confirm-content .section-count .money-box .value {
  float: left;
  min-width: 105px;
  height: 30px;
  display: block;
  line-height: 30px;
  color: #ff6700;
}
.confirmOrder .confirm-content .section-count .money-box .total .title {
  padding-top: 15px;
}
.confirmOrder .confirm-content .section-count .money-box .total .value {
  padding-top: 10px;
}
.confirmOrder .confirm-content .section-count .money-box .total-price {
  font-size: 30px;
}
/* 结算列表CSS END */

/* 结算导航CSS */
.confirmOrder .confirm-content .section-bar {
  padding: 20px 48px;
  border-top: 2px solid #f5f5f5;
  overflow: hidden;
}
.confirmOrder .confirm-content .section-bar .btn {
  float: right;
}
.confirmOrder .confirm-content .section-bar .btn .btn-base {
  float: left;
  margin-left: 30px;
  width: 158px;
  height: 38px;
  border: 1px solid #b0b0b0;
  font-size: 14px;
  line-height: 38px;
  text-align: center;
}
.confirmOrder .confirm-content .section-bar .btn .btn-return {
  color: rgba(0, 0, 0, 0.27);
  border-color: rgba(0, 0, 0, 0.27);
  color: #757575;
}
.confirmOrder .confirm-content .section-bar .btn .btn-primary {
  background: #ff6700;
  border-color: #ff6700;
  color: #fff;
}
/* 结算导航CSS */

/* 主要内容容器CSS END */
</style>