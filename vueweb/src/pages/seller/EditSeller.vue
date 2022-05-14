<template>
  <div class="EditSeller">
    <header class="clear">
      <span>修改资料</span>
    </header>
    <div class="content">
      <div class="inputBox">
        <span>店名：</span>
        <TextInput placeholder="请输入店名" v-model="sName" :initText="sName"/>
      </div>
      <div class="inputBox">
        <span>地址：</span>
        <TextInput placeholder="请输入地址" v-model="sLocation" :initText="sLocation"/>
      </div>
      <div class="inputBox">
        <span>电话：</span>
        <TextInput placeholder="请输入phone" v-model="phone" :initText="phone"/>
      </div>
      <div class="inputBox">
        <span>账号：</span>
        <TextInput placeholder="请输入account" v-model="account" :initText="account"/>
      </div>
      <div class="inputBox">
        <span>原密码：</span>
        <TextInput placeholder="请输入原密码" v-model="oldPwd" type="password"/>
      </div>
      <div class="inputBox">
        <span>新密码：</span>
        <TextInput placeholder="请输入新密码" v-model="newPwd" type="password"/>
      </div>
      <div class="inputBox">
        <span>确认新密码：</span>
        <TextInput placeholder="请再输入一次新密码" v-model="confirmPwd" type="password"/>
      </div>
      <button @click="confirmChange">确认修改</button>
    </div>
  </div>
</template>

<script>
import TextInput from '../../components/TextInput';
import {saveChange} from '../../api/seller';
import {mapState, mapMutations} from 'vuex';

export default {
  name: 'EditSeller',
  components: {
    TextInput
  },
  computed: {
    ...mapState([
      'sellerId',
      'sellerName',
      'sellerToken'
    ]),
  },
  data() {
    return {
      sName: '',
      sLocation:'',
      phone: '',
      account: '',
      oldPwd: '',
      newPwd: '',
      confirmPwd: ''
    }
  },
  methods: {
    ...mapMutations({
      sellerLogout: 'SELLER_LOGOUT',
    }),
    confirmChange() {
      const sName = this.sName + '';
      const sLocation=this.sLocation+'';
      const phone = this.phone + '';
      const account = this.account;
      const oldPwd = this.oldPwd + '';
      const newPwd = this.newPwd + '';
      const confirmPwd = this.confirmPwd + '';
      if (newPwd !== confirmPwd) {
        alert("请再次确认新密码！");
        this.newPwd = '';
        this.confirmPwd = '';
      } else {
        const res = saveChange({
          sName,
          sLocation,
          phone,
          account,
          oldPwd,
          newPwd
        });
        res.then((res) => {
          this.oldPwd = '';
          this.newPwd = '';
          this.confirmPwd = '';
          console.log(res);
          alert(res);
          this.sellerLogout();
          alert("即将跳转登录页面！");
          this.$router.push('/login');
        }).catch((e) => {
          alert(e);
        })
      }
    }
  },

  mounted() {
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.EditSeller {
  header {
    width: 100%;
    height: 40px;
    line-height: 40px;

    span {
      float: left;
    }
  }

  .content {
    margin-top: 20px;
    width: 290px;
    text-align: center;

    .inputBox {
      text-align: left;
      margin-bottom: 20px;

      span {
        color: @fontDefaultColor;
        font-size: 13px;
        display: inline-block;
        width: 90px;
      }

      input {
      }
    }

    button {
      background-color: #337da4;
      color: white;
      border: none;
      width: 80px;
      height: 30px;
      border-radius: 5px;
      cursor: pointer;
    }
  }
}
</style>
