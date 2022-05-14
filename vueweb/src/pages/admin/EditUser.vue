<template>
  <div class="EditUser">
    <header class="clear">
      <span>用户管理</span>
      <div><input ref="input" type="text" placeholder="搜索用户"/>
        <button @click="searchUser"><i class="iconfont icon-search"/></button>
      </div>
    </header>
    <table className="userTable">
      <thead>
      <tr>
        <th>ID</th>
        <th>姓名</th>
        <th>性别</th>
        <th>昵称</th>
        <th>邮箱</th>
        <th>电话</th>
        <th>积分</th>
        <th>等级</th>
        <th>创建时间</th>
        <th>更新时间</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item,index) in userList" :key="'user'+item.id">
        <td>{{ item.id }}</td>
        <td>{{ item.username }}</td>
        <td v-if="item.sex">男</td>
        <td v-else>女</td>
        <td>{{ item.nickname }}</td>
        <td>{{ item.email }}</td>
        <td>{{ item.phone }}</td>
        <td>{{ item.score }}</td>
        <td>{{ item.level }}</td>
        <td>{{ convertDate(new Date(item.createtime)) }}</td>
        <td>{{ convertDate(new Date(item.updatetime)) }}</td>
        <td>
          <button class="delete" @click="deleteUser(item.id)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>
    <div v-if="userList.length==0" class="empty">
      <p>暂无任何数据...</p>
    </div>

  </div>
</template>

<script>
import {getAllUser, getSearchUser, deleteUser} from '../../api/admin';
import {convertDate} from '../../util/util'

export default {
  name: 'EditUser',
  computed: {},
  data() {
    return {
      userList: [],
      convertDate
    }
  },
  mounted() {
    const res = getAllUser();
    res
        .then((users) => {
          this.userList = users;
        })
        .catch((e) => {
          alert(e)
        })
  },
  methods: {
    deleteUser(id) {
      const res = deleteUser(id);
      res
          .then(() => {
            alert('删除成功');
            this.userList.map((item, index) => {
              if (item.id === id) {
                this.userList.splice(index, 1);
              }
            })
          })
          .catch((e) => {
            alert(e);
          })
    },
    searchUser() {
      const val = this.$refs.input.value;
      const res = getSearchUser(val);
      res
          .then((data) => {
            this.userList = data;
          })
          .catch((e) => {
            alert(e);
          })
    },
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.EditUser {
  header {
    width: 100%;
    height: 40px;
    line-height: 40px;

    span {
      float: left;
    }

    div {
      height: 20px;
      float: right;

      input {
        border: none;
        border-bottom: 1px solid #337da4;
        background-color: rgba(0, 0, 0, 0);
        width: 180px;
        padding-left: 10px;
      }

      button {
        position: relative;
        top: -1px;
        border: none;
        background-color: rgba(0, 0, 0, 0);

        i {
          font-size: 17px;
          color: #337da4;
        }
      }
    }
  }
}

.empty {
  width: 100%;
  text-align: center;
  margin: 0 auto;
  padding-top: 36px;
  padding-bottom: 21px;
  color: #bdbaba;
  font-size: 18px;
}
</style>
