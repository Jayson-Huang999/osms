<template>
  <div class="favorite" id="favorite" name="favorite">
    <div class="favorite-layout">
      <div class="favorite-content">
        <div class="extra"></div>
        <div class="favorite-title">
          <p>我的收藏</p>
        </div>
        <div v-if="collectList.length>0">
          <div class="goods-list">
            <MyList :list="collectList" :isDelete="true"></MyList>
          </div>
          <!-- 分页 -->
          <div class="pagination">
            <el-pagination
                background
                @current-change="handleCurrentChange"
                :page-size="pageSize"
                layout="total, prev, pager, next, jumper"
                :total="total"
            ></el-pagination>
          </div>
          <div class="extra"></div>
          <div class="extra"></div>
          <!-- 分页END -->
        </div>
        <!-- 订单为空的时候显示的内容 -->
        <div v-else class="empty">
          <p>您暂未收藏任何商品</p>
        </div>
        <!-- 订单为空的时候显示的内容END -->
      </div>


    </div>
  </div>
</template>
<script>
import MyList from '../../components/MyList';
import {getFavoriteProducts, showFavorites} from '../../api/client';

export default {
  name: 'Favorite',
  data() {
    return {
      collectList: [],
      pageSize: 8,
      total: 0,
      start: 0,
      limit: 8
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.start = this.limit * (val - 1) // val 页面
      this.getFavorites()
    },
    getFavorites() {
      // 获取收藏数据
      const clientId=this.$store.getters.clientId
      const start=(this.start/this.limit)+1;
      const limit=this.limit;
      getFavoriteProducts({clientId,start,limit})
          .then(res => {
            this.collectList = res.list
            this.total = res.total
          })
          .catch(err => {
            this.notifyError('获取收藏夹失败', err)
          })
    }
  },
  activated() {
    this.getFavorites()
  },
  mounted() {
    this.getFavorites()
  },
  components: {
    MyList
  }
}
</script>
<style scoped>
.favorite-layout {
  max-width: 1225px;
  margin: 0 auto;
}
.favorite-content {
  background-color: #ffffff;
  margin-bottom: 30px;
}
.favorite-title {
  height: 100px;
  display: flex;
  align-items: center;
}
.favorite-title p {
  font-size: 30px;
  color: #757575;
  margin-left: 50px;
}
.extra {
  height: 10px;
}
.goods-list {
  overflow: hidden;
}
.favorite-content .pagination {
  width: 300px;
  margin: 0 auto;
  margin-top: 20px;
}
.empty {
  width: 200px;
  margin: 0 auto;
  padding-top: 20px;
  padding-bottom: 70px;
  color: #bdbaba;
  font-size: 18px;
}
</style>