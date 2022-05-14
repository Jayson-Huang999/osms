<template>
  <div class="cotn_principal">
    <div class="cont_centrar">
      <div class="cont_login">
        <div class="cont_info_log_sign_up">
          <div class="col_md_login">
            <div class="cont_ba_opcitiy">
              <h2>登录</h2>
              <p>欢迎回来！</p>
              <button class="btn_login" @click="cambiar_login()">登录</button>
            </div>
          </div>
          <div class="col_md_sign_up">
            <div class="cont_ba_opcitiy">
              <h2>注册</h2>
              <p>欢迎加入！</p>
              <button class="btn_sign_up" @click="cambiar_sign_up()">注册</button>
            </div>
          </div>
        </div>
        <div class="cont_back_info">
          <div class="cont_img_back_grey"> <img src="@/assets/img/po.jpg" alt="" /> </div>
        </div>
        <div class="cont_forms" >
          <div class="cont_img_back_"> <img src="@/assets/img/po.jpg" alt="" /> </div>
          <div class="cont_form_login">
            <a href="#" @click="ocultar_login_sign_up" ><img src="@/assets/img/return.png" height="20" width="20"></a>
            <h2>登录</h2>
            <label class="label-login">账号: </label>
            <input type="text" placeholder="输入账号" v-model="phone" required/> <br/>
            <label class="label-login">密码: </label>
            <input type="password" placeholder="输入密码" v-model="password" required/><br/>
            <div class="agree" style="margin-top: 15px">
              <el-checkbox v-model="checked" label="记住我" style="color: #fff;"></el-checkbox>
              <button class="btn_login" @click="login">登录</button>
            </div>
            <div class="login_p">
              忘记密码?
              <a style="color: #0000EE" href="#" @click="find_pwd_phone()">找回</a>
            </div>
          </div>
          <div class="cont_form_sign_up"> <a href="#" @click="ocultar_login_sign_up()"><img src="@/assets/img/return.png" height="20" width="20"></a>
            <h2>注册</h2>
            <form >
              <label class="label-text">昵称: </label>
              <input  type="text" placeholder="输入昵称" v-model="nickname" name="nickname" required/>
              <br/>
              <label class="label-text">账号: </label>
              <input  type="text" placeholder="输入账号" v-model="username" name="username" required/>
              <br/>
              <label class="label-text">密码: </label><input  type="password" placeholder="输入密码" v-model="password" name="password" required/><br/>
              <label class="label-text">确认密码: </label><input  type="password" placeholder="确认密码" v-model="req_password" name="req_password" required @blur="password_vertify"/><br/>
              <label class="label-text">手机号: </label><input  type="tel" placeholder="输入手机号"  v-model="phone" name="phone" required @blur="phone_vertify"/><br/>
              <label class="label-text"  >邮箱: </label><input type="email" placeholder="输入邮箱" v-model="email" name="email" required /><br/>
              <label class="label-text">验证码: </label><input style="width: 115px" type="text" v-model="email_code" name="email_code" placeholder="输入验证码" required/>
              <button class="btn_send_code" plain :disabled="flagEmail" @click="EmailCode">{{ msgEmail }}</button><br/>
              <br/>
              <div class="agree" style="margin-top: 15px">
                <input type="checkBox"  v-model="agreement" required>
                <a target="_blank" href="使用协议链接" style="color: #0000EE">同意《用户注册协议》</a>
              </div>
              <br/>
              <button type="submit" class="btn_sign_up" @click="signUp" >注册</button>
            </form>
          </div>
          <div class="find_pwd_phone"> <a href="#" @click="ocultar_login_sign_up()"><img src="@/assets/img/return.png" height="20" width="20"></a>
            <h2>找回密码</h2>
            <div>
              <label class="label-find">邮箱: </label>
              <input type="text" placeholder="输入邮箱" v-model="email" @blur="email_vertify()" /><br/>
              <label class="label-find">验证码: </label>
              <input style="width: 115px" type="text" v-model="email_code" placeholder="输入验证码"/>
              <button class="btn_send_code" plain :disabled="flagEmail" @click="getEmailCode">{{ msgEmail }}</button><br/>
              <button class="btn_find" @click="find">找回</button>
            </div>
          </div>
          <div class="showInfo"> <a href="#" @click="ocultar_login_sign_up()"><img src="@/assets/img/return.png" height="20" width="20"></a>
            <h2>修改密码</h2>
            <label class="label-find">密码: </label>
            <input type="text"  placeholder="请输入密码" v-model="password"/><br/>
            <label class="label-find">确认密码: </label>
            <input type="text" v-model="req_password" placeholder="请确认密码"/><br/>
            <div class="agree" style="margin-top: 15px">
              <button class="btn_return" @click="updatePwd">返回登录</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {searchGoods} from "../../api/client";
