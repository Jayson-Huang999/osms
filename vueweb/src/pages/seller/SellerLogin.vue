<template>
  <div class="SellerLogin" :style="{width:width+'px',height:height+'px'}">
    <div class="content">
      <h3>MoreMall</h3>
      <P>店铺后台管理系统</P>
      <div class="tag">
        <span @click="setIndex(0)" :class="{selected:curIndex===0}">登录</span>
        <span @click="setIndex(1)" :class="{selected:curIndex===1}">入驻</span>
      </div>
      <div class="formBox" v-show="curIndex===0">
        <input ref="account" type="text" placeholder="账号"/>
        <input ref="pwd" type="password" placeholder="密码"/>
        <button @click="login">登录</button>
      </div>
      <div class="formBox" v-show="curIndex===1">
        <input ref="signName" type="text" placeholder="店铺名"/>
        <input ref="signAddress" type="text" placeholder="店铺地址"/>
        <input ref="signPhone" type="text" placeholder="联系电话"/>
        <input ref="signAccount" type="text" placeholder="账户"/>
        <input ref="signPassword" type="password" placeholder="密码"/>
        <input ref="signPasswordAgain" type="password" placeholder="确认密码"/>
        <button @click="signup">申请</button>
      </div>
    </div>
  </div>
</template>

<script>
import {mapMutations} from 'vuex'
import {getClientSize} from '../../util/util';
import {login, signup} from '../../api/seller';

export default {
  name: 'SellerLogin',
  computed: {
    width() {
      return getClientSize().width;
    },
    height() {
      return getClientSize().height;
    },
  },
  data() {
    return {
      curIndex: 0
    }
  },
  methods: {
    ...mapMutations({
      setSellerId: 'SET_SELLER_ID',
      setSellerName: 'SET_SELLER_NAME',
      setSellerToken: 'SET_SELLER_TOKEN'
    }),
    setIndex(index) {
      if (index === this.curIndex) {
        return;
      }
      this.curIndex = index;
    },
    login() {
      console.log(this);
      const account = this.$refs.account.value;
      const pwd = this.$refs.pwd.value;
      const res = login({
        account: account,
        password: pwd
      });
      res.then((data) => {
        this.setSellerId(data.id);
        this.setSellerName(data.name);
        this.setSellerToken(data.token);
        this.$router.push('/backstage');
      }).catch((e) => {
        alert(e)
      })
    },
    signup() {
      if (this.$refs.signPassword.value != this.$refs.signPasswordAgain.value) {
        alert("密码不匹配！");
        this.$refs.signPassword.value = '';
        this.$refs.signPasswordAgain.value = '';
        return;
      }
      if (this.$refs.signName.value.trim()==""){
        alert("店铺名不能为空！");
        return;
      }
      if (this.$refs.signAddress.value.trim()==""){
        alert("店铺地址不能为空！");
        return;
      }
      if (this.$refs.signPhone.value.trim()==""){
        alert("电话不能为空！");
        return;
      }
      if (this.$refs.signAccount.value.trim()==""){
        alert("申请的账号不能为空！");
        return;
      }
      if (this.$refs.signPassword.value.trim()==""){
        alert("密码不能为空！");
        return;
      }
      const res = signup({
        name: this.$refs.signName.value,
        location: this.$refs.signAddress.value,
        phone: this.$refs.signPhone.value,
        account: this.$refs.signAccount.value,
        password: this.$refs.signPassword.value,
        onlinetime: new Date(),
        status:1
      });
      res.then((data) => {
        console.log(data);
        alert(data);
      }).catch((e) => {
        alert(e)
      })
    }
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.SellerLogin {
  background-color: @bgColor;
  position: relative;

  .content {
    width: 300px;
    position: absolute;
    top: 50%;
    left: 50%;
    margin-top: -260px;
    margin-left: -150px;
    text-align: center;
    overflow: hidden;

    h3 {
      color: @secondColor;
      font-size: 50px;
    }

    p {
      margin-top: 20px;
      color: @fontDefaultColor;
      margin-bottom: 20px;
    }

    .tag {
      margin-top: 20px;
      color: @fontDefaultColor;
      margin-bottom: 20px;

      span {
        display: inline-block;
        width: 50px;
        text-align: center;
        margin: 0 10px;
        padding: 10px 0;
        cursor: pointer;
      }

      .selected {
        border-bottom: 2px solid @secondColor;
        color: @secondColor
      }
    }

    input {
      border-radius: 0;
      box-shadow: none;
      background: #fff;
      padding: 14px;
      width: 80%;
      border: 1px solid @borderColor;
      margin-bottom: 10px;
    }

    button {
      width: 90%;
      background: @secondColor;
      box-shadow: none;
      border: 0;
      border-radius: 3px;
      line-height: 41px;
      color: #fff;
      cursor: pointer;
      margin-top: 20px;
    }
  }
}
</style>
