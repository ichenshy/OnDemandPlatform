<template>
  <div class="app-container">
    讲师添加
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" :min="0" controls-position="right" />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar+''" />
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>
        <image-cropper v-show="imagecropperShow" :width="300" :height="300" :key="imagecropperKey" :url="BASE_API+'/eduOss/file/'" field="file" @close="close" @crop-upload-success="cropSuccess" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default ({
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: ''
      },
      // 上传弹框组件是否显示
      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件key值
      BASE_API: process.env.BASE_API, // 获取dev.env.js里面地址
      saveBtnDisabled: false // 保存按钮是否禁用,

    }
  },
  watch: {
    $route(to, from) {
      this.into()
    }
  },
  // 渲染后
  created() {
    this.into()
  },
  // 渲染前
  methods: {
    close() { // 关闭上传弹框的方法
      this.imagecropperShow = false
      // 上传组件初始化
      this.imagecropperKey = this.imagecropperKey + 1
    },
    // 上传成功方法
    cropSuccess(data) {
      this.imagecropperShow = false
      // 上传之后接口返回图片地址
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey + 1
    },
    into() {
      // 判断是否有id
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getInfo(id)
      } else {
        // 清空表单
        this.teacher = {}
      }
    },
    // 修改方法
    updateTeacher() {
      teacherApi.updateTeacher(this.teacher)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          }),
          this.$router.push({ path: '/teacher/list' })
        })
    },
    // 添加方法
    saveTeacher() {
      teacherApi.addTeacher(this.teacher)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          }),
          this.$router.push({ path: '/teacher/list' })
        }).catch(error => {
          this.$message({
            type: 'error',
            message: '该讲师已存在'
          }),
          this.$router.push({ path: '/teacher/list' })
        })
    },
    // 根据id获取信息
    getInfo(id) {
      teacherApi.getByid(id)
        .then(response => {
          this.teacher = response.data.item
        })
    },
    // 保存按钮
    saveOrUpdate() {
      this.saveBtnDisabled = true
      // 判断是否有id
      if (!this.teacher.id) {
        // 添加
        this.saveTeacher()
      } else {
        // 修改
        this.updateTeacher()
      }
    }
  }
})
</script>
