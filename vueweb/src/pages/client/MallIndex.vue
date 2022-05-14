<template>
  <div class="MallIndex">
    <FadeSwiper class="swiperBox" :width="clientWidth" height="420px">
      <img class="banner" slot="item1" src="../../assets/img/banner1.jpg"/>
      <img class="banner" slot="item2" src="../../assets/img/banner2.jpg"/>
      <img class="banner" slot="item3" src="../../assets/img/banner3.jpg"/>
      <img class="banner" slot="item4" src="../../assets/img/banner4.jpg"/>
      <img class="banner" slot="item5" src="../../assets/img/banner5.jpg"/>
    </FadeSwiper>
    <section class="newGoods section">
      <SectionHeader title="新品首发" tips="周一周四上新，为你寻觅世间好物" moreText=""/>
      <Slick
        :ulWidth="(266*(newGoodsList.length-1))+(10*(newGoodsList.length-1))"
        :showWidth="(266*4)+(10*3)"
        :height="360"
      >
        <ul class="goodsList" :style="{width:`${(266*newGoodsList.length)+(10*(newGoodsList.length-1))}px`}" slot="list">
          <GoodsItem
            v-for="(item,index) in newGoodsList"
            :style="{marginRight: (index+1)%4===0?'0px':'10px',border:'3px solid #FFE4E1','border-radius':'5px'}"
            :key="+item.id"
            :id="item.id"
            :url="item.url"
            :name="item.name"
            :minprice="item.minprice"
            :activity="item.activity"
            :preferentiallevel="item.preferentiallevel"
          />
        </ul>
      </Slick>
    </section>
    <section class="flashSale section">
      <SectionHeader title="限时购" tips="抢抢抢，好货不等人"/>
      <div class="content">
        <div class="left">
          <p class="title">特价场</p>
          <hr/>
          <p class="tips">百万好货等你来抢</p>
          <div class="allBtn">查看全部 ></div>
        </div>
        <ul class="right" style="float:right" v-model="tempActivityProducts">
          <li v-for="(item,index) in tempActivityProducts" :key="item.product_id">
            <img class="leftImg" :src="item.url"/>
            <div class="rightBox" style="margin-left:5px">
              <p class="goodsName ellipsis" @click="navTo('/mall/goods/'+item.product_id)">{{ item.name }}</p>
              <div class="less">
                <!--<span class="lessBar"></span>-->
                <el-progress :text-inside="true" :stroke-width="18" :percentage="100*(item.quantity-item.remain)/item.quantity" color="#be4141"style="margin-bottom:10px"></el-progress>
                <span class="lessNum" style="text-align: center">还剩{{item.remain}}件</span>
              </div>
              <div class="price">
                <span class="nowPrice">限时价¥{{(item.minprice*item.preferentiallevel/100).toFixed(2)}}</span>
                <span class="beforePrice">原价¥{{ item.minprice }}</span>
              </div>
              <div class="countBox">
                <span class="time">{{ h[index] }}</span>
                <span>:</span>
                <span class="time">{{ m[index] }}</span>
                <span>:</span>
                <span class="time">{{ s[index] }}</span>
                <div class="buyBtn" @click="navTo('/mall/goods/'+item.product_id)">立即抢购</div>
              </div>
              <!--<div class="buyBtn" @click="navTo('/mall/goods/'+item.id)">立即抢购</div>-->
            </div>
          </li>
          <div v-bind:class="{positionChange:needPostion}" style="background-color: #FFEFD5;border-radius: 3px;padding-bottom:1px;padding-top:1px;">
            <el-pagination class="page"
                           style="text-align:center"
                           background
                           layout="prev, pager, next"
                           :page-size="4"
                           :total="activityProducts.length"
                            @current-change="changePage">
            </el-pagination>
          </div>

        </ul>
      </div>
    </section>
    <section class="hotGoods section">
      <SectionHeader title="人气推荐" tips="最火最潮商品，为您挑选" moreText=""/>
      <div class="content">
        <el-carousel trigger="click" indicator-position="outside" :interval="5000" style="width:1140px" height="540px">
          <el-carousel-item v-for="i in num" :key="i">
        <ul class="left">
          <GoodsItem
              :id="favoriteGoodsList[(i-1)*7].id"
              :url="favoriteGoodsList[(i-1)*7].url"
              :name="favoriteGoodsList[(i-1)*7].name"
              :minprice="favoriteGoodsList[(i-1)*7].minprice"
          />
        </ul>
        <ul class="right">
          <GoodsItem
            v-for="(item,index) in favoriteGoodsList.slice((i-1)*7+1,i*7)"
            :style="{marginBottom: index<=2?'10px':'0px'}"
            :key="+item.id"
            :id="item.id"
            :url="item.url"
            :name="item.name"
            :minprice="item.minprice"
            :activity="item.activity"
            :preferentiallevel="item.preferentiallevel"
          />
        </ul>
          </el-carousel-item>
        </el-carousel>
      </div>
    </section>
    <!--<section class="maker section">-->
      <!--<SectionHeader title="品牌制造商" tips="工厂直达消费者，剔除品牌溢价" moreText="更多制造商>"/>-->
      <!--<div class="content">-->
        <!--<ZoomImg imgSrc="http://yanxuan.nosdn.127.net/0266209ded1751f599fe0dc21bb33e02.jpg" class="left">-->
          <!--<div class="makerInfo" slot="otherEle">-->
            <!--<p class="large">Adidas制造商</p>-->
            <!--<hr/>-->
            <!--<p class="small">35元起</p>-->
          <!--</div>-->
        <!--</ZoomImg>-->
        <!--<ZoomImg imgSrc="http://yanxuan.nosdn.127.net/7cd0c8ed77da498090fb67c288ef05be.jpg" class="center">-->
          <!--<div class="makerInfo" slot="otherEle">-->
            <!--<p class="large">UGG制造商</p>-->
            <!--<hr/>-->
            <!--<p class="small">129元起</p>-->
          <!--</div>-->
        <!--</ZoomImg>-->
        <!--<div class="right">-->
          <!--<ZoomImg imgSrc="http://yanxuan.nosdn.127.net/d824afe357e61fbee097412c5894c6ce.jpg">-->
            <!--<div class="makerInfo" slot="otherEle">-->
              <!--<p class="large">新秀丽制造商</p>-->
              <!--<hr/>-->
              <!--<p class="small">49元起</p>-->
            <!--</div>-->
          <!--</ZoomImg>-->
          <!--<ZoomImg imgSrc="http://yanxuan.nosdn.127.net/cf5f4a0d110ca17b9e0a80e6f7e6184b.jpg">-->
            <!--<div class="makerInfo" slot="otherEle">-->
              <!--<p class="large">MUJI制造商</p>-->
              <!--<hr/>-->
              <!--<p class="small">12.9元起</p>-->
            <!--</div>-->
          <!--</ZoomImg>-->
        <!--</div>-->
      <!--</div>-->
    <!--</section>-->
    <!--<section class="typeSection section" v-for="(item,index) in typeList.slice(1)" :key="item.id">-->
      <!--<SectionHeader :title="item.name" tips="" moreText="查看更多>" @click.native="selectType(item.id)"/>-->
      <!--<ul class="content">-->
        <!--<GoodsItem-->
          <!--v-for="(item,index) in filterGoodsByType(item.id).slice(0,4)"-->
          <!--:style="{marginRight: (index+1)%4===0?'0px':'25px'}"-->
          <!--:key="+item.id"-->
          <!--:id="item.id"-->
          <!--:url="item.url"-->
          <!--:name="item.name"-->
          <!--:minprice="item.minprice"-->
        <!--/>-->
      <!--</ul>-->
    <!--</section>-->
  </div>
