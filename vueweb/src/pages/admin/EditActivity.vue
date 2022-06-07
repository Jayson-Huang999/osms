<template>
  <div class="EditActivity">
    <header class="clear">
      <span>活动审核</span>
      <div><input ref="input" type="text" placeholder="搜索活动"/>
        <button @click="searchActivity"><i class="iconfont icon-search"/></button>
      </div>
    </header>
    <Tag :tagList="tags" @indexChange="changeTag"/>
    <div class="content">
      <table class="activityTable">
        <thead>
        <tr>
          <th>活动ID</th>
          <th>商品名称</th>
          <th>活动折扣</th>
          <th>活动数量</th>
          <th>开始时间</th>
          <th>结束时间</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item,index) in activityList" :key="'activity'+item.id">
          <td>{{ item.id }}</td>
          <td>{{ item.productName }}</td>
          <td>{{ item.preferentiallevel }}%</td>
          <td>{{ item.remain }}</td>
          <td>{{ convertDate(new Date(item.starttime)) }}</td>
          <td>{{ convertDate(new Date(item.endtime)) }}</td>
          <td>
            <button class="normal" @click="changeActivityByID(item.id,1)" v-if="status===0">通过</button>
            <button class="delete" @click="changeActivityByID(item.id,2)" v-if="status===0">否决</button>
            <button class="delete" @click="changeActivityByID(item.id,3)" v-if="status===1">撤销</button>
            <button class="normal" @click="changeActivityByID(item.id,4)" v-if="status===2">恢复</button>
          </td>
        </tr>
        </tbody>
      </table>
      <div v-if="activityList.length==0" class="empty">
        <p>暂无任何数据...</p>
      </div>
    </div>
  </div>
</template>

<script>
import {changeActivityByID,getActivityByStatus,getSearchActivity} from '../../api/admin';
import {convertDate} from '../../util/util'
import Tag from '../../components/Tag';

export default {
  name: 'EditActivity',
  components: {
    Tag
  },
  computed: {},
  data() {
    return {
      activityList: [],
      tags: [ '待审核', '已通过', '未通过'],
      status:0,
      convertDate
    }
  },
  mounted() {
    this.changeTag(0);
  },
  methods: {
    changeTag(index) {
      this.activityList=[];
      this.status=index;
      const res = getActivityByStatus(index);
      res.then((res) => {
        this.activityList = res;
        console.log(res);
      }).catch((e) => {
        alert(e)
      })
    },
    changeActivityByID(id,flag) {
      const res = changeActivityByID({id:id,flag:flag});
      res.then(() => {
        alert('操作成功');
        this.activityList.map((item, index) => {
          if (item.id === id) {
            this.activityList.splice(index, 1);
          }
        })
      }).catch((e) => {
        alert(e);
      })
    },
    searchActivity() {
      const val = this.$refs.input.value;
      const res = getSearchActivity({"status":this.status,"keyword":val});
      res.then((data) => {
        this.activityList = data;
        console.log(data);
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

.EditActivity {
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

    .activityTable {
      width: 100%;

      th {
        text-align: center;
      }

      tbody {
        tr {
          td {
            max-width: 60px;
            min-width: 30px;
            text-align: center;

            text-align:center;
            vertical-align:middle;

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