import {mapMutations} from 'vuex'
import {getClientSize} from '../../util/util';
import {login, signup} from '../../api/client';
import axios from "axios";
export default {
  name: 'MallLogin',
  data () {
    return {
      id:'',
      nickname: '',
      phone: '',
      email: '',
      email_code: '',
      username: '',
      password: '',
      req_password: '',
      checked: false,
      agreement: false,
      flagEmail:false,
      msgEmail:'获取验证码'
    }
  },
  mounted() {
    //第1步，在页面加载的时候，首先去查看一下cookie中有没有用户名和密码可以用
    let user = localStorage.getItem("phone");
    if (user) {
      this.phone = localStorage.getItem("phone");
      this.password = localStorage.getItem("password");
      this.checked = true;
    }
  },
  methods: {
    ...mapMutations({
      setClientId:'SET_CLIENT_ID',
      setClientName: 'SET_CLIENT_NAME',
      setClientToken: 'SET_CLIENT_TOKEN',
    }),
    remenber () {
      if(this.checked === false) {
        this.checked = true;
      } else {
        this.checked =false;
      }
    },
    updatePwd(){
      if(this.password === this.req_password){
        axios({
          method: "post",
          url: "/api/user/findPwd",
          data: {
            id:this.id,
            password:this.password
          }}).then(res =>{
          console.info(res.data.data.msg)
          this.password = '';
          this.req_password = '';
          this.returnLogin();
        })
      } else {
        alert('两次输入的密码不一致！');
        return;
      }
    },
    signUp(){
      axios({
        method: "post",
        url: "api/user/signUp",
        contentType: 'application/json',
        data: {
          username: this.username,
          password: this.password,
          nickname: this.nickname,
          phone: this.phone,
          email: this.email,
          emailCode: this.email_code,
        }}).then(res => {
        console.info(res.data.data.msg)
        if(res.data.data.msg === '注册成功') {
          this.returnLogin()
        } else {
          alert("验证码不正确")
          this.setNull()
        }
      })
    },
    EmailCode(){
      if (this.email != null) {
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
          url: "/api/user/find/EmailCode",
          data: {
            email: this.email
          }}).then(res =>{
          console.info(res.data.data.msg)
          if(res.data.data.msg === '邮箱已注册') {
            alert("该邮箱已注册")
            _this.msgEmail = '获取验证码'
            _this.flagEmail = false
            clearInterval(timer)// 清除定时器
            this.email = ''
          }
        })
      } else {
        alert("邮箱不能为空")
      }
    },
    getEmailCode(){
        if (this.email != null) {
          axios({
            method: "post",
            url: "/api/user/email",
            data: {
              email: this.email
            }}).then(res =>{
            console.info(res.data.data.msg)
            if(res.data.data.msg === false){
              alert("该邮箱没有注册")
            }else {
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
                  email: this.email
                }}).then(res =>{
                console.info(res.data.data.msg)
              })
            }
          })
        } else {
          alert("邮箱不能为空")
        }
    },
    login () {
      if(this.phone != '' && this.password !='') {
        axios({
          method: "post",
          url: "api/user/login",
          data: {
            phone: this.phone,
            password: this.password
          }}).then(res => {
          console.info(res.data)
          if (res.data!= null) {
            console.info('yes')
            console.info(this.checked)
            this.setClientName(res.data.data.id);
            console.info(res.data.data.id)
            this.setClientName(res.data.data.nickname);
            this.setClientToken(res.data.data.token);
            this.setClientId(res.data.data.user_id);

            if (this.checked) {
              localStorage.setItem("phone", this.phone);
              localStorage.setItem("password", this.password);
            } else {
              localStorage.removeItem("phone");
              localStorage.removeItem("password");
            }
            this.$router.push( "/")
          } else {
            console.info('no')
          }
        })
      } else {
        alert('账号或密码不能为空')
      }
    },
    find () {
        if (this.email === '') {
          alert("邮箱不能为空！")
        } else if (this.email_code === '') {
          alert("请输入验证码！")
        } else {
          axios({
            method: "post",
            url: "/api/user/find/vertificationByEmail",
            data: {
              email_code: this.email_code,
              email: this.email
            }}).then(res=>{
            console.info(res.data)
            if(res.data.data != null) {
              console.info('yes')
              console.info(res.data.data.id)
              this.id = res.data.data.id
              this.phone = res.data.data.phone
              console.info(this.phone)
              console.info(this.password)
              this.showInfo()
            } else {
              console.info('no')
              alert('找回密码失败！')
            }
          })
        }
    },
    email_vertify () {
      const regMobile = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
      if (!regMobile.test(this.email)) {
        alert('邮箱格式不正确！')
        this.email = ''
      }
    },
    phone_vertify () {
      const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      if (!regMobile.test(this.phone)) {
        alert('手机号不正确！')
        this.phone = ''
      }
    },
    password_vertify () {
      if (this.password == '') {
        alert("请先输入密码！")
      } else if (this.req_password != this.password) {
        alert("密码不一致！")
        this.req_password = ''
      }
    },
    // 页面跳转时数据清空
    setNull () {
      this.phone = ''
      this.email = ''
      this.username = ''
      this.password = ''
      this.req_password = ''
      this.email_code = ''
      this.agreement = ''
      this.msgEmail = '获取验证码'
      this.flagEmail = false
    },
    cambiar_sign_up () {
      document.querySelector('.cont_forms').className = 'cont_forms cont_forms_active_sign_up'
      document.querySelector('.cont_form_sign_up').style.display = 'block'
      document.querySelector('.cont_form_login').style.opacity = '0'
      document.querySelector('.find_pwd_phone').style.opacity = '0'
      document.querySelector('.showInfo').style.opacity = '0'
      setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.opacity = '1'
      }, 100)
      setTimeout(function () {
        document.querySelector('.cont_form_login').style.display = 'none'
        document.querySelector('.showInfo').style.display = 'none'
        document.querySelector('.find_pwd_phone').style.display = 'none'
      }, 400)
    },
    // 之后的都是页面跳转
    cambiar_login () {
      document.querySelector('.cont_forms').className = 'cont_forms cont_forms_active_login'
      document.querySelector('.cont_form_login').style.display = 'block'
      document.querySelector('.cont_form_sign_up').style.opacity = '0'
      document.querySelector('.showInfo').style.opacity = '0'
      document.querySelector('.find_pwd_phone').style.opacity = '0'
      setTimeout(function () {
        document.querySelector('.cont_form_login').style.opacity = '1'
      }, 400)
      setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.display = 'none'
        document.querySelector('.showInfo').style.display = 'none'
        document.querySelector('.find_pwd_phone').style.display = 'none'
      }, 200)
    },
    ocultar_login_sign_up () {
      document.querySelector('.cont_forms').className = 'cont_forms'
      document.querySelector('.cont_form_sign_up').style.opacity = '0'
      document.querySelector('.cont_form_login').style.opacity = '0'
      document.querySelector('.find_pwd_phone').style.opacity = '0'
      document.querySelector('.showInfo').style.opacity = '0'
      document.querySelector('.cont_form_sign_up').style.display = 'none'
      document.querySelector('.cont_form_login').style.display = 'none'
      this.setNull()
      setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.display = 'none'
        document.querySelector('.cont_form_login').style.display = 'none'
        document.querySelector('.find_pwd_phone').style.display = 'none'
        document.querySelector('.showInfo').style.display = 'none'
      }, 500)
    },
    find_pwd_phone () {
      document.querySelector('.cont_forms').className = 'cont_forms cont_forms_active_find_pwd'
      document.querySelector('.find_pwd_phone').style.display = 'block'
      document.querySelector('.cont_form_login').style.opacity = '0'
      document.querySelector('.showInfo').style.opacity = '0'
      document.querySelector('.cont_form_sign_up').style.opacity = '0'
      this.setNull()
      setTimeout(function () {
        document.querySelector('.find_pwd_phone').style.opacity = '1'
      }, 100)
      setTimeout(function () {
        document.querySelector('.cont_form_login').style.display = 'none'
        document.querySelector('.cont_form_login').style.display = 'none'
        document.querySelector('.showInfo').style.display = 'none'
      }, 400)
    },
    showInfo () {
      document.querySelector('.cont_forms').className = 'cont_forms cont_forms_active_find_pwd'
      document.querySelector('.showInfo').style.display = 'block'
      document.querySelector('.cont_form_login').style.opacity = '0'
      document.querySelector('.find_pwd_phone').style.opacity = '0'
      document.querySelector('.cont_form_sign_up').style.opacity = '0'
      setTimeout(function () {
        document.querySelector('.showInfo').style.opacity = '1'
      }, 100)
      setTimeout(function () {
        document.querySelector('.cont_form_login').style.display = 'none'
        document.querySelector('.cont_form_login').style.display = 'none'
        document.querySelector('.find_pwd_phone').style.display = 'none'
      }, 400)
    },
    returnLogin () {
      document.querySelector('.cont_forms').className = 'cont_forms cont_forms_active_login'
      document.querySelector('.cont_form_login').style.display = 'block'
      document.querySelector('.cont_form_sign_up').style.opacity = '0'
      document.querySelector('.showInfo').style.opacity = '0'
      document.querySelector('.find_pwd_phone').style.opacity = '0'
      let phone = this.username
      this.setNull()
      this.phone = phone
      setTimeout(function () {
        document.querySelector('.cont_form_login').style.opacity = '1'
      }, 400)
      setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.display = 'none'
        document.querySelector('.showInfo').style.display = 'none'
        document.querySelector('.find_pwd_phone').style.display = 'none'
      }, 200)
    },
    // 设置cookie
    setCookie(phone, password, days) {
      let date = new Date(); // 获取时间
      date.setTime(date.getTime() + 7*24 * 60 * 60 * 1000 * days); // 保存的天数
      // 字符串拼接cookie
      window.document.cookie =
          "phone" + "=" + phone + ";path=/;expires=" + date.toGMTString();
      window.document.cookie =
          "password" + "=" + password + ";path=/;expires=" + date.toGMTString();
    },

    // 读取cookie 将用户名和密码回显到input框中
    getCookie() {
      if (document.cookie.length > 0) {
        let arr = document.cookie.split("; "); //分割成一个个独立的“key=value”的形式
        for (let i = 0; i < arr.length; i++) {
          let arr2 = arr[i].split("="); // 再次切割，arr2[0]为key值，arr2[1]为对应的value
          if (arr2[0] === "phone") {
            this.phone = arr2[1];
          } else if (arr2[0] === "password") {
            this.password = arr2[1];
            this.checked = true;
          }
        }
      }
    },
  }
}
</script>

