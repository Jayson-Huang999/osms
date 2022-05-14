<template>
  <div class="EditOrders">
    <div class="content">
      <div class="inputBox">
        <span style="font-size: 21px">活动发布</span>
      </div>
      <div class="inputBox">
        <span>商品名称：</span>
        <span class="val">{{ productName }}</span>
      </div>
      <div class="inputBox">
        <span>商品图片：</span>
        <ul class="el-upload-list el-upload-list--picture-card">
          <li tabindex="0" class="el-upload-list__item is-success">
            <div data-v-1048f9b8="">
              <img data-v-1048f9b8="" :src="imageUrl" alt="" class="el-upload-list__item-thumbnail avatar">
            </div>
          </li>
        </ul>
      </div>
      <div class="inputBox">
        <span>总库存：</span>
        <span class="val">{{ totalNum }}</span>
      </div>
      <div class="inputBox">
        <span>活动数量：</span>
        <TextInput placeholder="请输入活动数量" v-model="activityNum" :initText="activityNum"/>
      </div>
      <div class="inputBox">
        <span>折扣(%)：</span>
        <TextInput placeholder="请输入折扣率" v-model="preferentiallevel" :initText="preferentiallevel"/>
      </div>
      <div class="inputBox">
        <div class="block" style="float: left;margin-right: 100px;">
          <span class="demonstration">开始时间：</span>
          <el-date-picker
              v-model="startTime"
              type="datetime"
              placeholder="选择日期时间">
          </el-date-picker>
        </div>
        <div class="block">
          <span class="demonstration">结束时间：</span>
          <el-date-picker
              v-model="endTime"
              type="datetime"
              placeholder="选择日期时间">
          </el-date-picker>
        </div>
      </div>
      <div class="btnBox">
        <button class="confirmBtn" @click="addActivity">发布活动</button>
        <button class="normalBtn" @click="back">返回</button>
      </div>
    </div>
  </div>
</template>

<script>
import {addActivity, getGoodsInfo,getTotalStockByPid} from '../../api/seller';
import TextInput from '../../components/TextInput';

export default {
  name: 'Activity',
  components: {
    TextInput
  },
  data() {
    return {
      productId: this.$route.params.id,
      productName: '',
      imageUrl: '',
      totalNum: '',
      activityNum: '',
      preferentiallevel: '',
      startTime: '',
      endTime: ''
    }
  },
  methods: {
    addActivity() {
      if (this.totalNum<this.activityNum){
        alert("活动商品数量不可大于总库存数！");
        this.activityNum='';
        return;
      }
      if (this.preferentiallevel<=0){
        alert("活动折扣不可小等于0！");
        this.preferentiallevel='';
        return;
      }
      if (this.preferentiallevel>=100){
        alert("活动折扣不可大等于100！");
        this.preferentiallevel='';
        return;
      }
      addActivity({
        productId:this.productId,
        preferentiallevel:this.preferentiallevel,
        starttime:this.startTime,
        endtime:this.endTime,
        quantity:this.totalNum,
        remain:this.activityNum,
        status:0
      }).then((e)=>{
        console.log(e);
        alert(e);
        this.$router.go(-1);
      }).catch((e)=>{
        alert(e);
      })
    },
    back() {
      this.$router.go(-1);
    },
  },
  mounted() {
    const res = getGoodsInfo(this.productId);
    res.then((data) => {
      this.productId=this.$route.params.id;
      this.productName = data.name;
      this.imageUrl = data.images[0].url;
      this.activityNum = '';
      this.preferentiallevel = '';
      this.startTime = '';
      this.endTime = '';
      console.log(data);
    }).catch((e) => {
      alert(e);
    });
    const res2=getTotalStockByPid(this.productId);
    res2.then((data)=>{
      console.log(data);
      this.totalNum = data;
    }).catch((e)=>{
      alert(e);
    });
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.EditOrders {
  header {
    width: 100%;
    height: 40px;
    line-height: 40px;

    span {
      float: left;
    }
  }

  .content {
    width: 100%;
    background-color: white;
    padding: 10px;

    .inputBox {
      margin-bottom: 30px;

      span {
        width: 90px;
        display: inline-block;
        color: @fontDefaultColor;
        font-weight: 600;
      }

      .tips {
        font-weight: normal;
        width: auto;
        font-size: 13px;
        position: relative;
        left: 3px;
      }

      .val {
        width: auto;
        font-weight: 500;
        font-size: 14px;
        color: @fontDeepColor;
      }
    }

    .btnBox {
      margin: auto;
      display: block;
      width: 250px;
    }

    .confirmBtn {
      display: inline-block;
      margin-right: 30px;
      background-color: #337da4;
      color: white;
      border: none;
      width: 100px;
      height: 30px;
      border-radius: 5px;
      cursor: pointer;
    }

    .normalBtn {
      display: inline-block;
      background-color: grey;
      color: white;
      border: none;
      width: 100px;
      height: 30px;
      border-radius: 5px;
      cursor: pointer;
    }
  }
}
</style>