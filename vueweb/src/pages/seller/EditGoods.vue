<template>
  <div class="EditGoods">
    <header class="clear">
      <span>商品编辑</span>
    </header>
    <div class="content">
      <div class="inputBox">
        <span>商品名称：</span>
        <TextInput class="long" placeholder="请输入商品名称" v-model="productName" :initText="productName"/>
      </div>
      <div class="inputBox">
        <span>选择品牌：</span>
        <Radio v-for="(item,index) in brands" :key="item.id" v-model="temBrandId" :initVal="String(initBrandId)"
               radioName="brand" :radioVal="String(item.id)">
          <span class="tips" slot="tips">{{ item.name }}</span>
        </Radio>
      </div>
      <div class="inputBox">
        <span>选择类目：</span>
        <Radio v-for="(item,index) in categories" :key="item.id" v-model="temCategoryId"
               :initVal="String(initCategoryId)" radioName="category" :radioVal="String(item.id)">
          <span class="tips" slot="tips">{{ item.name }}</span>
        </Radio>
      </div>
      <div class="inputBox">
        <span>商品图片：</span>
        <!--        <TextInput class="long" placeholder="请输入图片地址" v-model="images[0].url" :initText="images[0].url"/>-->
      </div>
      <div>
        <ul class="el-upload-list el-upload-list--picture-card" v-for="img in tmpImages" style="float: left">
          <li tabindex="0" class="el-upload-list__item is-success">
            <div data-v-1048f9b8="">
              <img data-v-1048f9b8="" :src="img.url" alt="" class="el-upload-list__item-thumbnail avatar">
              <span data-v-1048f9b8="" class="el-upload-list__item-actions">
                <span data-v-1048f9b8="" class="el-upload-list__item-preview" @click="handlePictureCardPreview(img)">
                  <i data-v-1048f9b8="" class="el-icon-zoom-in"></i>
                </span>
                <span data-v-1048f9b8="" class="el-upload-list__item-delete" @click="handleRemove(img)">
                  <i data-v-1048f9b8="" class="el-icon-delete"></i>
                </span>
              </span>
            </div>
          </li>
        </ul>
      <el-upload
        class="avatar-uploader"
        action="/api/file/upload"
        list-type="picture-card"
        :auto-upload="true"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload"
        :limit="this.limitNum"
        ref="pictureUpload"
        style="margin-bottom: 30px;">
        <!--auto-upload设置为true  https://blog.csdn.net/qq_37768929/article/details/108450264-->
        <!--     :on-change="handleEditChange"     -->
        <i slot="default" class="el-icon-plus"></i>
        <div slot="file" slot-scope="{file}">
          <img
            class="el-upload-list__item-thumbnail avatar"
            :src="file.url" alt="">
          <span class="el-upload-list__item-actions">
        <span
          class="el-upload-list__item-preview"
          @click="handlePictureCardPreview(file)">
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
          v-if="!disabled"
          class="el-upload-list__item-delete"
          @click="handleRemove(file)">
          <i class="el-icon-delete"></i>
        </span>
      </span>
        </div>
      </el-upload>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt="">
      </el-dialog>
    </div>


    <div class="inputBox">
      <span class="verTop">规格详情：</span>
      <ul>
        <li v-for="(item,index) in specs" :key="index" style="width: 186%;">
          <span>名称：</span>
          <TextInput placeholder="请输入规格名称" v-model="item.name" :initText="item.name"/>
          <span>尺寸：</span><input type="text" class="numInput" v-model.trim.number="item.size"/>
          <span>价格：</span><input type="text" class="numInput" v-model.trim.number="item.price"/>
          <span>库存量：</span><input type="text" class="numInput" v-model.trim.number="item.stock"/>
          <span>图片路径：</span>
          <TextInput placeholder="请输入图片路径" v-model="item.img" :initText="item.img"/>
          <button v-show="specs.length>1" @click="deleteSpec(item.id)"><i class="iconfont icon-close"/></button>
        </li>
        <li class="addSpec" @click="showPopup" style="margin-left: 66%;">添加规格</li>
      </ul>
    </div>
    <div class="inputBox">
      <span>详情描述：</span>
      <TextInput class="long" placeholder="请输入简单描述" v-model="describ" :initText="describ"/>
    </div>
    <div class="btnBox">
      <button class="confirmBtn" @click="saveChange">保存修改</button>
      <button class="normalBtn" @click="back">返回</button>
    </div>
  </div>
  <Popup title="添加规格" @popupClose="closePopup" v-show="popupShow">
    <div class="popupContent" slot="popupContent">
      <input type="text" ref="specNameInput" placeholder="请输入规格名称"/>
      <input type="text" ref="sizeInput" placeholder="请输入尺寸"/>
      <input type="text" ref="priceInput" placeholder="请输入单价"/>
      <input type="text" ref="stockNumInput" placeholder="请输入库存量"/>
      <input type="text" ref="imgInput" placeholder="请输入图片路径"/>
      <button @click="addConfirm">确认</button>
    </div>
  </Popup>
  </div>
