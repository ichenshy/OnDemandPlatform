<template>
  <div class="app-container">
    讲师列表

    <!--查询表单-->

    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker v-model="teacherQuery.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="teacherQuery.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column label="头像" width="150" align="center">
        <template slot-scope="scope">
          <div class="pic">
            <img :src="scope.row.avatar" alt="scope.row.avatar" width="150px">
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="姓名" label="姓名" width="80">
        <template slot-scope="scope">
          <div class="title">
            <h5 href="">{{ scope.row.name }}</h5>
          </div>
        </template>
      </el-table-column>

      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>

      </el-table-column>
      <el-table-column prop="intro" label="资历" />
      <el-table-column prop="gmtCreate" label="添加时间" width="160" />
      <el-table-column prop="sort" label="排序" width="60" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" background style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper" @current-change="getList" />
  </div>
</template>
<script>
import teacher from '@/api/teacher'
export default ({
  data() {
    return {
      list: null,
      page: 1,
      limit: 5,
      total: 0,
      teacherQuery: {}
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
      teacher.pageTeachCondtion(this.page, this.limit, this.teacherQuery)
        .then(response => {
          this.list = response.data.rows
          this.total = response.data.total
        })
        .catch(error => {
          console.log(error)
        })
    },
    // 清空
    resetData() {
      this.teacherQuery = {}
      this.getList()
    },
    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除该讲师记录, 是否继续?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        teacher.removeteach(id)
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
