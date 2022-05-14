<template>
  <div class="GoodsList" >
    <div class="sortBox"  v-loading="loading">
      <span class="title">排序：</span>
      <span :class="['normal',{selected:sortMode===0}]" @click="changeSortMode(0)">默认</span>
      <span :class="['normal',{selected:sortMode!==0}]" @click="changeSortMode(3)">
        价格
        <i :class="['iconfont','icon-arrows-4-7',{'selected':sortMode===1}]" /><i :class="['iconfont','icon-arrows-4-7',{'selected':sortMode===2}]" />
      </span>
      <div class="brand" v-if="!isSearchPage">
        <span class="title">品牌：</span>
       <span v-for="(item,index) in brands"
             :key="item.id"
             :class="['normal',{selected:judgeCurBrand(item.id)}]"
             @click="selectBrand(item.id)">
         {{item.name}}
       </span>
      </div>
    </div>
    <ul class="result"  v-loading="loading">
      <GoodsItem
        v-for="(item,index) in sortedList"
        :style="{marginRight: (index+1)%4===0?'0px':'25px'}"
        :key="+item.id"
        :id="item.id"
        :url="item.url"
        :name="item.name"
        :minprice="item.minprice"
        :activity="item.activity"
        :preferentiallevel="item.preferentiallevel"
      />
      <p class="noGoods" v-if="goodsList.length<=0">抱歉，没有找到相关商品~</p>
  </ul>
  </div>
</template>

<script>
import {getGoodsList,searchGoods,getBrandByCid,getGoodsListBybid,getActivityProducts} from '../../api/client';
import GoodsItem from '../../components/GoodsItem';
import { mapState } from 'vuex';
import {mapMutations} from 'vuex';