</template>


<script>

import Popup from '../../components/Popup';
import TextInput from '../../components/TextInput';
import Radio from '../../components/Radio';
import {mapState, mapMutations} from 'vuex';
import {
  getAllBrands,
  getAllCategories,
  getGoodsInfo,
  addProduct,
  addSpec,
  deleteSpec,
  updateSpec,
  updateProduct,
  addImage,
  deleteImage
} from '../../api/seller';

export default {
  name: 'EditGoods',
  components: {
    Popup,
    Radio,
    TextInput
  },
  computed: {
    ...mapState([
      'sellerId'
    ])
  },
  data() {
    return {
      popupShow: false,
      id: this.$route.params.id,
      productId: -1,
      productName: '',
      brands: [],
      categories: [],
      initBrandId: '',
      temBrandId: '',
      initCategoryId: '',
      temCategoryId: '',
      images: [],
      tmpImages:[],
      delImages:[],
      describ: '',
      specs: [{
        productId: -1,
        name: '默认',
        img: '',
        size: 0,
        stock: 0,
        price: 0,
      }],

      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      limitNum: 5
      // imageUrl: 'https://pic1.zhimg.com/v2-d58ce10bf4e01f5086c604a9cfed29f3_r.jpg?source=1940ef5c',
    }
  },
  methods: {
    handleRemove(file) {
      console.log(file);
      console.log(this);
      // console.log(this);
      // console.log(this.$refs);
      // console.log(file);
      // console.log(this.images);
      if("productId" in file){
        for (let i = 0; i < this.tmpImages.length; i++) {
          if (this.tmpImages[i].url === file.url) {
            this.delImages.push(file);
            this.tmpImages.splice(i, 1);
            this.limitNum+=1;
            break;
          }
        }
        console.log("delImgs:",this.delImages);
      }else{
        this.$refs.pictureUpload.handleRemove(file);
        for (let i = 0; i < this.images.length; i++) {
          if (this.images[i].url === file.response.url) {
            this.images.splice(i, 1);
            break;
          }
        }
      }
      console.log("images:",this.images);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarSuccess(res, file) {
      console.log("success:", file, res);
      const fileType = /^image\/(jpeg|png|jpg)$/.test(file.raw.type);
      if (fileType) {
        this.images.push({url: res.url, productId: this.productId});

        // this.imageUrl = URL.createObjectURL(file.raw);

        // let videoId = JSON.parse(response.data).fileId;//保存video的id，用于拼接
        // this.$api.getPublicFileUrl().then(res => {
        //   this.publicFileUrl = res.data.data
        //   this.editForm.videoUrl = this.publicFileUrl + '/' + videoId
        //   console.log(this.editForm.videoUrl)
        // })
      } else {
        this.handleRemove(file);
        console.log("上传失败！");
        // this.$message.info("上传失败，请重新上传");
      }
    },
    beforeAvatarUpload(file) {
      console.log("before:", file);
      const isLt10M = file.size / 1024 / 1024 < 10;

      const testType = /^image\/(jpeg|png|jpg)$/.test(file.type)
      if (!testType) {
        this.$message.error('上传图片格式只能是jpg/jpeg/png格式!');
      }
      if (!isLt10M) {
        this.$message.error('上传头像图片大小不能超过10MB!');
      }
      return testType && isLt10M;
    },


    getAllBrands() {
      const res = getAllBrands();
      res
        .then((data) => {
          this.brands = data;
        })
        .catch((e) => {
          alert(e)
        })
    },
    getAllCategories() {
      const res = getAllCategories();
      res
        .then((data) => {
          this.categories = data;
        })
        .catch((e) => {
          alert(e)
        })
    },
    back() {
      this.$router.go(-1);
    },
    saveChange() {
      if (this.id === 'new') {
        const res = addProduct({
          sellerId: this.sellerId,
          brandId: this.temBrandId,
          categoryId: this.temCategoryId,
          name: this.productName,
          describ: this.describ,
          status: 1,
          createtime: new Date(),
          updatetime: new Date(),
        });
        res.then((e) => {
          let images = this.images;
          let specs = this.specs;
          console.log("imgs:", images);
          console.log("specs", specs);
          console.log(e);
          for (let i in images) {
            images[i].productId = e.id;
            console.log(images[i]);
            addImage(images[i]);
          }
          for (let i in specs) {
            specs[i].productId = e.id;
            console.log(specs[i]);
            addSpec(specs[i]);
          }
          alert('商品信息添加成功！')
        }).catch((e) => {
          alert(e);
        })
      } else {
        let images = this.images;
        let specs = this.specs;
        for (let i in images) {
          console.log(images[i]);
          addImage(images[i]);
        }
        for (let i in this.delImages){
          console.log(this.delImages[i]);
          deleteImage(this.delImages[i].id);
        }
        for (let i in specs) {
          console.log(specs[i]);
          updateSpec(specs[i]);
        }
        const res = updateProduct({
          id: this.productId,
          name: this.productName,
          brandId: this.temBrandId,
          categoryId: this.temCategoryId,
          describ: this.describ,
          updatetime: new Date()
        });
        res.then(() => {
          alert('修改成功！')
        }).catch((e) => {
          alert(e);
        })
      }
    },
    closePopup() {
      this.popupShow = false;
    },
    addConfirm() {
      if (this.id === 'new') {
        const name = this.$refs.specNameInput.value;
        const size = this.$refs.sizeInput.value;
        const price = this.$refs.priceInput.value;
        const stock = this.$refs.stockNumInput.value;
        const img = this.$refs.imgInput.value;
        this.specs.push({
          productId: this.productId,
          name: name,
          stock: stock,
          price: price,
          img: img,
          size: size
        });
        this.closePopup();
      } else {
        const res = addSpec({
          productId: this.productId,
          name: this.$refs.specNameInput.value,
          img: this.$refs.imgInput.value,
          size: this.$refs.sizeInput.value,
          stock: this.$refs.stockNumInput.value,
          price: this.$refs.priceInput.value,
        });
        res.then((spec) => {
          this.specs.push(spec);
          this.closePopup();
          alert("添加成功！");
        }).catch((e) => {
          alert(e);
        })
      }
    },
    showPopup() {
      this.popupShow = true;
    },
    deleteSpec(specId) {
      if (this.id === 'new') {
        this.specs.map((item, index) => {
          if (item.id === specId) {
            this.specs.splice(index, 1);
          }
        })
      } else {
        this.$confirm('是否继续删除操作?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
          const res = deleteSpec(specId);
          res.then(() => {
            this.specs.map((item, index) => {
              if (item.id === specId) {
                this.specs.splice(index, 1);
              }
            })
            alert("删除成功！");
          }).catch((e) => {
            alert(e);
          })
        }).catch(()=>{
          this.$message.info('已取消删除');
        })
      }
    }
  }
  ,
  mounted() {
    //获取所有品牌
    this.getAllBrands();
    //获取所有商品种类
    this.getAllCategories();
    //新建商品
    if (this.id === 'new') {

    } else {
      const res = getGoodsInfo(this.id);
      res.then((data) => {
        this.temBrandId = data.brandId;
        this.initBrandId = data.brandId;
        this.temCategoryId = data.categoryId;
        this.initCategoryId = data.categoryId;
        this.productId = data.id;
        this.productName = data.name;
        // this.images = data.images;
        for(let i in data.images){
          this.tmpImages.push(data.images[i]);
        }
        this.limitNum-=this.images.length;
        this.describ = data.describ;
        this.specs = data.specs;
        console.log(this.specs);
      }).catch((e) => {
        alert(e);
      })
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.EditGoods {
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

    textarea {
      width: 80%;
      height: 300px;
      border: 1px solid @borderColor;
    }

    .inputBox {
      margin-bottom: 30px;

      span {
        width: 90px;
        display: inline-block;
        color: @fontDefaultColor;
        font-weight: 600;
        vertical-align: middle;
      }

      .verTop {
        vertical-align: top;
      }

      .Radio /deep/ input:checked + .tipsBox {
        &:after {
          background-color: #337da4;
        }
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
        color: @fontDeepColor;
      }

      ul {
        display: inline-block;
        width: 500px;

        li {
          width: 100%;
          height: 40px;

          span {
            width: auto;
            font-size: 13px;
            font-weight: 500;
          }

          input {
            margin-right: 10px;
          }

          button {
            width: 50px;
            height: 25px;
            color: #d7514a;
            border: none;
            background-color: white;
            border-radius: 5px;
          }
        }

        .addSpec {
          text-align: center;
          line-height: 30px;
          background-color: #337da4;
          color: white;
          border: none;
          width: 100px;
          height: 30px;
          border-radius: 5px;
          font-size: 13px;
          cursor: pointer;
          margin: 10px auto;
        }
      }

      .long {
        width: 900px;
      }

      .numInput {
        width: 30px;
        text-align: center;
        border: none;
        border-bottom: 2px solid @mainColor;
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

  .popupContent {
    padding: 10px;

    input {
      display: block;
      width: 400px;
      margin-bottom: 10px;
      height: 30px;
    }

    button {
      display: block;
      margin: auto;
      background-color: #333333;
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
