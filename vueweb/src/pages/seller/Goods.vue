<template>
  <div class="Goods">
    <header class="clear">
      <span>商品管理</span>
      <button @click="addCategory" style="margin-left: 50px">添加类目</button>
      <button @click="addBrand">添加品牌</button>
    </header>
    <Tag :tagList="tagTextList" @indexChange="changeTag"/>
    <div class="content">
      <ul class="clear">
        <li v-for="(item,index) in goodsList" :key="'goods'+item.id">
          <img :src="item.url" alt=""/>
          <span>{{ item.name }}</span>
          <div>
            <button class="normalBtn" @click="navTo('/backstage/goods/'+item.id)">编辑</button>
            <button class="normalBtn" @click="navTo('/backstage/activity/'+item.id)">活动</button>
            <button @click="deleteGoods(item.id)" class="deleteBtn">删除</button>
          </div>
        </li>
        <li>
          <div class="addProduct" @click="navTo('/backstage/goods/new')">
            <div>+</div>
            点击增加商品
          </div>
        </li>
      </ul>
    </div>
    <Popup title="增加品牌" @popupClose="closePopupBrand" v-show="popupShowBrand">
      <div class="popupContent" slot="popupContent">
        <input type="text" ref="brandNameInput" placeholder="品牌名："/>
        <input type="text" ref="logoUrl" placeholder="品牌logo图片地址："/>
        <input type="text" ref="describe" placeholder="描述："/>
        <button @click="addConfirmBrand">确认</button>
      </div>
    </Popup>
    <Popup title="增加类目" @popupClose="closePopupCategory" v-show="popupShowCategory">
      <div class="popupContent" slot="popupContent">
        <input type="text" ref="categoryNameInput" placeholder="请输入类目名称"/>
        <button @click="addConfirmCategory">确认</button>
      </div>
    </Popup>
  </div>
</template>

<script>
import {getAllProductByCidAndSid, getAllCategories, addCategory, addBrand, deleteGoods} from '../../api/seller';
import Tag from '../../components/Tag';
import Popup from '../../components/Popup';
import {mapState} from "vuex";

export default {
  name: 'Goods',
  components: {
    Tag,
    Popup
  },
  computed: {
    ...mapState([
      'sellerId'
    ]),
    tagTextList() {
      let temArr = [];
      this.tags.map((item, index) => {
        temArr.push(item.name)
      });
      return temArr;
    }
  },
  data() {
    return {
      tags: [],
      goodsList: [],
      popupShowCategory: false,
      popupShowBrand: false,
      curIndex: 0
    }
  },
  methods: {
    changeTag(index) {
      this.curIndex = index;
      let cid = this.tags[index].id;
      let sid = this.sellerId;
      // console.log("categoryId:", cid, "sellerid:", sid);
      const res = getAllProductByCidAndSid({"cid": cid, "sid": sid});
      res.then((goods) => {
        this.goodsList = goods;
      })
          .catch((e) => {
            alert(e);
          })
    },
    getAllCategories() {
      const res = getAllCategories();
      res.then((data) => {
        this.tags = data;
        this.changeTag(this.curIndex);
      })
          .catch((e) => {
            alert(e);
          })
    },

    addBrand() {
      this.popupShowBrand = true;
    },
    closePopupBrand() {
      this.popupShowBrand = false;
    },
    addConfirmBrand() {
      const val1 = this.$refs.brandNameInput.value;
      const val2 = this.$refs.logoUrl.value;
      const val3 = this.$refs.describe.value;
      const res = addBrand({
        name: val1,
        logourl: val2,
        describ: val3
      });
      res.then(() => {
        alert('添加成功!');
        this.closePopupBrand();
      }).catch((e) => {
        alert(e);
      })
    },
    addCategory() {
      this.popupShowCategory = true;
    },
    closePopupCategory() {
      this.popupShowCategory = false;
    },
    addConfirmCategory() {
      const val = this.$refs.categoryNameInput.value;
      const res = addCategory({
        name: val
      });
      res.then(() => {
        alert('添加成功!');
        this.getAllCategories();
        this.closePopupCategory();
      }).catch((e) => {
        alert(e);
      })
    },
    navTo(route) {
      this.$router.push(route);
    },
    deleteGoods(id) {
      this.$confirm('是否继续删除操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const res = deleteGoods(id);
        res.then(() => {
          this.goodsList.map((item, index) => {
            if (item.id === id) {
              this.goodsList.splice(index, 1)
            }
          })
        }).catch((e) => {
          alert(e);
        })
      }).catch(()=>{
        this.$message.info('已取消删除');
      })
    },
  },
  mounted() {
    this.getAllCategories();
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.Goods {
  header {
    width: 100%;
    height: 40px;
    line-height: 40px;

    span {
      float: left;
    }

    button {
      float: right;
      background-color: #337da4;
      color: white;
      border: none;
      width: 80px;
      height: 30px;
      border-radius: 5px;
    }
  }

  .content {
    position: relative;
    background-color: white;
    width: 100%;
    top: -2px;

    ul {
      padding: 10px;

      li {
        float: left;
        display: inline-block;
        width: 200px;
        height: 300px;
        text-align: center;
        margin-right: 20px;

        img {
          width: 100%;
          height: 200px;
          border: 1px solid @borderColor;
        }

        span {
          font-size: 13px;
          display: block;
          margin: 10px 0;
          color: @fontDefaultColor;
        }

        .normalBtn {
          width: 50px;
          height: 25px;
          color: @mainColor;
          border: 1px solid @mainColor;
          background-color: white;
          border-radius: 5px;
          margin-right: 5px;
        }

        .deleteBtn {
          width: 50px;
          height: 25px;
          color: @falseColor;
          border: 1px solid @falseColor;
          background-color: white;
          border-radius: 5px;
        }

        .addProduct {
          width: 100%;
          height: 200px;
          text-align: center;
          cursor: pointer;
          border: 1px solid @borderColor;
          color: @fontDefaultColor;

          div {
            margin: 50px auto 10px;
            border-radius: 50%;
            border: 2px solid @fontDefaultColor;
            width: 40px;
            height: 40px;
            font-size: 30px;
            text-align: center;
            line-height: 30px;
          }
        }

        &:last-of-type {
          margin-right: 0;
        }
      }
    }
  }

  .popupContent {
    padding: 20px;

    input {
      display: block;
      width: 200px;
      height: 30px;
      border: none;
      border-bottom: 2px solid #333333;
      margin-top: 20px;
    }

    button {
      display: block;
      margin: 30px auto 0;
      background-color: #333333;
      color: white;
      border: none;
      width: 80px;
      height: 30px;
      border-radius: 5px;
    }
  }
}
</style>
