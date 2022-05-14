<template>
  <div class="MyData">
    <ul>
      <li>
        <span>用户头像</span>
        <img :src="headimage" alt=""/>
        <el-upload class="upload-demo" style="margin-top:4px;display:inline-block;position:relative;
                   left: 30px;top: -20px" :show-file-list="false"
                   action="/api/file/upload" :file-list="[]"
                   multiple :before-upload="beforeAvatarUpload"
                   :on-success="handleAvatarSuccess">
          <el-button size="mini" type="success" plain>修改头像</el-button>
        </el-upload>
      </li>
      <li>
        <span>会员积分</span>
        <p>{{ score }}</p>
      </li>
      <li>
        <span>会员等级</span>
        <p>{{level}}</p>
      </li>
      <li>
        <span>昵称</span>
        <input type="text" ref="nickname" v-model="nickname" :disabled="nickname_input" @blur="nickname_check"/>
        <button @click="updateNickname">修改昵称</button>
      </li>
      <li>
        <span>账号</span>
        <input type="text" ref="username" v-model="username" :disabled="username_input" @blur="username_vertify"/>
        <button @click="updateUsername">修改账号</button>
      </li>
      <li>
        <span>性别</span>
        <input type="text" v-model="sex" :disabled="true"/>
        <el-radio v-model="sex" label="男">男</el-radio>
        <el-radio v-model="sex" label="女">女</el-radio>
      </li>
      <li>
        <span>联系电话</span>
        <input type="text" ref="phone" v-model="phone" :disabled="phone_input" @blur="phoneVertify"/>
        <button @click="updatePhone">修改电话</button>
      </li>
      <li>
        <span>电子邮件</span>
        <p>{{email}}</p>
        <button @click="showEmail">修改邮箱</button>
      </li>
      <li>
        <span>密码</span>
        <button @click="showPopup">修改密码</button>
        <button @click="updateUserData" class="saveBtn">保存</button>
      </li>
    </ul>

    <Popup title="修改密码" @popupClose="closePopup" v-show="popupShow">
      <div class="popupContent" slot="popupContent">
        <input type="password" v-model="oldPwd" placeholder="请输入原密码"/>
        <input type="password" v-model="newPwd" placeholder="请输入新密码"/>
        <input type="password" v-model="confirmPwd" placeholder="请再次输入新密码"/>
        <button @click="updatePwd">确认修改</button>
      </div>
    </Popup>
    <Popup title="修改邮箱" @popupClose="closeEmail" v-show="emailPopup">
      <div class="popupContent" slot="popupContent">
        <input type="text" v-model="newEmail" placeholder="请输入邮箱" @blur="emailVertify"/>
        <input type="text" v-model="emailCode" placeholder="请输入验证码"/>
        <button :disabled="flagEmail" @click="getEmailCode">{{ msgEmail }}</button>
        <button @click="updateEmail">确认修改</button>
      </div>
    </Popup>
  </div>
</template>

<script>
import {mapState, mapMutations} from 'vuex';
import {getUserData, updateUserData, updatePwd} from '../../api/client';
import Popup from '../../components/Popup';
import axios from "axios";

