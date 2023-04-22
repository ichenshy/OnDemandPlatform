<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" @click="onAdd">添加章节</el-button>

    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="open(chapter.id)">添加课时</el-button>
            <el-button style="" type="text" @click="openEdit(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>

          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">

          <li v-for="video in chapter.children" :key="video.id">

            <p>{{ video.title }}

              <span class="acts">
                <el-button type="text" @click="openVideoEdit(video.id)">编辑</el-button>
                <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <el-form label-width="120px">
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="last">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
      </el-form-item>
    </el-form>

    <!-- ========================添加章节================================== -->
    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>

    <!-- ==========================添加小节================================= -->
    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">收费</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-success="handleVodUploadSuccess"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduVod/video/uploadAlyiVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import chapter from '@/api/chapter'
import eduVideo from '@/api/eduVideo'

export default {
  data() {
    return {
      saveBtnDisabled: false,
      chapterVideoList: [],
      // 课程id
      courseId: '',
      dialogChapterFormVisible: false,
      chapter: {
        titleL: '',
        sort: 0
      },
      video: {
        title: '',
        sort: 0,
        isFree: 0,
        videoSourceId: '',
        videoOriginalName: ''

      },
      dialogVideoFormVisible: false,
      fileList: [], // 上传文件列表
      BASE_API: process.env.BASE_API // 接口API地址

    }
  },
  created() {
    // 获取id
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      // 查询章节与小节
      this.ChapterVideo()
    }
  },
  methods: {
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确认移除${file.name}?`)
    },
    handleVodRemove() {
      // 删除视频 调用接口
      eduVideo.removeAlyVideo(this.video.videoSourceId)
        .then(response => {
          this.$message({
            type: 'success',
            message: response.message
          })
          this.fileList = []
          this.video.videoSourceId = ''
          this.video.videoOriginalName = ''
        })
    },
    handleVodUploadSuccess(response, file, fileList) {
      this.video.videoSourceId = response.data.vodId
      this.video.videoOriginalName = file.name
    },
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },
    open(chapterId) {
      this.fileList = []
      this.dialogVideoFormVisible = true
      this.video = {}
      this.video.chapterId = chapterId
      this.video.sort = 0
    },
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.saveAdd()
      } else {
        this.updateVideo()
      }
      // this.saveAdd()
    },
    // 修改小节  数据回显
    openVideoEdit(chapterId) {
      this.dialogVideoFormVisible = true
      // 查询接口
      eduVideo.getVideoId(chapterId)
        .then(response => {
          // this.chapter = response.data.eduChapter
          this.video = response.data.eduVideo
          this.fileList = [{ 'name': this.video.videoOriginalName }]
        })
    },

    // 修改小节
    updateVideo() {
      eduVideo.upDataVideo(this.video)
        .then(response => {
          // 关闭弹窗
          this.dialogVideoFormVisible = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改小节成功!'
          })
          // 刷新页面(重新获取一遍)
          this.ChapterVideo()
        })
    },
    // 小节删除
    deleteVideo(chapterId) {
      this.$confirm('此操作将永久删除该课时, 是否继续?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        eduVideo.deleteChapterVideoId(chapterId)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            // 刷新页面
            this.ChapterVideo()
          })
      })
    },
    // 小节添加
    saveAdd() {
      this.video.courseId = this.courseId
      eduVideo.addVideo(this.video)
        .then(response => {
          // 关闭弹窗
          this.dialogVideoFormVisible = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '添加课时成功!'
          })
          // 刷新页面(重新获取一遍)
          this.ChapterVideo()
        })
    },
    // 删除
    removeChapter(chapterId) {
      this.$confirm('此操作将永久删除该章节, 是否继续?', '提示', {
        cancelButtonText: '取消',
        confirmButtonText: '确定',
        type: 'warning'
      }).then(() => {
        chapter.deleteChapterVideoId(chapterId)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除  成功!'
            })
            // 刷新页面
            this.ChapterVideo()
          })
      })
    },
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.addChapter()
      } else {
        this.updateChapter()
      }
    },
    // 修改章节  数据回显
    openEdit(chapterId) {
      this.dialogChapterFormVisible = true
      // 查询接口
      chapter.getChapterVideoId(chapterId)
        .then(response => {
          // this.chapter = response.data.eduChapter
          this.chapter = response.data.eduChapter
        })
    },

    // 修改章节
    updateChapter() {
      chapter.upDataChapterVideo(this.chapter)
        .then(response => {
          // 关闭弹窗
          this.dialogChapterFormVisible = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改章节成功!'
          })
          // 刷新页面(重新获取一遍)
          this.ChapterVideo()
        })
    },

    // 清空数据
    onAdd() {
      this.dialogChapterFormVisible = true
      this.chapter = {}
      this.chapter.sort = 0
    },
    // 添加章节
    addChapter() {
      this.chapter.courseId = this.courseId
      chapter.addChapterVideo(this.chapter)
        .then(response => {
          // 关闭弹窗
          this.dialogChapterFormVisible = false
          // 提示信息
          this.$message({
            type: 'success',
            message: '添加章节成功!'
          })
          // 刷新页面(重新获取一遍)
          this.ChapterVideo()
        })
    },
    // 查询章节与小节
    ChapterVideo() {
      chapter.getChapterVideo(this.courseId)
        .then(response => {
          this.chapterVideoList = response.data.allChapterVideo
        })
    },
    last() {
      this.$router.push({ path: '/course/info/' + this.courseId })
    },
    next() {
      // 跳转到第二步
      this.$router.push({ path: '/course/publish/' + this.courseId })
    }
  }
}
</script>

<style scoped>
.chanpterList {
  position: relative;

  list-style: none;

  margin: 0;

  padding: 0;
}

.chanpterList li {
  position: relative;
}

.chanpterList p {
  /* float: left; */

  font-size: 20px;

  margin: 10px 0;

  padding: 10px;

  height: 70px;

  line-height: 50px;

  width: 100%;

  border: 1px solid #ddd;
}

.chanpterList .acts {
  float: right;

  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}

.videoList p {
  /* float: left; */
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>
