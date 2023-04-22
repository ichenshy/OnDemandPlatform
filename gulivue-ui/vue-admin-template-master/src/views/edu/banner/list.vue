<template>
  <div class="app-container">
    Banner列表
    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row row-class-name="myClassList">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="Banner信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.imageUrl" alt="scope.row.imageUrl" width="150px">
            </div>
            <div class="title">
              <h4 href="">{{ scope.row.title }}</h4>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="60"/>
      <el-table-column prop="linkUrl" label="地址" width="100"/>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/banner/edit/'+scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit">编辑</el-button>
          </router-link>
          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"/>

  </div>
</template>
<script>
import banner from '@/api/banner'

export default ({
  data() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      sort: 0,
      bannerQuery: {}
    }
  },
  // 渲染后
  created() {
    this.getList()
  },
  // 渲染前
  methods: {
    getList(page = 1) {
      this.page = page
      banner.pageBanner(this.page, this.limit)
        .then(response => {
          this.list = response.data.rows
          this.total = response.data.total
        })
    },
    // 清空
    resetData() {
      this.bannerQuery = {}
      this.getList()
    },
    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        banner.deleteBanner(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getList()
          })
      })
    }
  }
})
</script>

<style scoped>
.myClassList .info {
  width: 450px;

  overflow: hidden;
}

.myClassList .info .pic {
  width: 150px;

  height: 90px;

  overflow: hidden;

  float: left;
}

.myClassList .info .pic a {
  display: block;

  width: 100%;

  height: 100%;

  margin: 0;

  padding: 0;
}

.myClassList .info .pic img {
  display: block;

  width: 100%;
}

.myClassList td .info .title {
  width: 280px;

  float: right;

  height: 90px;
}

.myClassList td .info .title a {
  display: block;

  height: 48px;

  line-height: 24px;

  overflow: hidden;

  color: #00baf2;

  margin-bottom: 12px;
}

.myClassList td .info .title p {
  line-height: 20px;

  margin-top: 5px;

  color: #818181;
}
</style>