export default {
  name: 'GoodsList',
  components:{
    GoodsItem
  },
  computed:{
    ...mapState([
      'activityProducts'
    ]),
    curBrandsId(){
        return this.brandId;
    },
    isSearchPage(){
      return Number(this.categoryId)===0?true:false;
    },
    categoryId(){
      return this.$route.params.categoryId || 0;
    },
    keyword(){
      return this.$route.params.keyword || '';
    },
    sortedList(){
      let temList = this.goodsList.slice();
      if(this.sortMode===0){
        return temList;
      }else if(this.sortMode===1){
        return temList.sort((a,b)=>{
          return a.minprice - b.minprice;
        })
      }else if(this.sortMode===2){
        return temList.sort((a,b)=>{
          return b.minprice - a.minprice;
        })
      }
    }
  },
  data () {
    return {
      goodsList:[],
      sortMode:0,//0默认，1价格升序，2价格降序
      brands:[],
      brandId:0,
      loading: false,
      timer:null
    }
  },

  methods:{
    ...mapMutations({
      setActivityProducts:"SET_ACTIVITY_PRODUCTS",
    }),
    getGoodsList(categoryId){
      this.loading=true;
      const res = getGoodsList(categoryId);
      res.then((data)=>{
        this.goodsList = data;
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
          for(var i=0;i<this.goodsList.length;i++){
            for(var j=0;j<this.activityProducts.length;j++){
              if(this.goodsList[i].id==this.activityProducts[j].id)
                if(new Date(this.activityProducts[j].starttime.replace(/-/g, '/' )).getTime()<=new Date().getTime()&&new Date(this.activityProducts[j].endtime.replace(/-/g, '/' )).getTime()>new Date().getTime()){
                  this.goodsList[i].preferentiallevel=this.activityProducts[j].preferentiallevel;
                  this.goodsList[i].activity=true;
                  var temp=this.goodsList[i];
                  this.$set(this.goodsList,i,temp);
                  break;
                }
            }
            if(j==this.activityProducts.length){
              this.goodsList[i].preferentiallevel=100;
              this.goodsList[i].activity=false;
              var temp=this.goodsList[i];
              this.$set(this.goodsList,i,temp);
            }
          }
          console.log(this.goodsList);
        }).catch((e)=>{
          alert(e);
        }),
        setTimeout(()=>{this.loading=false},100);
      })
      .catch((e)=>{
        alert(e);
      })
    },
    getBrandByCid(categoryId){
      this.loading=true;
      const res = getBrandByCid(categoryId);
      res.then((data)=>{
        this.brands = data;
        this.getGoodsList(this.categoryId);
      })
          .catch((e)=>{
            alert(e);
          })
    },
    getGoodsListBybid(brandId){
      const res = getGoodsList(brandId);
      res.then((data)=>{
        this.goodsList = data;
      })
          .catch((e)=>{
            alert(e);
          })
    },
    searchGoods(keyword){
      const res = searchGoods(keyword);
      res.then((data)=>{
        this.goodsList = data;
      })
      .catch((e)=>{
        alert(e);
      })
    },
    changeSortMode(mode){
      if(mode===3){
        this.sortMode = this.sortMode===1?2:1;
      }else{
        this.sortMode = 0;
      }
    },
    judgeCurBrand(bid){
      if(this.curBrandsId==bid){
        return true;
      }
      else return false;
    },
    selectBrand(bid){
      this.brandId=bid;
      if(bid!=0){
      const res = getGoodsListBybid(bid);
      res.then((data)=>{
        this.goodsList = data;
      })
          .catch((e)=>{
            alert(e);
          })
    }
      else this.getGoodsList(this.categoryId);
    }
  },

  beforeDestroy() {
    clearInterval(this.timer);
    this.timer = null;
  },

  mounted(){

    //类别页
    if(!this.isSearchPage){
      this.getGoodsList(this.categoryId);
      this.getBrandByCid(this.categoryId);
    }
    //搜索结果页
    else{
      this.searchGoods(this.keyword);
    }
    this.timer=setInterval(()=>{
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
        for(var i=0;i<this.goodsList.length;i++){
          for(var j=0;j<this.activityProducts.length;j++){
            if(this.goodsList[i].id==this.activityProducts[j].id)
              if(new Date(this.activityProducts[j].starttime.replace(/-/g, '/' )).getTime()<=new Date().getTime()&&new Date(this.activityProducts[j].endtime.replace(/-/g, '/' )).getTime()>new Date().getTime()){
                this.goodsList[i].preferentiallevel=this.activityProducts[j].preferentiallevel;
                this.goodsList[i].activity=true;
                var temp=this.goodsList[i];
                this.$set(this.goodsList,i,temp);
                break;
              }
          }
          if(j==this.activityProducts.length){
            this.goodsList[i].preferentiallevel=100;
            this.goodsList[i].activity=false;
            var temp=this.goodsList[i];
            this.$set(this.goodsList,i,temp);
          }
        }
      }).catch((e)=>{
        alert(e);
      })
    },2000)

  },

  watch:{
    $route(to,from){

      this.sortMode = 0;
      if(!this.isSearchPage){
        this.getBrandByCid(this.categoryId);
        this.brandId=0;
        this.getGoodsList(this.categoryId);
      }else{
        this.searchGoods(to.params.keyword);
      }
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";
.GoodsList{
  .sortBox{
    width: 90%;
    border-top: 1px dotted @borderColor;
    border-bottom: 1px dotted @borderColor;
    margin: 30px auto;
    padding-bottom: 30px;
    padding-top: 30px;
    span{
      font-size: 13px;
    }
    .title{
      color:@fontDefaultColor;
      display: inline-block;
      margin-right: 10px;
    }
    .normal{
      cursor: pointer;
      color:@fontDefaultColor;
      display: inline-block;
      margin-right: 10px;
    }
    .iconfont{
      color:@fontDefaultColor;
      display: inline-block;
      position: relative;
      left: -3px;
      &:last-of-type{
        transform:rotate(180deg);
        left: -6px;
      }
    }
    .selected{
      color:@thirdColor;
    }
    .brand{
      font-size: 13px;
      margin-top: 15px;
    }
  }
  .noGoods{
    margin: 38px auto;
    text-align: center;
    color:@thirdColor;
    font-size: 18px;
  }
}
</style>