</template>

<script>
import {getTypes, getGoodsList,getfavoriteGoodsList,getNewGoodsList,getActivityProducts} from '../../api/client';
import SectionHeader from '../../components/SectionHeader';
import ZoomImg from '../../components/ZoomImg';
import GoodsItem from '../../components/GoodsItem';
import Slick from '../../components/Slick';
import FadeSwiper from '../../components/FadeSwiper';

import {getClientSize, getScrollWidth} from '../../util/util';
import {mapMutations} from 'vuex';
import { mapState } from 'vuex';

export default {
  name: 'MallIndex',
  components: {
    SectionHeader,
    ZoomImg,
    GoodsItem,
    Slick,
    FadeSwiper
  },
  computed: {
    ...mapState([
      'activityProducts'
    ]),
    clientWidth() {
      return getClientSize().width - getScrollWidth() + 'px';
    }
  },
  data() {
    return {
      mypage:1,
      num:0,
      typeList: [],
      goodsList: [{}],
      newGoodsList:[{}],
      favoriteGoodsList:[{}],
      tempActivityProducts:[],
      initTimestamp: 0,
      newTimestamp: 0,
      timer1: null,
      timer2: null,
      needPostion:false,
      h:[],
      m:[],
      s:[]
    }
  },

  methods: {
    ...mapMutations({
      setActivityProducts:"SET_ACTIVITY_PRODUCTS",
    }),
    changePage(currentPage){
      this.mypage=currentPage;
      var pageSize=4;
      this.tempActivityProducts=this.activityProducts.slice(pageSize*(currentPage-1),pageSize*currentPage<=this.activityProducts.length?pageSize*currentPage:this.activityProducts.length);
      if(this.tempActivityProducts.length<=2){
        this.needPostion=true;
      }
      else{
        this.needPostion=false;
      }
    },

    // format(percentage) {
    //
    // },
    filterGoodsByType(categoryId) {
      return this.goodsList.filter((item) => {
        return item.categoryId === categoryId;
      });
    },
    navTo(route) {
      this.$router.push(route);
    },
    selectType(categoryId) {
      if (categoryId == -1) {
        return;
      }
      this.navTo('/mall/show/goodsList/' + categoryId + '/all');
    },
    //获取人气商品
    getfavoriteGoodsList() {
      const res = getfavoriteGoodsList();
      res.then((data) => {
        this.favoriteGoodsList = data;
        this.num=Math.ceil(data.length/7);
        getActivityProducts().then((data)=> {
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
          for(var i=0;i<this.favoriteGoodsList.length;i++){
            for(var j=0;j<this.activityProducts.length;j++){
              if(this.favoriteGoodsList[i].id==this.activityProducts[j].product_id)
                if(new Date(this.activityProducts[j].starttime.replace(/-/g, '/' )).getTime()<=new Date().getTime()&&new Date(this.activityProducts[j].endtime.replace(/-/g, '/' )).getTime()>new Date().getTime()){
                  this.favoriteGoodsList[i].preferentiallevel=this.activityProducts[j].preferentiallevel;
                  this.favoriteGoodsList[i].activity=true;
                  var temp=this.favoriteGoodsList[i];
                  this.$set(this.favoriteGoodsList,i,temp);
                  break;
                }
            }
            if(j==this.activityProducts.length){
              this.favoriteGoodsList[i].preferentiallevel=100;
              this.favoriteGoodsList[i].activity=false;
              var temp=this.favoriteGoodsList[i];
              this.$set(this.favoriteGoodsList,i,temp);
            }
          }
          console.log(this.favoriteGoodsList);
        })
      })
        .catch((e) => {
          alert(e);
        })
    },getGoodsList(categoryId) {
      const res = getGoodsList(categoryId);
      res.then((data) => {
        this.goodsList = data;
      })
          .catch((e) => {
            alert(e);
          })
    },
    // 获取新品
    getNewGoodsList() {
      const res = getNewGoodsList();
      res.then((data) => {
        this.newGoodsList = data;
                getActivityProducts().then((data)=> {
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
                  for(var i=0;i<this.newGoodsList.length;i++){
                    for(var j=0;j<this.activityProducts.length;j++){
                      if(this.newGoodsList[i].id==this.activityProducts[j].product_id)
                        if(new Date(this.activityProducts[j].starttime.replace(/-/g, '/' )).getTime()<=new Date().getTime()&&new Date(this.activityProducts[j].endtime.replace(/-/g, '/' )).getTime()>new Date().getTime()){
                          this.newGoodsList[i].preferentiallevel=this.activityProducts[j].preferentiallevel;
                          this.newGoodsList[i].activity=true;
                          var temp=this.newGoodsList[i];
                          this.$set(this.newGoodsList,i,temp);
                          break;
                        }
                    }
                    if(j==this.activityProducts.length){
                      this.newGoodsList[i].preferentiallevel=100;
                      this.newGoodsList[i].activity=false;
                      var temp=this.newGoodsList[i];
                      this.$set(this.newGoodsList,i,temp);
                    }
                  }
                  console.log(this.newGoodsList);
                })
      })
          .catch((e) => {
            alert(e);
          })
    },
    searchTip(tip) {
      alert(tip)
    },
    inputTextChange(text) {
    },
    scrollHandle() {
      const top = this.$refs.typeList.getBoundingClientRect().top;
      //还未到顶
      if (top > 0) {
        this.navShouldFixed = false;
      }
      //已经到顶
      else {
        this.navShouldFixed = true;
      }
    },
    getActivityProducts(){
      getActivityProducts().then((data)=>{
        var tempList=[];
        for(var i=0;i<data.length;i++){
          var starttime=new Date(data[i].starttime.replace(/-/g, '/' ));
          var endtime=new Date(data[i].endtime.replace(/-/g, '/' ));
          // console.log(starttime.getTime());
          // console.log(endtime.getTime());
          if(new Date().getTime()>starttime.getTime()&&new Date().getTime()<endtime.getTime()){
            tempList.push(data[i]);
          }
        }
        this.setActivityProducts(tempList);
        this.tempActivityProducts=this.activityProducts.slice(0,this.activityProducts.length>=4?4:this.activityProducts.length);
        if(this.activityProducts.length>2){
          this.needPostion=false;
        }
        else{
          this.needPostion=true;
        }
      }).catch((e)=>{
        alert(e);
      })
    },
  },

  mounted() {
    //获取数据
    const res = getTypes();
    res.then((data) => {
      data.unshift({
        id: -1,
        name: '首页'
      });
      this.typeList = data;
      this.getGoodsList(-1);
      this.getNewGoodsList();
      this.getfavoriteGoodsList();
    }).catch((e) => {
      alert(e);
    });
    //获取所有活动商品信息并筛选出正在进行活动的商品
    getActivityProducts().then((data)=>{
      var tempList=[];
      for(var i=0;i<data.length;i++){
        var starttime=new Date(data[i].starttime.replace(/-/g, '/' ));
        var endtime=new Date(data[i].endtime.replace(/-/g, '/' ));
        // console.log(starttime.getTime());
        // console.log(endtime.getTime());
        if(new Date().getTime()>starttime.getTime()&&new Date().getTime()<endtime.getTime()){
          tempList.push(data[i]);
        }
      }
      this.setActivityProducts(tempList);
      this.tempActivityProducts=this.activityProducts.slice(0,this.activityProducts.length>=4?4:this.activityProducts.length);
      if(this.activityProducts.length>2){
        this.needPostion=false;
      }
      else{
        this.needPostion=true;
      }
      console.log(this.tempActivityProducts);
      //记录打开网页再加四小时的时间
      // for(var i=0;i<this.tempActivityProducts.length;i++){
      //   this.initTimestamp[i] = new Date(this.tempActivityProducts[i].endtime.replace(/-/g, '/' )).getTime();
      // }
      // for(var i=0;i<this.tempActivityProducts.length;i++){
      //   this.timer[i]=setInterval(this.mytimer(i),1000);
      // }
      this.timer1=setInterval(()=>{
        for(var i=0;i<this.tempActivityProducts.length;i++){
          this.initTimestamp= new Date(this.tempActivityProducts[i].endtime.replace(/-/g, '/' )).getTime();
          this.newTimestamp = new Date().getTime();
          let diff = parseInt((this.initTimestamp - this.newTimestamp) / 1000);
          // diff = diff % (86400 * 365) % (86400 * 30) % 86400;
          diff = diff % (86400 * 365) % (86400 * 30);
          this.$set(this.h,i,parseInt(new String(Math.floor(diff / 3600)).padStart(2, '0')));
          // this.h[i] = parseInt(new String(Math.floor(diff / 3600)).padStart(2, '0'));
          diff = diff % 3600;
          this.$set(this.m,i,parseInt(new String(Math.floor(diff / 60)).padStart(2, '0')));
          // this.m[i]= parseInt(new String(Math.floor(diff / 60)).padStart(2, '0'));
          diff = diff % 60;
          this.$set(this.s,i,parseInt(new String(diff).padStart(2, '0')));
          // this.s[i] = parseInt(new String(diff).padStart(2, '0'));
        }
      },1000);
      this.timer2=setInterval(()=>{
        getActivityProducts().then((data)=>{
          var tempList=[];
          for(var i=0;i<data.length;i++){
            var starttime=new Date(data[i].starttime.replace(/-/g, '/' ));
            var endtime=new Date(data[i].endtime.replace(/-/g, '/' ));
            // console.log(starttime.getTime());
            // console.log(endtime.getTime());
            if(new Date().getTime()>starttime.getTime()&&new Date().getTime()<endtime.getTime()){
              tempList.push(data[i]);
            }
          }
          this.setActivityProducts(tempList);
          console.log(this.mypage);
          this.tempActivityProducts=this.activityProducts.slice(4*(this.mypage-1),this.activityProducts.length>=4*this.mypage?4*this.mypage:this.activityProducts.length);
          if(this.tempActivityProducts.length>2){
            this.needPostion=false;
          }
          else{
            this.needPostion=true;
          }
          for(var i=0;i<this.newGoodsList.length;i++){
            for(var j=0;j<this.activityProducts.length;j++){
              if(this.newGoodsList[i].id==this.activityProducts[j].product_id)
                if(new Date(this.activityProducts[j].starttime.replace(/-/g, '/' )).getTime()<=new Date().getTime()&&new Date(this.activityProducts[j].endtime.replace(/-/g, '/' )).getTime()>new Date().getTime()){
                  this.newGoodsList[i].preferentiallevel=this.activityProducts[j].preferentiallevel;
                  this.newGoodsList[i].activity=true;
                  var temp=this.newGoodsList[i];
                  this.$set(this.newGoodsList,i,temp);
                  break;
                }
            }
            if(j==this.activityProducts.length){
              this.newGoodsList[i].preferentiallevel=100;
              this.newGoodsList[i].activity=false;
              var temp=this.newGoodsList[i];
              this.$set(this.newGoodsList,i,temp);
            }
          }
          for(var i=0;i<this.favoriteGoodsList.length;i++){
            for(var j=0;j<this.activityProducts.length;j++){
              if(this.favoriteGoodsList[i].id==this.activityProducts[j].product_id)
                if(new Date(this.activityProducts[j].starttime.replace(/-/g, '/' )).getTime()<=new Date().getTime()&&new Date(this.activityProducts[j].endtime.replace(/-/g, '/' )).getTime()>new Date().getTime()){
                  this.favoriteGoodsList[i].preferentiallevel=this.activityProducts[j].preferentiallevel;
                  this.favoriteGoodsList[i].activity=true;
                  var temp=this.favoriteGoodsList[i];
                  this.$set(this.favoriteGoodsList,i,temp);
                  break;
                }
            }
            if(j==this.activityProducts.length){
              this.favoriteGoodsList[i].preferentiallevel=100;
              this.favoriteGoodsList[i].activity=false;
              var temp=this.favoriteGoodsList[i];
              this.$set(this.favoriteGoodsList,i,temp);
            }
          }

        }).catch((e)=>{
          alert(e);
        })
      },2000)
      // console.log(this.initTimestamp);
      // console.log(this.timer);
    }).catch((e)=>{
      alert(e);
    })
  },

  beforeDestroy() {
    clearInterval(this.timer1);
    this.timer1 = null;
    clearInterval(this.timer2);
    this.timer2 = null;
  },

}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.MallIndex {
  width: 100%;

  .swiperBox {
    position: absolute;
    left: 0;
    top: 230px;
    left: 0px;
    top: 220px;
    margin-top: 20px;
    display: block;
    box-shadow: 0 -1px 2px rgba(0, 0, 0, 0.2);
    overflow: hidden;

    .banner {
      transform: scale(1.2, 1);
    }
  }

  .section {
    padding: 30px;
    overflow: hidden;

    .Slick {
      position: relative;
      left: -30px;
    }
  }

  .newGoods {
    margin-top: 440px;
  }

  .flashSale {
    .content {
      border: 3px solid #EEE9E9;
      border-radius: 5px;
      height: 376px;
      position: relative;

      .left {
        vertical-align: top;
        display: inline-block;
        width: 20%;
        height: 100%;
        position: relative;
        background-image: url(//yanxuan.nosdn.127.net/c9aeb62a3f79123d793d8c49b6698b09.jpg);
        background-repeat: no-repeat;
        background-size: 100%;
        text-align: center;
        color: #615548;
        cursor: pointer;

        .title {
          font-size: 26px;
          font-weight: 500;
          margin-top: 60px;
        }

        hr {
          width: 20px;
          height: 2px;
          background-color: #615548;
          border: none;
          margin-top: 20px;
        }

        .tips {
          font-size: 18px;
          margin-top: 20px;
        }

        .countBox {
          margin-top: 20px;

          .time {
            width: 42px;
            height: 42px;
            line-height: 42px;
            font-size: 19px;
            display: inline-block;
            color: white;
            background-color: #615548;
          }
        }

        .allBtn {
          width: 110px;
          height: 33px;
          line-height: 33px;
          font-size: 13px;
          background-color: #a7936e;
          color: white;
          border-radius: 20px;
          margin: 50px auto;
        }
      }

      .right {
        vertical-align: top;
        display: inline-block;
        width: 80%;
        height: 100%;
        position: relative;
        overflow: hidden;

        li {
          display: inline-block;
          width: 50%;
          height: 45%;
          overflow: hidden;

          .leftImg {
            width: 180px;
            height: 100%;
            display: inline-block;
          }

          .rightBox {
            display: inline-block;
            width: 240px;
            height: 100%;
            overflow: hidden;


            .countBox {
              margin-top: 5px;

              .time {
                /*width: 42px;*/
                /*height: 42px;*/
                /*line-height: 42px;*/
                width: 30px;
                height: 30px;
                line-height: 30px;
                font-size: 15px;
                display: inline-block;
                color: white;
                background-color: #615548;
                text-align:center;
              }
              .buyBtn {
                margin-left:15px;
                margin-top: 5px;
                width: 100px;
                height: 30px;
                color: white;
                display:inline-block;
                background-color: @falseColor;
                text-align: center;
                line-height: 30px;
                cursor: pointer;
              }
            }

            .goodsName {
              font-size: 15px;
              cursor: pointer;
              margin-top: 16px;

              &:hover {
                color: @thirdColor;
              }
            }

            .less {
              margin-top: 16px;

              .lessBar {
                display: inline-block;
                width: 150px;
                height: 10px;
                background-color: #ffe5e5;
                border: 1px solid #f2cecd;
                border-radius: 10px;
              }

              .lessNum {
                color: @fontDefaultColor;
                font-size: 13px;
              }
            }

            .price {
              margin-top: 10px;

              .nowPrice {
                color: @falseColor;
                font-size: 18px;
              }

              .beforePrice {
                color: @fontDefaultColor;
                text-decoration: line-through;
                margin-left: 10px;
                font-size: 14px;
              }
            }

            .buyBtn {
              margin-top: 10px;
              width: 100px;
              height: 30px;
              color: white;
              background-color: @falseColor;
              text-align: center;
              line-height: 30px;
              cursor: pointer;
            }
          }

          &:nth-of-type(1) {
            border-bottom: 1px solid black;
            border-right: 1px solid black;
          }

          &:nth-of-type(2) {
            border-bottom: 1px solid black;
          }

          &:nth-of-type(3) {
            border-right: 1px solid black;
          }
        }
        .positionChange{
          position:relative;
          top:169px;
        }
      }
    }
  }

  .maker {
    .content {
      .left, .center, .right {
        display: inline-block;
        height: 20px;
        width: 32%;
      }

      .left, .center {
        margin-right: 7px;

        .makerInfo {
          p {
            text-align: center;
          }

          hr {
            width: 60px;
          }

          .large {
            margin-top: 50px;
            margin-bottom: 10px;
            font-size: 26px;
          }

          .small {
            margin-top: 10px;
            font-size: 14px;
          }
        }
      }

      .right {
        .ZoomImg {
          width: 100%;
          height: 49%;

          .makerInfo {
            p {
              text-align: left;
              margin-left: 30px;
            }

            hr {
              width: 60px;
              position: relative;
              left: -122px;
            }

            .large {
              margin-top: 30px;
              margin-bottom: 10px;
              font-size: 20px;
            }

            .small {
              margin-top: 10px;
              font-size: 12px;
            }
          }

          &:first-child {
            margin-bottom: 7px;
          }
        }
      }
    }
  }

  .hotGoods {
    background-color: #FDF5E6;
    height: 654px;
    border-radius: 8px;

    .left {
      width: 394px;
      display: inline-block;
      vertical-align: top;

      .GoodsItem {
        width: 100%;
        height: 532px;
        background-color: white;
        border:3px solid #DCDCDC;
        border-radius: 6px;
      }

      .GoodsItem /deep/ .imgBox {
        height: 380px;
      }

      .GoodsItem /deep/ .goodsInfo {
        height: auto;
      }

      .GoodsItem /deep/ .goodsName {
        padding-left:25px;
        padding-right:25px;
        font-size: 20px;
        line-height: 40px;
      }
      .GoodsItem /deep/ .price {
        font-size: 20px;
        line-height: 40px;
      }
    }

    .right {
      display: inline-block;
      vertical-align: top;
      width: 740px;
      height: 100%;

      .GoodsItem {
        width: 230px;
        height: 260px;
        background-color: white;
        margin-left: 10px;
        overflow: hidden;
        border:2px solid #DCDCDC;
        border-radius: 5px;
      }

      .GoodsItem /deep/ .imgBox {
        height: 180px;
      }
    }
  }
}
</style>
