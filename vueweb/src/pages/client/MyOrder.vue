<template>
  <div class="MyOrder">
    <ul class="tagList">
      <li :class="{selected:curIndex===index}" v-for="(item,index) in tagList" :key="'tag'+index" @click="changeIndex(index)">
        {{item}}
      </li>
    </ul>
    <div class="orderBox">
      <div class="orderTableHeader">
        <span>商品</span>
        <span>单价</span>
        <span>数量</span>
        <span>实付款</span>
        <span>交易操作</span>
      </div>
      <ul class="orderList">
        <li v-for="(item,index) in orderList" :key="'order'+item.order_id">
          <div class="orderHeader">
            <span class="orderTime">{{item.createtime}}</span>
            <span class="orderId">{{'订单号：'+item.order_id}}</span>
            <span class="state">{{tagList[item.status]}}</span>
            <!--<span class="deleteBtn" @click="deleteOrder(item.order_id)"><i class="iconfont icon-close" /></span>-->
          </div>
          <div class="orderDetail">
            <img :src="item.url" alt="商品图片" />
            <div class="goodsName">
              <p @click="nav_To('/mall/goods/'+item.product_id)">{{item.product_name}}</p>
              <span>{{item.specification_name}}</span>
              <span>{{item.size}}</span>
            </div>
            <span class="unitPrice">{{'￥'+item.price}}</span>
            <span class="num">{{item.quantity}}</span>
            <span class="amount">{{'￥'+item.totalprice}}</span>
            <button style="position:absolute;right:90px;bottom:55px;width:70px;height:30px;border-radius: 3px;background-color:#b4a078;color:white;border:none" v-if="item.status===1" @click="navTo('/mall/payment',item)">确认付款</button>
            <button style="position:absolute;right:90px;bottom:15px;width:70px;height:30px;border-radius: 3px;background-color:#B5B5B5;color:white;border:none" v-if="item.status===1" @click="cancelOrder(item.order_id)">取消订单</button>

            <button v-else-if="item.status===3" @click="confirmReceive(item.order_id)">确认收货</button>
            <button v-else-if="item.status===4 && !item.hasComment" @click="showPopup(item.order_id,item.product_id,item.specification_id)">评价</button>
            <span class="hasComment" v-else-if="item.status===4 && item.hasComment">已评价</span>
          </div>
        </li>
      </ul>
    </div>
    <Popup title="商品评价" @popupClose="closePopup" v-show="popupShow">
      <div class="popupContent" slot="popupContent">
        <div class="scoreBox">
          <span class="tips">评分：</span>
          <i
            class="iconfont icon-collection_fill"
            v-for="(item,index) in 5"
            :key="'star'+index"
            :style="{color:(index+1)<=curStar?'#f9bd4f':'white'}"
            @mouseover="setCurStar(index+1)"
            @mouseout="setCurStar(0)"
            @click="confirmStar(index+1)"
          />
        </div>
        <textarea v-model="comment" cols="30" rows="10" placeholder="请输入评论内容"></textarea>
        <button @click="sendComment">发表</button>
      </div>
    </Popup>
  </div>
</template>

<script>
import { mapState } from 'vuex';
import {getOrderByStatus,deleteOrder,updateOrdersStatus,sendComment,updateUserScore} from '../../api/client';
import Popup from '../../components/Popup';

