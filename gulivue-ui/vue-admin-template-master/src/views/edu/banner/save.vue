<template>
  <div class="app-container">
    添加Banner
    <el-form label-width="120px">
      <el-form-item label=" Banner信息">
        <el-input v-model="banner.title"/>
      </el-form-item>
      <el-form-item label="Banner排序">
        <el-input-number v-model="banner.sort" :min="0" controls-position="right"/>
      </el-form-item>
      <el-form-item label="linkUrl地址">
        <el-input v-model="banner.linkUrl"/>
      </el-form-item>
      <el-form-item label="Banner封面">

        <pan-thumb :image="banner.imageUrl+''"/>
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API+'/eduOss/file/'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"/>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="saveOrUpdate">保存
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import banner from '@/api/banner'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default ({
  components: { ImageCropper, PanThumb },
  data() {
    return {
      banner: {
        title: '',
        sort: 0,
        imageUrl: '',
        linkUrl: ''

      },
      // //上传弹框组件是否显示
      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件key值
      BASE_API: process.env.BASE_API // 获取dev.env.js里面地址

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
      this.banner.imageUrl = data.url
      this.imagecropperKey = this.imagecropperKey + 1
    },
    into() {
      // 判断是否有id
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getInfo(id)
      } else {
        // 清空表单
        this.banner = {}
      }
    },
    // 修改方法
    updateTeacher() {
      banner.updateBanner(this.banner)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({ path: '/banner/list' })
        })
    },
    // 添加方法
    saveBanner() {
      banner.addBanner(this.banner)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({ path: '/banner/list' })
        })
    },
    // 根据id获取信息
    getInfo(id) {
      banner.getBannerById(id)
        .then(response => {
          this.banner = response.data.item
        })
    },
    // 保存按钮
    saveOrUpdate() {
      // this.saveBtnDisabled = true
      // 判断是否有id
      if (!this.banner.id) {
        // 添加
        this.saveBanner()
      } else {
        // 修改
        this.updateTeacher()
      }
    }
  }
})
</script>
<style lang="scss">
.form-container {
  width: 700px;
  margin: 0 auto;

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;

    img {
      object-fit: contain; //（保持纵横比缩放图片，使图片的长边能完全显示出来）
    }
  }

  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }

  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
}
</style>
