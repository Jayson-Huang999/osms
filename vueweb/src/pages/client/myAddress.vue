<!--showAddresses接口获取当前用户地址-->
<!--返回数据用户:{id:1}-->
<!--item:{"id":142,"user_id":185,"name":"prj","phone":"123","receiverphone":"1231","receiverprovince":"1231","receiverdistrict":"1231","receiveraddress":"1231","seen":false}-->
<template>
  <div class="address" id="address" name="address">
    <div class="address-layout">
          <div class="address-content">
            <div class="extra"></div>
            <div class="address-title">
              <p>收货地址</p>
            </div>
            <div class="address-body">
              <ul>
                <router-link to>
<!--                  <li class="add-address" @click="addVisible=true">-->
                  <li class="add-address" @click="addAddress">
                    <i class="el-icon-circle-plus-outline"></i>
                    <p>添加新地址</p>
                  </li>
                </router-link>
                <router-link to>
                  <li
                    :class="item.id == confirmAddress ? 'in-section' : ''"
                    v-for="(item,index) in address"
                    :key="item.id"
                    :style="item.is_default?'background-color:#FDF5E6':''"
                    @mouseenter="mouseEnter(item)"
                    @mouseleave="mouseLeave(item)"
                  >
                    <h2>{{item.receivername}}</h2>
                    <p class="receiverphone">电话：{{item.receiverphone}}</p>
                    <p class="receiverprovince">省：{{item.receiverprovince}}</p>
                    <p class="receivercity">市：{{item.receivercity}}</p>
                    <p class="receiverdistrict">区：{{item.receiverdistrict}}</p>
                    <p class="receiveraddress">详细地址：{{item.receiveraddress}}</p>
                    <div class="operate" v-show="item.seen">
                      <span @click="edit(item)">修改</span>
                      <span @click="deleteDialog(item.id,index)">删除</span>
                    </div>
                  </li>
                </router-link>
              </ul>
            </div>
          </div>
          <!-- 新建收货地址弹出框 -->
          <el-dialog title="新建收货地址" :visible.sync="addVisible" width="30%" class="demo-ruleForm">
            <el-form ref="form" :model="form" label-width="70px" :rules="rules">
              <el-form-item label="姓名" prop="receivername">
                <el-input v-model="form.receivername"></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="receiverphone">
                <el-input v-model.number="form.receiverphone"></el-input>
              </el-form-item>
              <el-form-item label="地区">
                <v-region type="column" v-model="selected" @values="values"></v-region>
              </el-form-item>
              <el-form-item label="地址" prop="receiveraddress">
                <el-input type="textarea" rows="3" v-model="form.receiveraddress"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="postEdit">确 定</el-button>
<!--              <el-button @click="addVisible = false">取 消</el-button>-->
              <el-button @click="cancelNewEdit">取 消</el-button>
            </span>
          </el-dialog>
          <!-- 新建收货地址弹出框END -->
          <!-- 修改收货地址弹出框 -->
          <el-dialog title="修改收货地址" :visible.sync="editVisible" width="30%" class="demo-ruleForm">
            <el-form ref="form" :model="form" label-width="70px" :rules="rules">
              <el-form-item label="姓名" prop="receivername">
                <el-input v-model="form.receivername"></el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="receiverphone">
                <el-input v-model.number="form.receiverphone"></el-input>
              </el-form-item>
              <el-form-item label="地区">
                <v-region type="column" v-model="selected" @values="values" ref="ad"></v-region>
<!--                <el-input v-model="form.phone"></el-input>-->
              </el-form-item>
              <el-form-item label="地址" prop="receiveraddress">
                <el-input type="textarea" rows="3" v-model="form.receiveraddress"></el-input>
              </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="saveEdit">确 定</el-button>
<!--              <el-button @click="editVisible = false">取 消</el-button>-->
              <el-button @click="cancelEdit">取 消</el-button>
            </span>
          </el-dialog>
          <!-- 修改收货地址弹出框END -->
          <!-- 删除收货地址弹出框 -->
          <el-dialog title="提示" :visible.sync="deleteVisible" width="30%" center>
            <div class="delete-dialog">
              <span>确认删除该地址吗？</span>
            </div>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="deleteAddress">确 定</el-button>
              <el-button @click="deleteVisible = false">取 消</el-button>
            </span>
          </el-dialog>
          <!-- 删除收货地址弹出框END -->
    </div>
  </div>