export default {
  name: 'MyOrder',
  components:{
    Popup
  },
  computed:{
    ...mapState([
      'clientToken',
      'clientId'
    ]),
  },
  data () {
    return {
      tagList:['全部订单','待付款','待发货','已发货','已完成','已取消'],
      curIndex:0,
      orderList:[],
      popupShow:false,
      curOrderId:'',
      curCommentGoodsId:'',
      curCommentGoodsDetailId:'',
      curStar:0,
      hasClickStar:false,
      comment:'',
    }
  },

  methods:{
    changeIndex(i){
      this.curIndex = i;
      this.getOrderByStatus(this.curIndex);
    },
    nav_To(route){
      this.$router.push(route);
    },
    navTo(route,item){
      // this.$router.push(route);
      this.$router.push({
        path: route,
        query: {
          orderIds: [item.order_id],
          totalprice:item.totalprice
        }
      })
    },
    getOrderByStatus(status){
      const res = getOrderByStatus(this.clientId,status,this.clientToken);
      res
      .then((data)=>{
        console.log(data);
        this.orderList=data
      })
      .catch((e)=>{
        alert(e);
      })
    },
    // deleteOrder(orderId){
    //   const res = deleteOrder(orderId);
    //   res
    //   .then(()=>{
    //     alert('删除订单成功！');
    //     this.orderList.map((item,index)=>{
    //       if(item.id===orderId){
    //         this.orderList.splice(index,1);
    //       }
    //     })
    //   })
    //   .catch((e)=>{
    //     alert(e);
    //   })
    // },

    confirmPay(orderId){
      const res = updateOrdersStatus({
        id:orderId,
        addressId:null,
        specificationId:null,
        userId:null,
        status:2,
        quantity:null,
        preferentialprice:null,
        totalprice:null,
        createtime:null
      });
      res
      .then(()=>{
        alert('支付成功！');
        this.getOrderByStatus(this.curIndex);
        // this.orderList.map((item,index)=>{
        //   if(item.order_id===orderId){
        //     item.status = 2;
        //   }
        // })
      })
      .catch((e)=>{
        alert(e);
      })
    },
    cancelOrder(orderId){
      const res = updateOrdersStatus({
        id:orderId,
        addressId:null,
        specificationId:null,
        userId:null,
        status:5,
        quantity:null,
        preferentialprice:null,
        totalprice:null,
        createtime:null
      });
      res
        .then(()=>{
          alert('订单已取消！');
          this.getOrderByStatus(this.curIndex);
          // this.orderList.map((item,index)=>{
          //   if(item.order_id===orderId){
          //     item.status = 5;
          //   }
          // })
        })
        .catch((e)=>{
          alert(e);
        })
    },
    confirmReceive(orderId){
      const res = updateOrdersStatus({
        id:orderId,
        addressId:null,
        specificationId:null,
        userId:null,
        status:4,
        quantity:null,
        preferentialprice:null,
        totalprice:null,
        createtime:null
      });
      res
      .then(()=>{
        alert('确认收货成功！');
        this.getOrderByStatus(this.curIndex);
        // this.orderList.map((item,index)=>{
        //   if(item.order_id===orderId){
        //     item.status = 4;
        //     updateUserScore(this.clientId,
        //      parseInt(item.totalprice));
        //   }
        // })
      })
      .catch((e)=>{
        alert(e);
      })
    },

    closePopup(){
      this.popupShow = false;
      this.curCommentGoodsId = '';
      this.curOrderId = '';
      this.curCommentGoodsDetailId = '';
      this.hasClickStar = false;
      this.curStar = 0;
      this.comment = '';
    },
    showPopup(orderId,productId,specificationId){
      this.curCommentGoodsId = productId;
      this.curOrderId = orderId;
      this.curCommentGoodsDetailId = specificationId;
      this.popupShow = true;
    },
    sendComment(){
      if(this.curStar<=0 || this.comment==''){
        alert('评分和评价不能为空！');
        return;
      }
      const res = sendComment({
        // token:this.clientToken,
        id:null,
        userId:this.clientId,
        orderId:this.curOrderId,
        productId:this.curCommentGoodsId,
        specificationId:this.curCommentGoodsDetailId,
        content:this.comment,
        score:this.curStar*20,
        createtime:null
      });
      res
      .then(()=>{
        alert('评价成功！');
        this.getOrderByStatus(this.curIndex);
        // for(let order of this.orderList){
        //   if(order.id===this.curOrderId){
        //     order.hasComment = true;
        //   }
        // }
        this.closePopup();
      })
      .catch((e)=>{
        alert(e);
      })
    },
    setCurStar(star){
      if(this.hasClickStar){
        return;
      }
      this.curStar = star;
    },
    confirmStar(star){
      this.curStar = star;
      this.hasClickStar = true;
    }
  },

  mounted(){
    this.getOrderByStatus(0);
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.MyOrder{
  .tagList{
    li{
      text-align: center;
      display: inline-block;
      font-weight: bold;
      font-size: 18px;
      border-bottom: 2px solid @borderColor;
      cursor: pointer;
      padding: 10px 20px;
      position: relative;
      &:after{
        top:12px;
        right: 0;
        position:absolute;
        content:'';
        width: 1px;
        height: 15px;
        background-color: @borderColor;
      }
      &:last-child{
        &:after{
          display:none;
        }
      }
    }
    .selected{
      color:@thirdColor;
      border-bottom: 2px solid @thirdColor;
    }
  }
  .orderBox{
    width: 100%;
    margin-top: 20px;
    .orderTableHeader{
      width: 100%;
      height: 40px;
      background-color: #f5f5f5;
      border: 1px solid @borderColor;
      color: @fontDefaultColor;
      font-size: 14px;
      line-height: 40px;
      span{
        display: inline-block;
        width: 14%;
        &:first-child{
          width:40%;
          text-align: center;
        }
      }
    }
    .orderList{
      width: 100%;
      li{
        border: 1px solid @borderColor;
        font-size: 13px;
        margin-top: 10px;
        .orderHeader{
          background-color: #f1f1f1;
          height: 40px;
          line-height: 40px;
          padding: 0 5px;
          .orderTime{
            font-weight: 600;
          }
          .orderId,.state{
            margin-left: 10px;
          }
          .deleteBtn{
            float: right;
            cursor: pointer;
            i{

            }
          }
        }
        .orderDetail{
          width: 100%;
          padding: 10px;
          position: relative;
          overflow: hidden;
          img{
            width: 84px;
            height: 84px;
            display: inline-block;
          }
          .goodsName{
            display: inline-block;
            margin-left: 5px;
            width: 250px;
            vertical-align: top;
            p{
              cursor: pointer;
              line-height: 20px;
              &:hover{
                text-decoration:underline;
              }
            }
            span{
              color:@fontDefaultColor;
              display: block;
              margin-top: 10px;
            }
          }
          .unitPrice,.amount{
            display: inline-block;
            vertical-align: top;
            width: 14.5%;
            height: 85px;
            line-height: 85px;
            padding-left:35px
          }
          .num{
            display: inline-block;
            vertical-align: top;
            width: 14.5%;
            height: 85px;
            line-height: 85px;
            padding-left:47px
          }
          .hasComment{
            display: inline-block;
            vertical-align: top;
            width: 15%;
            height: 85px;
            line-height: 85px;
            font-size:15px;
            /*text-align: center;*/
            padding-left:30px
          }
          button{
            position: absolute;
            right: 90px;
            bottom: 40px;
            width: 70px;
            height: 30px;
            border-radius: 3px;
            background-color: @thirdColor;
            color:white;
            border: none;
          }
        }
      }
    }
  }
  .popupContent{
    padding: 10px;
    .scoreBox{
      width: 100%;
      height: 50px;
      position: relative;
      line-height: 50px;
      text-align: left;
      .tips{
        font-size: 15px;
        vertical-align: middle;
        display: inline-block;
      }
      i{
        cursor: pointer;
        vertical-align: middle;
        display: inline-block;
        font-size: 25px;
        margin-right: 5px;
        -webkit-text-stroke: 1px #f9bd4f;
      }
    }
    textarea{
      width: 400px;
      height: 80px;
      padding: 5px;
      border:1px solid @borderColor;
      display: block;
      margin-top: 10px;
    }
    button{
      display: block;
      margin: 10px auto;
      width: 70px;
      height: 30px;
      border-radius: 3px;
      background-color: #313541;
      color:white;
      border: none;
    }
  }
}
</style>
