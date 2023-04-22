<template>
  <div class="app-container">
    课程列表

    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="courseQuery.price" clearable placeholder="是否免费">
          <el-option :value="0" label="免费" />
          <!-- TODO -->
          <el-option :value="3" label="付费" />
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker v-model="courseQuery.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="courseQuery.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row row-class-name="myClassList">
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px">
            </div>
            <div class="title">
              <a href="">{{ scope.row.title }}</a>
              <p>{{ scope.row.lessonNum }}课时</p>
            </div>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center">

        <template slot-scope="scope">

          {{ scope.row.gmtCreate.substr(0, 10) }}

        </template>

      </el-table-column>

      <el-table-column label="发布时间" align="center">

        <template slot-scope="scope">

          {{ scope.row.gmtModified.substr(0, 10) }}

        </template>

      </el-table-column>

      <el-table-column label="价格" width="100" align="center">

        <template slot-scope="scope">

          {{ Number(scope.row.price) === 0 ? '免费' :

          '¥' + scope.row.price.toFixed(2) }}

        </template>

      </el-table-column>

      <el-table-column prop="buyCount" label="付费学员" width="100" align="center">

        <template slot-scope="scope">

          {{ scope.row.buyCount }}人

        </template>

      </el-table-column>

      <el-table-column label="操作" width="150" align="center">

        <template slot-scope="scope">

          <router-link :to="'/course/info/'+scope.row.id">
            <!-- <router-link :to="'/course/info/1468194724896563201'"> -->

            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程信息</el-button>

          </router-link>

          <router-link :to="'/course/chapter/'+scope.row.id">
            <!-- <router-link :to="'/course/chapter/1468194931994517506'"> -->

            <el-button type="text" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>

          </router-link>

          <el-button type="text" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>

        </template>

      </el-table-column>

    </el-table>
    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper" @current-change="getList" />

  </div>
</template>
<script>
import course from '@/api/course'
export default ({
  data() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      courseQuery: {}
    }
  },
  // 渲染后
  created() {
    this.getList()
  },
  // 渲染前
  methods: {
    getList(page = 1) {
      // alert(this.page)
      this.page = page
      course.pagePushlish(this.page, this.limit, this.courseQuery)
        .then(response => {
          this.list = response.data.rows
          this.total = response.data.total
        })
    },
    // 清空
    resetData() {
      this.courseQuery = {}
      this.getList()
    },
    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        course.deleCourse(id)
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