</template>
<script>
import {showAddresses,addAddress,updateAddress,deleteAddress} from '../../api/client'
export default {
  name: 'Details',
  data() {
    return {
      address: [],
      selected:{},
      addVisible: false,
      editVisible: false,
      deleteVisible: false,
      confirmAddress: 0, // 选择的地址id
      addressID: 0,
      addressIndex: 0,
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
      relform:{
      },
      rules: {
        receivername: [
          { required: true, message: '请输入姓名', trigger: 'blur' }
        ],
        receiverphone: [
          { required: true, message: '请输入电话号'},
          { type: 'number', message: '电话号必须为数字值'}
        ],
        receiveraddress:[
          { required: true, message: '请输入地址', trigger: 'blur' }
        ]
      },

    }
  },
  created() {
    this.getAddress();
  },
  methods: {
    values(data){
      this.form.receiverprovince=data.province.value;
      this.form.receivercity=data.city.value;
      this.form.receiverdistrict=data.area.value;
    },
    getAddress() {
      const id=this.$store.getters.clientId;
      const res=showAddresses(this.qs.stringify({id:id}));
      res.then(res => {
            this.address = res;
            console.log(res);
      }
      ).catch(err => {
        this.notifyError('获取收货地址失败', err)
        })
    },
    mouseEnter(item) {
      item.seen = true
      this.confirmAddress = item.id
    },
    mouseLeave(item) {
      item.seen = false
      this.confirmAddress = 0
    },
    edit(item) {
      this.emptySelected();
      this.form = JSON.parse(JSON.stringify(item));
      this.relform=JSON.parse(JSON.stringify(item));
      console.log("selected",this.selected);
      this.editVisible = true
    },
    addAddress(){
      this.emptySelected();
      this.emptyform();
      this.addVisible=true;
    },
    deleteDialog(val, index) {
      this.addressID = val
      this.addressIndex = index
      this.deleteVisible = true
    },
    postEdit() {
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
    saveEdit() {
      this.form.userId = this.$store.getters.clientId
      if(this.form.receiverdistrict==null){
        this.notifyError('请选择地区', '请选择地区')
        return
      }
        updateAddress(this.qs.stringify(this.form))
        .then(res => {
            this.address = res
            this.editVisible = false
          this.notifySucceed('修改收货地址成功');
        })
        .catch(err => {
          this.notifyError('修改收货地址失败', err)
        })
    },
    deleteAddress() {
        deleteAddress(this.addressID)
        .then(res => {
            this.address.splice(this.addressIndex, 1)
            this.deleteVisible = false
          this.notifySucceed('删除收货地址成功');
        })
        .catch(err => {
          this.$notify.error({
            title: '删除收货地址失败',
            message: err,
            offset: 100
          })
        })
    },
    cancelEdit(){
      this.form=this.relform;
      this.editVisible = false;

    },
    cancelNewEdit(){
      this.form=this.relform;
      this.addVisible = false;
    }
  },
  components: {
  }
}
</script>
<style scoped>
.address-layout {
  max-width: 1225px;
  margin: 0 auto;
}
.address-content {
  background-color: #ffffff;
  margin-bottom: 30px;
}
.address-title {
  height: 100px;
  display: flex;
  align-items: center;
}
.address-title p {
  font-size: 30px;
  color: #757575;
  margin-left: 50px;
}
.extra {
  height: 10px;
}
/*收货地址列表*/
.address .address-layout .address-content .address-body {
  overflow: hidden;
  width: 910px;
  margin: 0 auto;
}
.address .address-layout .address-content .address-body ul li {
  float: left;
  color: #333;
  width: 220px;
  height: 178px;
  border: 1px solid #e0e0e0;
  padding: 15px 24px 0;
  margin-right: 17px;
  margin-bottom: 24px;
}
.address .address-layout .address-content .address-body .in-section {
  border: 1px solid #ff6700;
}
.address .address-layout .address-content .address-body li h2 {
  font-size: 18px;
  font-weight: normal;
  line-height: 30px;
  margin-bottom: 10px;
}
.address .address-layout .address-content .address-body li p {
  font-size: 14px;
  color: #757575;
}
.address .address-layout .address-content .address-body li .address {
  padding: 10px 0;
  max-width: 180px;
  height: 70px;
  line-height: 22px;
  overflow: hidden;
}

.address .address-layout .address-content .address-body li .operate {
  margin-top: 10px;
  color: #ff6700;
  font-size: 14px;
  float: right;
}

.address .address-layout .address-content .address-body li .operate span {
  margin-right: 10px;
}
.address .address-layout .address-content .address-body .add-address {
  text-align: center;
  line-height: 30px;
}
.address .address-layout .address-content .address-body .add-address i {
  font-size: 30px;
  padding-top: 50px;
  text-align: center;
}
/*收货地址列表END*/
.address .address-layout .delete-dialog {
  margin: 0 auto;
  width: 180px;
  font-size: 20px;
}
</style>