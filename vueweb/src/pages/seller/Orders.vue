<template>
  <div class="Orders">
    <header class="clear">
      <span>订单管理</span>
    </header>
    <Tag :tagList="tags" @indexChange="changeTag"/>
    <div class="content">
      <table class="ordersTable">
        <thead>
        <tr>
          <th>订单号</th>
          <th>用户</th>
          <th>收件人</th>
          <th>收货地址</th>
          <th>联系电话</th>
          <th>商品</th>
          <th>规格</th>
          <th>购买数量</th>
          <th>金额</th>
          <th>订单状态</th>
          <th>更新时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in orderList" :key="'order'+item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.username }}</td>
          <td>{{ item.receivername }}</td>
          <td>{{ item.receiveraddress }}</td>
          <td>{{ item.receiverphone }}</td>
          <td>{{ item.name }}</td>
          <td>{{ item.specName }}</td>
          <td>{{ item.quantity }}</td>
          <td>{{ item.totalprice }}</td>
          <td>{{ item.status === 1 ? '未付款' : item.status === 2 ? '未发货' : item.status === 3 ? '已发货' : item.status ===4 ? '已到货':'已取消' }}</td>
          <td>{{ item.time }}</td>
          <td>
            <button class="normal" @click="editOrder(item.id)">编辑</button>
            <!--            <button class="delete" @click="deleteOrder(item.id)">删除</button>-->
          </td>
        </tr>
        </tbody>
      </table>
      <div v-if="orderList.length==0" class="empty">
        <p>暂无任何数据...</p>
      </div>
    </div>
  </div>
</template>

<script>
import {getOrders} from '../../api/seller';
import Tag from '../../components/Tag';
import {mapState} from "vuex";

export default {
  name: 'Orders',
  components: {
    Tag
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
      tags: ['全部', '未付款', '未发货', '已发货', '已签收', '已取消'],
      orderList: []
    }
  },
  methods: {
    changeTag(index) {
      // console.log("商家id：",this.sellerId);
      const res = getOrders({sid: this.sellerId, status: index});
      res.then((orders) => {
        this.orderList = orders;
        console.log(this.orderList);
      }).catch((e) => {
        alert(e);
      })
    },
    editOrder(id) {
      this.$router.push('/backstage/orders/' + id)
    },
    //   deleteOrder(id) {
    //     const res = deleteOrder(id);
    //     res.then(() => {
    //       alert('删除成功');
    //       this.orderList.map((item, index) => {
    //         if (item.id === id) {
    //           this.orderList.splice(index, 1);
    //         }
    //       })
    //     }).catch((e) => {
    //       alert(e);
    //     })
    //   }
  },
  mounted() {
    this.changeTag(0);
  }
}
</script>

<style scoped lang="less">
@import "../../assets/css/var.less";

.Orders {
  header {
    width: 100%;
    height: 40px;
    line-height: 40px;

    span {
      float: left;
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

            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;

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