<style scoped>
* {
  margin: 0px auto;
  padding: 0px;
  text-align: center;
  font-family: 'Lato', sans-serif;
}

.cotn_principal {
  position: absolute;
  width: 100%;
  height: 100%;
  /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#cfd8dc+0,607d8b+100,b0bec5+100 */
  background: #aac4bc; /* Old browsers */
  background: -moz-linear-gradient(-45deg,  #aac4bc 0%, #eca8a8 100%, #eed5a9 100%); /* FF3.6-15 */
  background: -webkit-linear-gradient(-45deg,  #aac4bc 0%,#eca8a8 100%,#eed5a9 100%); /* Chrome10-25,Safari5.1-6 */
  background: linear-gradient(135deg, #aac4bc 0%,#eca8a8 100%,#eed5a9 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
  filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#cfd8dc', endColorstr='#b0bec5',GradientType=1 ); /* IE6-9 fallback on horizontal gradient */

}

.cont_centrar {
  position: relative;
  float: left;
  width: 100%;

}

.cont_login {
  position: relative;
  width: 640px;
  left: 50%;
  margin-left: -320px;

}

.cont_back_info {
  position: relative;
  float: left;
  width: 640px;
  height: 280px;
  overflow: hidden;
  background-color: #fff;
  margin-top: 100px;
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
}
.content-header{
  height: 40px;
  margin-left: 20px;
  margin-right: 20px;
}
.content-header > a{
  height: 40px;
  width: 50%;
  display: inline-block;
  float: left;
  /*水平居中*/
  text-align: center;
}

.cont_forms {
  position: absolute;
  overflow: hidden;
  top:100px;
  left: 0px;
  width: 320px;
  height: 280px;
  background-color: #eee;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.cont_forms_active_login {
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
  height: 340px;
  top:65px;
  left: 0px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;

}
.cont_forms_active_find_pwd {
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
  height: 340px;
  top:65px;
  left: 0px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;

}

.cont_forms_active_sign_up {
  box-shadow: 1px 10px 30px -10px rgba(0,0,0,0.5);
  height: 620px;
  top:20px;
  left:320px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.cont_img_back_grey {
  position: absolute;
  width: 950px;
  top:-80px;
  left: -116px;
}

.cont_img_back_grey > img {
  width: 100%;
  -webkit-filter: grayscale(100%);     filter: grayscale(100%);
  opacity: 0.2;
  animation-name: animar_fondo;
  animation-duration: 20s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  animation-direction: alternate;

}

.cont_img_back_ {
  position: absolute;
  width: 950px;
  top:-80px;
  left: -116px;
}

.cont_img_back_ > img {
  width: 100%;
  opacity: 0.3;
  animation-name: animar_fondo;
  animation-duration: 20s;
  animation-timing-function: linear;
  animation-iteration-count: infinite;
  animation-direction: alternate;
}

.cont_forms_active_login > .cont_img_back_ {
  top:0px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}
.cont_forms_active_find_pwd > .cont_img_back_ {
  top:0px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.cont_forms_active_sign_up > .cont_img_back_ {
  top:0px;
  left: -435px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.cont_info_log_sign_up {
  position: absolute;
  width: 640px;
  height: 280px;
  top: 100px;
  z-index: 1;
}

.col_md_login {
  position: relative;
  float: left;
  width: 50%;
}

.col_md_login > h2 {
  font-weight: 400;
  margin-top: 70px;
  color: #757575;
}

.col_md_login > p {
  font-weight: 400;
  margin-top: 15px;
  width: 80%;
  color: #37474F;
}

.btn_login {
  background-color: #FF9800;
  border: none;
  padding: 10px;
  width: 200px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}
.btn_find {
  background-color: #FF9800;
  border: none;
  padding: 10px;
  width: 280px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}
.btn_return {
  background-color: #FF9800;
  border: none;
  padding: 10px;
  width: 250px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}
.btn_select {
  background-color: #FF9800;
  border: none;
  padding: 10px;
  width: 100px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 30px;
  cursor: pointer;
}
.col_md_sign_up {
  position: relative;
  float: left;
  width: 50%;
}

.cont_ba_opcitiy > h2 {
  font-weight: 400;
  color: #fff;
}

.cont_ba_opcitiy > p {
  font-weight: 400;
  margin-top: 15px;
  color: #fff;
}
.cont_ba_opcitiy > a {
  font-weight: 400;
  margin-top: 15px;
  color: #fff;
}
.cont_ba_opcitiy {
  position: relative;
  background-color: rgba(187, 168, 170, 0.79);
  width: 80%;
  border-radius:3px ;
  margin-top: 60px;
  padding: 15px 0px;
}

.btn_sign_up {
  background-color: #f44336;
  text-align: center;
  border: none;
  padding: 10px;
  width: 200px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}

.btn_send_code {
  background-color: #AAAAAA;
  border: none;
  padding: 10px;
  width: 90px;
  border-radius:3px;
  box-shadow: 1px 5px 20px -5px rgba(0,0,0,0.4);
  color: #fff;
  margin-top: 10px;
  cursor: pointer;
}
.cont_forms_active_sign_up {
  z-index: 2;
}

@-webkit-keyframes animar_fondo {
  from { -webkit-transform: scale(1) translate(0px);
    -moz-transform: scale(1) translate(0px);
    -ms-transform: scale(1) translate(0px);
    -o-transform: scale(1) translate(0px);
    transform: scale(1) translate(0px); }
  to { -webkit-transform: scale(1.5) translate(50px);
    -moz-transform: scale(1.5) translate(50px);
    -ms-transform: scale(1.5) translate(50px);
    -o-transform: scale(1.5) translate(50px);
    transform: scale(1.5) translate(50px); }
}
@-o-keyframes identifier {
  from { -webkit-transform: scale(1);
    -moz-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1); }
  to { -webkit-transform: scale(1.5);
    -moz-transform: scale(1.5);
    -ms-transform: scale(1.5);
    -o-transform: scale(1.5);
    transform: scale(1.5); }

}
@-moz-keyframes identifier {
  from { -webkit-transform: scale(1);
    -moz-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1); }
  to { -webkit-transform: scale(1.5);
    -moz-transform: scale(1.5);
    -ms-transform: scale(1.5);
    -o-transform: scale(1.5);
    transform: scale(1.5); }

}
@keyframes identifier {
  from { -webkit-transform: scale(1);
    -moz-transform: scale(1);
    -ms-transform: scale(1);
    -o-transform: scale(1);
    transform: scale(1); }
  to { -webkit-transform: scale(1.5);
    -moz-transform: scale(1.5);
    -ms-transform: scale(1.5);
    -o-transform: scale(1.5);
    transform: scale(1.5); }
}
.cont_form_login {
  position: absolute;
  opacity: 0;
  display: none;
  width: 320px;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}

.cont_forms_active_login {
  z-index: 2;
}
.cont_forms_active_login  >.cont_form_login {
}

.cont_form_sign_up {
  position: absolute;
  width: 320px;
  float: left;
  opacity: 0;
  display: none;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}
.cont_form_sign_up > form{}
form >  input {
  text-align: left;
  padding: 10px 5px;
  margin-left: 10px;
  margin-top: 15px;
  width: 210px;
  border: none;
  color: #757575;
}
.agree {
  border: none;
  margin: auto;
  font-weight: 400;
  color: #fff;
}
.cont_form_sign_up > h2 {
  margin-top: 50px;
  font-weight: 400;
  color: #757575;
}

.cont_form_login > input {
  text-align: left;
  padding: 10px 5px;
  margin-left: 10px;
  margin-top: 15px;
  width: 210px;
  border: none;
  color: #757575;
}

.cont_form_login > h2 {
  margin-top:60px;
  font-weight: 400;
  color: #757575;
}
.cont_form_login > a,.cont_form_sign_up > a  {
  color: #3333ff;
  position: relative;
  float: left;
  margin: 10px;
  margin-left: 30px;
}
.login_p {
  font-weight: 400;
  margin-top: 15px;
  color: #fff;
}
.login_a {
  font-weight: 400;
  margin-top: 20px;
  color: #fff;
  float: left;
  width: 50%;
  margin-left: auto;
  margin-right: auto;
}

.find_pwd {
  position: absolute;
  width: 320px;
  float: left;
  opacity: 0;
  display: none;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}
.cont_forms_active_find_pwd {
  z-index: 2;
}
.find_pwd_phone{
  position: absolute;
  width: 320px;
  float: left;
  opacity: 0;
  display: none;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}
.cont_forms_active_find_pwd  >.find_pwd_phone {
}
.find_pwd_phone >div > input {
  text-align: left;
  padding: 10px 5px;
  margin-left: 5px;
  margin-top: 15px;
  width: 210px;
  border: none;
  color: #757575;
}

.find_pwd_phone > h2 {
  margin-top: 50px;
  font-weight: 400;
  color: #757575;
}
.find_pwd_phone > a {
  color: #3333ff;
  position: relative;
  float: left;
  margin: 10px;
  margin-left: 30px;
}
.showInfo{
  position: absolute;
  width: 320px;
  float: left;
  opacity: 0;
  display: none;
  -webkit-transition: all 0.5s;
  -moz-transition: all 0.5s;
  -ms-transition: all 0.5s;
  -o-transition: all 0.5s;
  transition: all 0.5s;
}
.cont_forms_active_find_pwd  >.showInfo {
}
.showInfo > input {
  text-align: left;
  padding: 10px 5px;
  margin-left: 5px;
  margin-top: 15px;
  width: 170px;
  border: none;
  color: #757575;
}

.showInfo > h2 {
  margin-top: 80px;
  font-weight: 400;
  color: #757575;
}
.showInfo > a {
  color: #3333ff;
  position: relative;
  float: left;
  margin: 10px;
  margin-left: 30px;
}
.label-text {
  width: 70px;
  text-align: right;
  margin-top: 20px;
  display:inline-block;
  font-weight: 400;
  color: #757575;
}
.label-find {
  width: 60px;
  text-align: right;
  margin-top: 20px;
  display:inline-block;
  font-weight: 500;
  color: #fff;
  /*color: #757575;*/
}
.label-login {
  width: 40px;
  text-align: right;
  margin-top: 20px;
  display:inline-block;
  font-weight: 500;
  font-size: medium;
  color: #fff;
}
.label-find {
  width: 70px;
  text-align: right;
  margin-top: 20px;
  display:inline-block;
  font-weight: 500;
  font-size: medium;
  color: #fff;
}
</style>