export default {
  name: 'MyData',
  components: {
    Popup
  },
  computed: {
    ...mapState([
      'clientToken',
      'clientId'
    ]),
  },
  data() {
    return {
      id: '',
      headimage: '',
      email: '',
      nickname: '',
      username: '',
      sex: '男',
      phone: '',
      popupShow: false,
      oldPwd: '',
      newPwd: '',
      confirmPwd: '',
      emailPopup: false,
      emailCode: '',
      newEmail: '',
      score: '',
      level: '',
      nickname_input: true,
      username_input: true,
      phone_input: true,
      msgEmail: '发送验证码',
      flagEmail: false
    }
  },

  methods: {
    ...mapMutations({
      setClientName: 'SET_CLIENT_NAME',
    }),
    nickname_check() {
      this.nickname_input = true;
      if (this.nickname == '') {
        alert("昵称不能空")
      }
    },
    username_vertify() {
      this.username_input = true;
      if (this.username == '') {
        alert("用户名不能为空")
      } else {
        axios({
          method: "post",
          url: "/api/user/username",
          data: {
            username: this.username
          }
        }).then(res => {
          console.info(res.data.data.msg)
          if (res.data.data === false) {
            alert("该账号已注册")
            this.username = ''
          }
        })
      }
    },
    updateNickname() {
      this.nickname_input = false;
      this.$nextTick(() => {
        this.$refs.nickname.focus();
      })
    },
    updateUsername() {
      this.username_input = false;
      this.$nextTick(() => {
        this.$refs.username.focus();
      })
    },
    updatePhone() {
      this.phone_input = false;
      this.$nextTick(() => {
        this.$refs.phone.focus();
      })
    },
    updateUserData() {
      var gender = 0;
      if (this.sex === '女') {
        gender = 1;
      }
      if (this.username === '') {
        alert("用户名不能为空")
      } else if (this.phone === '') {
        alert("手机号不能为空")
      } else if (this.nickname === '') {
        alert("昵称不能为空")
      } else {
        const res = updateUserData({
          id: this.id,
          nickname: this.nickname,
          phone: this.phone,
          headimage: this.headimage,
          sex: gender,
          username: this.username
        });
        res.then(() => {
          alert('修改成功!');
          this.setClientName(this.nickname);
        })
            .catch((e) => {
              alert(e);
            })
      }
    },
    closeEmail() {
      this.newEmail = '';
      this.emailCode = '';
      this.emailPopup = false;
    },
    showEmail() {
      this.emailPopup = true;
      //到对应的函数去修改
    },
    showPhone() {
      this.phone = true;
    },
    closePopup() {
      this.oldPwd = '';
      this.newPwd = '';
      this.confirmPwd = '';
      this.popupShow = false;
    },
    showPopup() {
      this.popupShow = true;
    },
    updatePwd() {
      if (this.newPwd !== this.confirmPwd) {
        alert('两次输入的密码不一致！');
        return;
      } else {
        axios({
          method: "post",
          url: "/api/user/updatePwd",
          data: {
            id: this.id,
            oldPwd: this.oldPwd,
            newPwd: this.newPwd
          }
        }).then(res => {
          console.info(res.data.data.msg)
          if (res.data.data.msg === '密码修改成功') {
            this.oldPwd = '';
            this.newPwd = '';
            this.confirmPwd = '';
            this.closePopup();
            alert('修改密码成功!');
          } else {
            alert("原密码验证失败")
            this.oldPwd = '';
          }
        })
      }
    },
    updateEmail() {
      if (this.newEmail === null) {
        alert("邮箱不能为空");
      } else if (this.emailCode === null) {
        alert("验证码不能为空");
      } else {
        axios({
          method: "post",
          url: "api/user/update/email",
          contentType: 'application/json',
          data: {
            email: this.newEmail,
            emailCode: this.emailCode,
            id: this.id
          }
        }).then(res => {
          console.info(res.data.data.msg)
          if (res.data.data.msg === '修改成功') {
            this.email = this.newEmail;
            this.newEmail = '';
            this.emailCode = '';
            this.closeEmail();
          } else {
            alert("验证码不正确");
            this.emailCode = '';
          }
        })
      }
    },
    getEmailCode() {
      if (this.newEmail != null) {
        axios({
          method: "post",
          url: "api/user/email",
          contentType: 'application/json',
          data: {
            email: this.newEmail
          }
        }).then(res => {
          console.info(res.data.data.msg)
          if (res.data.data.msg === true) {
            alert("该邮箱已注册")
            this.newEmail = ''
          } else {
            // 获取验证码按钮倒计时功能的实现
            const _this = this// ！！坑！setInterval中的this指向问题
            this.flagEmail = true // ！按钮不可重复点击
            var time = 120// 定义时间变量 120s
            var timer = null// 定义定时器
            timer = setInterval(function () {
              if (time === 0) {
                _this.msgEmail = '获取验证码'
                _this.flagEmail = false
                clearInterval(timer)// 清除定时器
              } else {
                _this.msgEmail = time + 's后失效'
                time--
              }
            }, 1000)
            axios({
              method: "post",
              url: "/api/user/find/getEmailCode",
              data: {
                email: this.newEmail

              }
            }).then(res => {
              console.info(res.data.data.msg)
            })
          }
        })
      } else {
        alert("邮箱不能为空")
      }
    },
    emailVertify() {
      const phoneModel = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
      if (!phoneModel.test(this.newEmail)) {
        alert('邮箱格式不正确！')
        this.newEmail = ''
      }
    },
    phoneVertify() {
      this.phone_input = true;
      const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      if (!regMobile.test(this.phone)) {
        alert('手机号不正确！')
        this.phone = ''
      }
    },
    handleAvatarSuccess(res, file) {
      console.info(res.url)
      this.headimage = res.url;
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },
  },

  mounted() {
    const res = getUserData(this.clientId);
    res.then((data) => {
      console.info(data);
      this.id = data.id;
      this.headimage = data.headimage;
      this.email = data.email;
      this.nickname = data.nickname;
      this.username = data.username;
      this.phone = data.phone;
      this.score = data.score;
      if (data.score<5000) {
        this.level = '普通会员'
      } else if (data.score<10000) {
        this.level = '白银会员'
      }else if(data.score<20000){
        this.level = '黄金会员'
      }else if(data.score<50000){
        this.level = '白金会员'
      }else{
        this.level = '钻石会员'
      }
      if (data.sex === true) {
        this.sex = '女'
      }
    })
        .catch((e) => {
          alert(e)
        })
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.MyData {
  ul {
    margin-left: 50px;
    width: 100%;
    overflow: hidden;

    li {
      margin-bottom: 30px;

      &:first-child {
        height: 60px;
        line-height: 60px;

        span {
          position: relative;
          bottom: 18px;
        }
      }

      span {
        display: inline-block;
        width: 100px;
        height: 20px;
      }

      img {
        display: inline-block;
        width: 60px;
        height: 60px;
        border-radius: 50%;
      }

      p {
        display: inline-block;
        width: 280px;
        text-align: center;
        border-bottom: 2px solid @thirdColor
      }

      input {
        width: 280px;
        border: none;
        text-align: center;
        border-bottom: 2px solid @thirdColor;
      }

      .long {
      }

      button {
        background-color: white;
        border: 1px solid @thirdColor;
        color: @thirdColor;
        width: 80px;
        height: 30px;
      }

      el-upload {
        background-color: white;
        border: 1px solid @thirdColor;
        color: @thirdColor;
      }
    }
  }
  .el-radio{
    margin-right:10px;
  }
  .saveBtn {
    background-color: @thirdColor;
    border: none;
    color: white;
    width: 110px;
    height: 35px;
    display: block;
    position: relative;
    left: 200px;
    top: 20px;
  }

  .popupContent {
    padding: 20px;

    input {
      display: block;
      border: none;
      border-bottom: 1px solid #313541;
      margin-bottom: 16px;
      font-size: 13px;
      padding: 5px;
      width: 200px;
    }

    button {
      background-color: #333333;
      border: none;
      color: white;
      width: 80px;
      height: 32px;
      display: block;
      margin: 20px auto 5px;
    }
  }
}

.btn_address {
  background-color: #333333;
  border: none;
  color: white;
  width: 160px;
  height: 32px;
  display: block;
  margin: 20px auto 5px;
}
</style>
