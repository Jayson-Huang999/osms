<template>
  <div class="EditSeller">
    <header class="clear">
      <span>商家管理</span>
      <div><input ref="input" type="text" placeholder="搜索店铺"/>
        <button @click="searchSeller"><i class="iconfont icon-search"/></button>
      </div>
    </header>
    <Tag :tagList="tags" @indexChange="changeTag"/>
    <div class="content">
      <table className="sellerTable">
        <thead>
        <tr>
          <th>ID</th>
          <th>店铺名</th>
          <th>店铺地址</th>
          <th>电话</th>
          <th>账号</th>
          <th>密码</th>
          <th>创建时间</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in sellerList" :key="'seller'+item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.location }}</td>
          <td>{{ item.phone }}</td>
          <td>{{ item.account }}</td>
          <td type="password">{{ item.password }}</td>
          <td>{{ convertDate(new Date(item.onlinetime)) }}</td>
          <td>
            <button class="normal" @click="changeSellerByID(item.id,1)" v-if="status===1">通过</button>
            <button class="delete" @click="changeSellerByID(item.id,2)" v-if="status===1">否决</button>
            <button class="delete" @click="changeSellerByID(item.id,3)" v-if="status===2">注销</button>
            <button class="delete" @click="changeSellerByID(item.id,4)" v-if="status===3">重新审核</button>
          </td>
        </tr>
        </tbody>
      </table>
      <div v-if="sellerList.length==0" class="empty">
        <p>暂无任何数据...</p>
      </div>
    </div>
  </div>
</template>

<script>
import {getSearchSeller, changeSellerByID,getAllSellersByStatus} from '../../api/admin';
import {convertDate} from '../../util/util'
import Tag from '../../components/Tag';

export default {
  name: 'EditSeller',
  components: {
    Tag
  },
  computed: {},
  data() {
    return {
      sellerList: [],
      tags: ['全部', '待审核', '已通过', '未通过', '已注销'],
      status:0,
      convertDate
    }
  },
  mounted() {
    this.changeTag(0);
  },
  methods: {
    changeTag(index) {
      this.sellerList=[];
      this.status=index;
      const res = getAllSellersByStatus(index);
      res.then((res) => {
        this.sellerList = res;
      }).catch((e) => {
        alert(e)
      })
    },
    changeSellerByID(id,flag) {
      const res = changeSellerByID({id:id,flag:flag});
      res.then(() => {
        alert('操作成功');
        this.sellerList.map((item, index) => {
          if (item.id === id) {
            this.sellerList.splice(index, 1);
          }
        })
      }).catch((e) => {
        alert(e);
      })
    },
    searchSeller() {
      const val = this.$refs.input.value;
      const res = getSearchSeller({"status":this.status,"keyword":val});
      res.then((data) => {
        this.sellerList = data;
        if (data.length==0){
          alert("未查到任何信息！");
        }
      }).catch((e) => {
        alert(e);
      })
    },
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

  .empty {
    width: 100%;
    text-align: center;
    margin: 0 auto;
    padding-top: 36px;
    padding-bottom: 21px;
    color: #bdbaba;
    font-size: 18px;
  }

  .content {
    width: 100%;
    background-color: white;
    position: relative;
    top: -3px;
    padding: 5px;

    .ordersTable {
      width: 100%;

      th {
        text-align: center;
      }

      tbody {
        tr {
          td {
            max-width: 100px;
            min-width: 30px;
            text-align: center;

            button {
              display: block;
              overflow: hidden;
              margin-bottom: 5px;
            }
          }
        }
      }
    }
  }
}
</style>
