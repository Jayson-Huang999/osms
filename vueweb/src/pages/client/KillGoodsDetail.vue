<template>
  <div class="GoodsDetail" v-loading="loading">
    <div class="content">
      <div class="goodsInfo">
        <img class="infoLeft" :src="goodsImg" alt="商品图片"/>
        <div class="infoRight">
          <div class="infoBox">
            <h3 class="name">{{ goodsName }}</h3>
          </div>
          <div class="infoBox">
            <p>{{ goodsDesc }}</p>
          </div>
          <div class="infoBox">
            <h3 class="price" v-if="activity">{{ '¥' + goodsPrice
            +'(折后价'+'¥'+(goodsPrice*preferentiallevel/100).toFixed(2)+')'}}</h3>
            <h3 v-if="activity" style="margin-top:15px;color:#8B3A3A"> 活动商品仅剩{{remain}}件</h3>
            <h3 class="price" v-else>
              {{ '¥' + goodsPrice}}
            </h3>
          </div>
          <div class="infoBox" style="margin-bottom: 0px">
            <span>规格：</span>
            <div style="margin-top:15px">
              <Radio v-for="(item,index) in specs" :key="item.id" v-model="temSpecId"
                     :initVal="specs[0].id"
                     radioName="spec" :radioVal="item.id" :index="index">
                <span class="tips" slot="tips">{{ item.name + '(' + item.size + ')' + ' 还剩' + item.stock + '件' }}</span>
              </Radio>
            </div>
          </div>
          <button @click="killGoods">立即抢购</button>
        </div>
      </div>
      <section class="msgBox leftContainer">
        <ul class="tagList">
          <li :class="{selected:curIndex===index}" v-for="(item,index) in tagList" :key="'tag'+index"
              @click="changeIndex(index)">
            {{ item }}
          </li>
        </ul>
        <div class="commentBody" v-if="curIndex===0">
          <div v-if="commentList.length>0">
            <div class="rateBox">
              <span>好评率</span>
              <span class="rate">{{ rate + '%' }}</span>
            </div>
            <ul class="commentList">
              <li v-for="(item,index) in commentList" :key="'comment'+index">
                <div class="userInfo">
                  <img :src="item.headimage"/>
                  <span>{{ item.nickname }}</span>
                </div>
                <div class="commentInfo">
                  <div class="starList">
                    <i
                        class="iconfont icon-collection_fill"
                        v-for="(star,index) in (parseInt(item.score/20))"
                        :key="item.id+''+index"
                    />
                  </div>
                  <p class="specName">{{ item.name }}</p>
                  <p class="comment">{{ item.content }}</p>
                  <p class="time">{{ item.createtime }}</p>
                </div>
              </li>
            </ul>
          </div>
          <div class="noComment" v-else>还没有评论~</div>
        </div>
        <div class="msgBody" v-else>
          <div class="inputBox">
            <textarea placeholder="请输入提问内容" v-model="askContent" cols="30" rows="10"></textarea>
            <button v-if="clientToken" @click="postAsk" :class="{ban:askContent.trim().length<=0}">提问
            </button>
            <div v-else class="banAsk">请先登录</div>
          </div>
          <ul class="msgList">
            <li v-for="(item,index) in msgList" :key="'msg'+item.id">
              <div class="ask">
                <span class="note">问</span>
                <span class="text">{{ item.content }}</span>
                <span class="tipsInfo">{{ item.asker + ' ' + item.time }}</span>
              </div>
              <div class="answer">
                <span class="note">答</span>
                <span class="text">{{ Object.keys(item.reply).length > 0 ? item.reply.content : '暂时没有回答' }}</span>
                <span class="tipsInfo">{{ Object.keys(item.reply).length > 0 ? item.reply.time : '' }}</span>
              </div>
            </li>
          </ul>
        </div>
      </section>
      <section class="typeGoods rightContainer">
        <div class="title">相似商品</div>
        <ul class="list">
          <GoodsItem
              v-for="(item,index) in filterList"
              :key="+item.id"
              :id="item.id"
              :url="item.url"
              :name="item.name"
              :minprice="item.minprice"
          />
        </ul>
      </section>
    </div>
  </div>
</template>

