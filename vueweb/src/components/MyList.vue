<!--
 * @Descripttion:展示商品列表组件 
 * @Author: congz
 * @Date: 2020-06-04 11:22:40
 * @LastEditors: congz
 * @LastEditTime: 2020-08-05 15:44:31
-->

<template>
  <div id="myList" class="myList">
    <ul>
      <li v-for="(item,index) in list" :key="item.favorite_id">
        <el-popover placement="top">
          <p>确定删除吗？</p>
          <div style="text-align: right; margin: 10px 0 0">
            <el-button type="primary" size="mini" @click="deleteFavorite(item.favorite_id,index)">确定</el-button>
          </div>
          <i class="el-icon-close delete" slot="reference" v-show="isDelete"></i>
        </el-popover>
        <router-link :to="{ path: '/mall/goods/'+item.product_id }">
          <img :src="item.url" alt />
          <h2>{{item.name}}</h2>
          <h3>{{item.describ}}</h3>
          <p>
            <span>{{item.minprice}}元</span>
            <!--            <span v-show="item.price != item.discount_price" class="del">{{item.price}}元</span>-->
          </p>
        </router-link>
      </li>
    </ul>
  </div>
</template>
<script>
import {deleteFavorites} from '../api/client';

export default {
  name: 'MyList',
  // list为父组件传过来的商品列表
  // isMore为是否显示“浏览更多”
  props: ['list', 'isDelete'],
  data() {
    return {}
  },
  computed: {

  },
  methods: {
    deleteFavorite(favorite_id, index) {
      deleteFavorites(favorite_id)
          .then(res => {
            this.list.splice(index, 1)
            this.notifySucceed('删除成功')
          })
          .catch(err => {
            this.notifyError('删除失败', err)
          })
    }
  }
}
</script>
<style scoped>
.myList ul li {
  z-index: 1;
  float: left;
  width: 230px;
  height: 280px;
  padding: 10px 0;
  margin: 0 0 14.5px 13.7px;
  background-color: white;
  -webkit-transition: all 0.2s linear;
  transition: all 0.2s linear;
  position: relative;
}
.myList ul li:hover {
  z-index: 2;
  -webkit-box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.1);
  -webkit-transform: translate3d(0, -2px, 0);
  transform: translate3d(0, -2px, 0);
}
.myList ul li img {
  display: block;
  width: 160px;
  height: 160px;
  background: url(../assets/img/placeholder.png) no-repeat 50%;
  margin: 0 auto;
}
.myList ul li h2 {
  margin: 25px 10px 0;
  font-size: 14px;
  font-weight: 400;
  color: #333;
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.myList ul li h3 {
  margin: 5px 10px;
  height: 18px;
  font-size: 12px;
  font-weight: 400;
  color: #b0b0b0;
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
.myList ul li p {
  margin: 10px 10px 10px;
  text-align: center;
  color: #ff6700;
}
.myList ul li p .del {
  margin-left: 0.5em;
  color: #b0b0b0;
  text-decoration: line-through;
}
.myList #more {
  text-align: center;
  line-height: 280px;
}
.myList #more a {
  font-size: 18px;
  color: #333;
}
.myList #more a:hover {
  color: #ff6700;
}
.myList ul li .delete {
  position: absolute;
  top: 10px;
  right: 10px;
  display: none;
}
.myList ul li:hover .delete {
  display: block;
}
.myList ul li .delete:hover {
  color: #ff6700;
}
</style>