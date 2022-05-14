<template>
  <div class="EditAdmin">
    <header class="clear">
      <span>修改资料</span>
    </header>
    <div class="content">
      <div class="inputBox">
        <span>姓名：</span>
        <TextInput placeholder="请输入姓名" v-model="aName" :initText="aName"/>
      </div>
      <div class="inputBox">
        <span>账号：</span>
        <TextInput placeholder="请输入账号" v-model="account" :initText="account"/>
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
import {saveChange} from '../../api/admin';
import {mapState, mapMutations} from 'vuex';

export default {
  name: 'EditAdmin',
  components: {
    TextInput
  },
  computed: {
    ...mapState([
      'adminId',
      'adminName',
      'adminToken'
    ]),
  },
  data() {
    return {
      aName: '',
      account: '',
      oldPwd: '',
      newPwd: '',
      confirmPwd: ''
    }
  },
  methods: {
    ...mapMutations({
      adminLogout: 'ADMIN_LOGOUT',
    }),
    confirmChange() {
      const aName = this.aName + '';
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
          aName,
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
          this.adminLogout();
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

.EditAdmin {
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
