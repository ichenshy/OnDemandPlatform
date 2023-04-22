<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select v-model="courseInfo.subjectParentId" placeholder="请选择" @change="subjectLevelOneChanged">
          <el-option v-for="subject in subjectOneList" :key="subject.id" :label="subject.title" :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option v-for="subject in subjectTwoList" :key="subject.id" :label="subject.title" :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>
      <!-- 课程封面-->
      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/>
        元
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveUpdata">保存并下一步</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
import course from '@/api/course'
import subject from '@/api/subject'
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        title: '',
        subjectId: '',
        teacherId: '',
        subjectParentId: '',
        lessonNum: 0,
        description: '',
        cover: process.env.OSS_PATH + '/1.jpg',
        price: 0
      },
      conid: '', // 课程id
      teacherList: [],
      subjectOneList: [],
      subjectTwoList: [],
      BASE_API: process.env.BASE_API // 接口API地址
    }
  },
  watch: {
    $route(to, from) {
      this.into()
      this.description = ''
    }
  },
  created() {
    // 获取id
    if (this.$route.params && this.$route.params.id) {
      this.conid = this.$route.params.id
      this.getInfo()
      this.finAll()
    } else {
      this.finAll()
      this.getList()
    }
  },
  methods: {
    into() {
      if (this.$route.params && this.$route.params.id) {
        this.conid = this.$route.params.id
        this.getInfo()
        this.finAll()
      } else {
        this.finAll()
        this.getList()
        this.courseInfo = {}
        this.courseInfo.cover = process.env.OSS_PATH + '/1.jpg'
      }
    },
    getInfo() {
      course.getCourseInfo(this.conid)
        .then(response => {
          this.courseInfo = response.data.courseInfoVo
          subject.getSubjectList()
            .then(response => {
              this.subjectOneList = response.data.list

              for (var i = 0; i < this.subjectOneList.length; i++) {
                var onesubject = this.subjectOneList[i]
                if (this.courseInfo.subjectParentId === onesubject.id) {
                  this.subjectTwoList = onesubject.children
                }
              }
            })
        })
    },
    handleAvatarSuccess(res, file) {
      console.log(res)// 上传响应
      this.courseInfo.cover = res.data.url
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      return isJPG
    },

    subjectLevelOneChanged(value) {
      for (var i = 0; i < this.subjectOneList.length; i++) {
        var oneSubject = this.subjectOneList[i]
        if (value === oneSubject.id) {
          this.subjectTwoList = oneSubject.children
          // this.courseInfo.subjectId = ''
        }
        this.courseInfo.subjectId = ''
      }
    },
    getList() {
      subject.getSubjectList()
        .then(response => {
          this.subjectOneList = response.data.list
        })
    },
    finAll() {
      course.findAll()
        .then(response => {
          this.teacherList = response.data.item
        })
    },
    addData() {
      course.addCourse(this.courseInfo)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加课程成功'
          })
          this.$router.push({ path: '/course/chapter/' + response.data.courseId })
        })
    },
    upData() {
      course.updateCourseInfo(this.courseInfo)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改课程成功'
            // eslint-disable-next-line no-sequences
          })
          this.$router.push({ path: '/course/chapter/' + this.conid })
        })
    },
    saveUpdata() {
      if (!this.courseInfo.id) {
        // 添加
        this.addData()
      } else {
        // 修改
        this.upData()
      }
    }
  }
}
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>