<script>
import {mapState} from 'vuex';
import {
  getKillGoodsInfo,
  getGoodsMsg,
  askGoodsMsg,
  killGoods,
  test,
  getComment,
  getGoodsList,
  hasStored,
  getActivityProducts,
  getKillGoodsStock,
  queryKillRes,
  falseToKill, getProductStock
} from '../../api/client';
import NumberInput from '../../components/NumberInput';
import Radio from '../../components/Radio';
import GoodsItem from '../../components/GoodsItem';
import OrderConfirm from './OrderConfirm';
import {mapMutations} from 'vuex'
import {convertDate} from '../../util/util'
import axios from "axios";
export default {
  name: 'KillGoodsDetail',
  components: {
    NumberInput,
    Radio,
    GoodsItem
  },
  computed: {
    ...mapState([
      'clientToken',
      'clientName',
      'clientId',
      'activityProducts'
    ]),
    id() {
      return this.$route.params.id;
    },
    goodsPrice() {
      let unitPrice = 0;
      this.specs.map((item, index) => {
        if (item.id === this.temSpecId) {
          unitPrice = Number(item.price);
        }
      })
      return (this.num * unitPrice);
    },
    temStockNum() {
      let stockNum = 0;
      this.specs.map((item, index) => {
        if (item.id === this.temSpecId) {
          stockNum = Number(item.stock);
        }
      })
      return stockNum;
    },
    filterList() {
      return this.goodsList.filter((item) => {
        return String(item.id) !== String(this.id);
      })
    }
  },
  data() {
    return {
      imgUrl: '/static/img/store.jpg',
      sellerId: '',
      goodsImg: '',
      goodsName: '',
      goodsDesc: '',
      specs: [],
      categoryId: '',
      temSpecId: 0,//选中的规格Id
      num: 1,
      msgList: [],
      askContent: '',
      tagList: ['评价', '商品问答'],
      curIndex: 0,
      rate: 0,
      commentList: [],
      goodsList: [],
      loading: false,
      timer: null,
      times:null,
      activity: false,
      preferentiallevel: 100,
      remain: 0,
      activity_id:null,//活动Id
      hasToken:false,
      toastShow:false
    }
  },

  methods: {
    ...mapMutations({
      setOrdersToConfirm: "SET_ORDERS_TO_CONFIRM",
      setActivityProducts: "SET_ACTIVITY_PRODUCTS",
    }),
    changeIndex(i) {
      this.curIndex = i;
    },
    store() {
      if (this.imgUrl === '/static/img/store.jpg') {
        this.imgUrl = '/static/img/stored.jpg'

        console.info(this.clientId, this.id)
        axios({
          method: "post",
          url: "/api/product/store",
          data: {
            userid: this.clientId,
            pid: this.id
          }
        }).then(res => {
        })
      } else {
        this.imgUrl = '/static/img/store.jpg'

        console.info(this.clientId, this.id)
        axios({
          method: "post",
          url: "/api/product/cancelStore",
          data: {
            userid: this.clientId,
            pid: this.id
          }
        }).then(res => {
        })
      }
    },
    getGoodsInfo(id) {
      //初始化数据
      const res = getKillGoodsInfo(id);
      res.then((data) => {
        this.goodsImg = data.specs[0].img;
        this.goodsName = data.name;
        this.goodsDesc = data.describ;

        this.specs = data.specs;
        this.categoryId = data.categoryId;
        this.temSpecId = data.specs[0].id;
        this.sellerId = data.sellerId;
        //根据活动信息设置秒杀时间
        getActivityProducts().then((data) => {
          var tempList = [];
          for (var i = 0; i < data.length; i++) {
            var starttime = new Date(data[i].starttime.replace(/-/g, '/'));
            var endtime = new Date(data[i].endtime.replace(/-/g, '/'));
            // console.log(starttime.getTime());
            // console.log(endtime.getTime());
            if (new Date().getTime() > starttime.getTime() && new Date().getTime() < endtime.getTime()&&data[i].remain>0) {
              tempList.push(data[i]);
            }
          }
          //
          this.setActivityProducts(tempList);
          for (var j = 0; j < this.activityProducts.length; j++) {
            if (id == this.activityProducts[j].product_id)
              if (new Date(this.activityProducts[j].starttime.replace(/-/g, '/')).getTime() <= new Date().getTime() && new Date(this.activityProducts[j].endtime.replace(/-/g, '/')).getTime() > new Date().getTime()) {
                this.preferentiallevel = this.activityProducts[j].preferentiallevel;
                this.remain = this.activityProducts[j].remain;
                this.activity = true;
                this.activity_id=this.activityProducts[j].activity_id;
                break;
              }
          }
          if (j == this.activityProducts.length) {
            this.preferentiallevel = 100;
            this.remain = 0;
            this.activity = false;
          }
        }).catch((e) => {
          alert(e);
        }),
            this.getTypeGoodsList(data.categoryId);
      }).catch((e) => {
        alert(e);
      })
    },

    getGoodsMsg(id) {
      const res = getGoodsMsg(id);
      res.then((data) => {
        console.log(data);
        this.msgList = data;
      }).catch((e) => {
        alert(e);
      })
    },


    postAsk() {
      if (this.askContent.trim().length <= 0) {
        return;
      }
      const res = askGoodsMsg({
        userId: this.clientId,
        productId: this.id,
        sellerId: this.sellerId,
        content: this.askContent,
        state: 1,
        createtime: new Date(),
        token:this.clientToken
      });
      res.then(() => {
        let time = new Date();
        this.msgList.unshift({
          id: 'new',
          content: this.askContent,
          state: 0,
          asker: this.clientName,
          // time: time.getMonth() + 1 + '-' + time.getDate() + '-',
          time: convertDate(time),
          reply: {}
        });
        this.askContent = '';
      }).catch((e) => {
        alert(e);
      })
    },

    getGoodsId(specId){
      for(var i = 0; i < this.specs.length; i++){
        if(specId==this.specs[i].id){
          return this.specs[i].goodsId;
        }
      }
    },
   /* navTo(route) {
      this.$router.push(route);
    },*/
    killGoods(){
      var that = this;
      if (!that.clientToken) {
        alert('请先登录！');
        return;
      }
      var quest={
        goodsId: that.getGoodsId(that.temSpecId),
        userId: that.clientId
      };
      const res = killGoods(JSON.stringify(quest));
      res.then((data) => {
        if(data.msg=="商品库存不足！"){
          alert("商品库存不足！");
          return;
        }
        if(data.msg=="您已经抢购过该商品！"){
          alert("您已经抢购过该商品！");
          return;
        }
        if(data.msg=="商品库存不足！"){
          alert("商品库存不足！");
          return;
        }
        if(data.msg=="抢购商品排队中，请稍候..."){
          var time = 0// 定义时间变量
          var msgTimer = null// 定义定时器
          const loading = that.$loading({
            lock: true,//lock的修改符--默认是false
            text: '排队中，请稍候...   '+'0s',//显示在加载图标下方的加载文案
            spinner: 'el-icon-loading',//自定义加载图标类名
            background: 'rgba(0, 0, 0, 0.7)',//遮罩层颜色
            target: document.querySelector('#table')//loadin覆盖的dom元素节点
          });
          msgTimer = setInterval(function () {
            if (time > 12) {
              loading.close();
              clearInterval(msgTimer);// 清除定时器
              falseToKill(JSON.stringify(quest)).then((data)=>{
                alert("抢购商品失败，请重新尝试！");
              });
            } else {
              queryKillRes(JSON.stringify(quest)).then((data)=>{
                loading.text='排队中，请稍候...   '+time+'s';
                time++;
                if(data==null){
                  console.log("数据返回空");
                }
                else {
                  //成功回调函数停止加载
                  loading.close()
                  clearInterval(msgTimer)// 清除定时器
                  //跳转
                  that.setOrdersToConfirm(JSON.stringify(
                      [{
                        specification_id: data.specification_id,
                        price: data.price,
                        product_name: data.product_name,
                        specification_name: data.specification_name,
                        num: data.num,
                        img: data.img,
                        size: data.size,
                        preferentiallevel: data.preferentiallevel,
                        activity_id:data.activity_id,
                        goods_id:data.goods_id,
                        user_id:data.user_id
                      }]
                  ));
                  that.$router.push({
                    name: 'KillOrderConfirm',
                    query: {data:JSON.stringify(
                          []
                      )}
                  })
                };
              })
                  .catch((e)=>{
                    alert(e);
                  })
            }
          }, 1000)

        }
      }).catch((e) => {
        alert(e);
      });
    },
    getComment(goodsId) {
      const res = getComment(goodsId);
      res.then((data) => {
        if (Object.keys(data).length <= 0) {
          this.rate = 0;
          this.commentList = [];
          return;
        }
        this.commentList = data;
        this.commentList.map((item, index) => {
          if (item.score >= 60) {
            this.rate++;
          }
        })
        this.rate = (100 * this.rate / Object.keys(data).length).toFixed(2);
        console.log(this.rate);
      }).catch((e) => {
        alert(e);
      })
    },

    getTypeGoodsList(categoryId) {
      const res = getGoodsList(categoryId);
      res.then((data) => {
        this.goodsList = data;
      }).catch((e) => {
        alert(e);
      })
    },
    getStock(item,data){
      for(var i = 0; i < data.length; i++){
        if(item.id==data[i].id){
          return data[i].stock;
        }
      }
    },
  },
  beforeDestroy() {
    clearInterval(this.timer);
    clearInterval(this.times);
    this.timer = null;
    this.times=null;
  },
  mounted() {
    if(this.clientToken == "" || this.clientToken== undefined || this.clientToken == null){
      this.hasToken=false;
    }
    else{
      this.hasToken=true;
    }
    this.getGoodsInfo(this.id);
    this.getGoodsMsg(this.id);
    this.getComment(this.id);
    var that=this;
    this.timer = setInterval(() => {
      getActivityProducts().then((data) => {
        var tempList = [];
        for (var i = 0; i < data.length; i++) {
          var starttime = new Date(data[i].starttime.replace(/-/g, '/'));
          var endtime = new Date(data[i].endtime.replace(/-/g, '/'));
          // console.log(starttime.getTime());
          // console.log(endtime.getTime());
          if (new Date().getTime() > starttime.getTime() && new Date().getTime() < endtime.getTime()&&data[i].remain>0) {
            tempList.push(data[i]);
          }
        }
        this.setActivityProducts(tempList);
        for (var j = 0; j < this.activityProducts.length; j++) {
          if (this.id == this.activityProducts[j].product_id)
            if (new Date(this.activityProducts[j].starttime.replace(/-/g, '/')).getTime() <= new Date().getTime() && new Date(this.activityProducts[j].endtime.replace(/-/g, '/')).getTime() > new Date().getTime()) {
              this.preferentiallevel = this.activityProducts[j].preferentiallevel;
              this.activity = true;
              //this.remain = this.activityProducts[j].remain;
              this.activity_id=this.activityProducts[j].activity_id;
              break;
            }
        }
        if (j == this.activityProducts.length) {
          this.preferentiallevel = 100;
          this.remain = 0;
          this.activity = false;
        }
      }).catch((e) => {
        alert(e);
      });
      //循环判断库存
      this.specs.map((item,index)=>{
        var quest={
          goodsId: item.goodsId
        };
        getKillGoodsStock(quest).then((data)=>{
          //console.log(data);
          if(data==null){
            console.log("提示：库存数据返回空");
          }else {
            item.stock=data.stock;
          }
        })
            .catch((e)=>{
              alert(e);
            })
        });
      var count=0;
      for(var k=0;k<this.specs.length;k++){
        count+=this.specs[k].stock;
      }
      this.remain=count;
    }, 1000)
    this.loading = true;
    if(this.hasToken){
      const res = hasStored(this.clientId, this.id,this.clientToken);
      res.then((data) => {
        console.info(data)
        if (data === true) {
          this.imgUrl = '/static/img/stored.jpg'
        }
      })
          .catch((e) => {
            alert(e);
          })
      setTimeout(() => {
        this.loading = false
      }, 300);
    }else{
      setTimeout(() => {
        this.loading = false
      }, 200);
    }
  },
  watch: {
    $route(to, from) {
      this.getGoodsInfo(to.params.id);
      this.getGoodsMsg(to.params.id);
      this.getComment(to.params.id);
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.GoodsDetail {
  .content {
    width: 100%;
    padding-top: 20px;

    .goodsInfo {
      width: 100%;
      overflow: hidden;

      .infoLeft {
        display: inline-block;
        width: 400px;
        height: 400px;
        float: left;
      }

      .infoRight {
        display: inline-block;
        float: right;
        width: 700px;

        .infoBox {
          margin-bottom: 20px;

          .name {
            font-size: 20px;
          }

          p {
            color: @fontDefaultColor;
            font-size: 15px;
          }

          .price {
            font-size: 30px;
            color: @falseColor;
          }

          span {
            color: @fontDefaultColor;
            font-size: 13px;
            display: inline-block;
            width: 50px;
          }

          .tips {
            width: auto;
            margin: 0 20px 0 5px;
          }

          .NumberInput {
            display: inline-block;
            vertical-align: middle;
          }
        }

        button {
          background-color: #b4a078;
          width: 170px;
          height: 50px;
          color: white;
          border: none;
          font-size: 19px;
          margin-right: 20px;
          margin-top: 10px;

          &:hover {
            opacity: 0.8;
          }
        }

        .buyBtn {
          border: 1px solid #b4a078;
          color: #b4a078;
          background-color: #f5f3ef;
        }

      }
    }

    .msgBox {
      margin: 50px 0;
      border: 1px solid @borderColor;
      padding-top: 0;

      .tagList {
        width: 100%;
        height: 40px;
        border-bottom: 1px solid @borderColor;
        background-color: #f5f5f5;

        li {
          width: 50%;
          height: 42px;
          position: relative;
          top: -2px;
          display: inline-block;
          text-align: center;
          line-height: 40px;
          font-size: 13px;
          cursor: pointer;

          &:hover {
            color: @thirdColor;
          }
        }

        .selected {
          background-color: white;
          border-top: 4px solid @thirdColor;
        }
      }

      .commentBody {
        padding: 20px;
        min-height: 300px;

        .rateBox {
          margin-bottom: 10px;

          span {
            color: @fontDefaultColor;
            display: inline-block;
            margin-right: 10px;
          }

          .rate {
            color: @falseColor;
            font-weight: 600;
            font-size: 30px;
          }
        }

        .commentList {
          width: 100%;

          li {
            width: 100%;
            display: block;
            margin: 0 auto;
            border-bottom: 1px solid @borderColor;
            padding: 20px 0;

            .userInfo {
              width: 80px;
              display: inline-block;
              text-align: center;

              img {
                margin: auto;
                display: block;
                width: 50px;
                height: 50px;
                border-radius: 50%;
                margin-bottom: 6px;
              }

              span {
                font-size: 13px;
                color: @fontDefaultColor;
              }
            }

            .commentInfo {
              display: inline-block;
              vertical-align: top;

              .starList {
                i {
                  color: #f9bd4f;
                }
              }

              .specName, .time {
                color: @fontDefaultColor;
                font-size: 13px;
                margin-top: 10px;
              }

              .comment {
                margin-top: 10px;
              }
            }
          }
        }

        .noComment {
          width: 100%;
          text-align: center;
          color: @thirdColor;
          padding-top: 30px;
        }
      }

      .msgBody {
        padding: 20px;
        min-height: 300px;

        .inputBox {
          margin-top: 20px;

          textarea {
            width: 88%;
            height: 50px;
            padding: 5px;
            border: 2px solid @borderColor;
            display: inline-block;
            overflow: hidden;
          }

          button, .banAsk {
            float: right;
            width: 10%;
            height: 50px;
            position: relative;
            display: inline-block;
            overflow: hidden;
            background-color: white;
            border: 2px solid @fontShallowColor;
            color: @fontDefaultColor;
          }

          .banAsk {
            background-color: @fontShallowColor;
            text-align: center;
            font-size: 12px;
            line-height: 50px;
            color: white;
            user-select: none;
          }
        }

        .msgList {
          margin-top: 20px;

          li {
            border-bottom: 1px solid @borderColor;
            padding: 10px 0;

            .ask, .answer {
              margin: 8px 0;
              width: 100%;

              .note {
                display: inline-block;
                color: white;
                text-align: center;
                width: 20px;
                height: 20px;
                border-radius: 50%;
                font-size: 10px;
                line-height: 20px;
              }

              .text {
                font-size: 14px;
              }

              .tipsInfo {
                float: right;
                font-size: 14px;
                color: @fontDefaultColor;
              }
            }

            .ask {
              .note {
                background-color: @falseColor;
              }
            }

            .answer {
              .note {
                background-color: @mainColor;
              }
            }
          }
        }
      }
    }

    .typeGoods {
      margin: 50px 0;
      border: 1px solid @borderColor;
      padding-top: 0;

      .title {
        text-align: center;
        width: 100%;
        height: 40px;
        line-height: 40px;
        background-color: #f5f5f5;
        font-weight: 600;
        border-bottom: 1px solid @borderColor;
      }

      .list {
        .GoodsItem {
          display: block;
          border-bottom: 1px dotted @borderColor;
          margin: 0 auto;
        }
      }
    }
  }

  .ban {
    user-select: none;
    cursor: not-allowed;
  }
}
</style>
