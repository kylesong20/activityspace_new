<template>
  <div class="app-container">
    <el-form ref="activity" :model="activity" :rules="rules" class="demo-ruleForm" label-width="130px" status-icon>
      <el-form-item label="活动名称" prop="name">
        <el-input v-model="activity.name" />
      </el-form-item>
      <el-form-item label="活动开始时间" prop="beginTime">
        <el-date-picker
          v-model="activity.beginTime"
          default-time="00:00:00"
          placeholder="活动开始日期"
          style="width: 100%;"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
        />
      </el-form-item>
      <el-form-item label="活动结束时间" prop="endTime">
        <el-date-picker
          v-model="activity.endTime"
          default-time="00:00:00"
          placeholder="活动开始日期"
          style="width: 100%;"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
        />
      </el-form-item>
      <el-form-item label="选择活动场地" prop="venueId">
        <el-select v-model="venues" filterable multiple placeholder="请选择" :disabled="activity.beginTime===''||activity.endTime===''" @visible-change="getAllVenue()">
          <el-option
            v-for="item in AllVenue"
            :key="item.venue.id"
            :label="item.venue.name"
            :value="item.venue.id"
            :disabled="item.activity !== undefined"
            @click.native="click(item)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="选择一级审核" prop="venueId">
        <el-select v-model="AId" filterable placeholder="请选择" @visible-change="getAuditA()">
          <el-option
            v-for="item in AuditA"
            :key="item.id"
            :label="item.nickName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动图" prop="img">
        <pan-thumb :image=" activity.img === ''?'':'http://127.0.0.1:8001'+activity.img" />

        <el-button
          icon="el-icon-upload"
          style="position: absolute;bottom: 15px;margin-left: 40px;"
          type="primary"
          @click="imagecropperShow=true"
        >
          上传活动图
        </el-button>

        <image-cropper
          v-show="imagecropperShow"
          :key="imagecropperKey"
          :height="300"
          :url="BASE_API+'/actfile/file/uploadAvatar'"
          :width="300"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>
      <el-form-item label="请输入推文链接" prop="externalLinks">
        <el-input v-model="activity.externalLinks" />
      </el-form-item>
      <el-form-item label="申请理由" prop="reson">
        <el-input
          v-model="activity.reson"
          :autosize="{ minRows: 5, maxRows: 5}"
          maxlength="200"
          show-word-limit
          type="textarea"
        />
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="addActivity('activity')">保存</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

  <style lang="scss" scoped>
      .upload-demo {
        display: flex;
      }
      ::v-deep .el-list-enter-active,
      ::v-deep .el-list-leave-active {
        transition: none;
      }

      ::v-deep .el-list-enter,
      ::v-deep .el-list-leave-active {
        opacity: 0;
      }
      ::v-deep .el-upload-list {
        height: 40px;
      }
  </style>

<script>
import activity from '@/api/activityspace/activity'
import user from '@/api/activityspace/acl/user'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
export default {
  components: {
    ImageCropper,
    PanThumb
  },
  data() {
    return {
      rules: {
        name: [{
          required: true,
          message: '请输入活动名称',
          trigger: 'blur'
        }
        ],
        beginTime: [{
          required: true,
          message: '请输入活动开始时间',
          trigger: 'blur'
        }
        ],
        endTime: [{
          required: true,
          message: '请输入活动结束时间',
          trigger: 'blur'
        }
        ],
        venueId: [{
          required: true,
          message: '请选择活动场地',
          trigger: 'blur'
        }
        ],
        externalLinks: [{
          required: true,
          message: '请输入推文链接',
          trigger: 'blur'
        }],
        reson: [{
          required: true,
          message: '请输入申请理由',
          trigger: 'blur'
        }],
        img: [{
          required: true,
          message: '请选择图片',
          trigger: 'blur'
        }]
      },

      activity: {
        name: '',
        beginTime: '',
        endTime: '',
        reson: '',
        externalLinks: ''
      },

      venueId: '',
      Aid: '',

      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件Key值
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 保存按钮是否禁用,

      fileList: [],

      AllVenue: null,
      venues: null,
      AId: '',
      AuditA: null
    }
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getInfo(id)
    }
  },

  methods: {
    // 添加
    addActivity() {
      this.saveBtnDisabled = true
      activity.addActivity(this.activity, this.AId, this.venues)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/activity/activityList'
          })
        })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    getAllVenue() {
      if (this.activity.beginTime === '' || this.activity.endTime === '') { return }
      const time = {
        endTime: this.activity.endTime,
        beginTime: this.activity.beginTime
      }
      activity.getVenueAble(time)
        .then(response => {
          this.AllVenue = response.data.list
          console.log(this.AllVenue)
        })
    },

    getAuditA() {
      user.getAuditA().then(response => {
        this.AuditA = response.data.users
        console.log(this.AuditA)
      })
    },

    // 设置组id
    click(item) {
      console.log(item.venue.id)
      if (item.activity !== undefined) {
        this.$notify.info({
          title: '场地已被占用',
          message: item.activity.name + '在:' + item.activity.beginTime + '到' + item.activity.endTime + '使用该场地'
        })
      } else {
        this.venueId = item.venue.id
      }
    },

    // 头像保存
    cropSuccess(resData) {
      console.log(resData)
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.activity.img = resData.fileUrl
      // this.avatar_thumb = resData.fileUrl
      // this.getImg(resData.fileUrl)
    },
    // 关闭弹窗
    close() {
      this.imagecropperShow = false
      console.log(this.user.avatar)
    },

    // 获取头像
    // getImg(fileUrl) {
    //   console.log(fileUrl)
    //   user.getUserAvatar(fileUrl)
    //     .then(response => {
    //         this.avatar_thumb = response.data.avatar
    //                 console.log(response)
    //       })
    //     }

    // ↓↓↓↓↓↓↓↓文件上传↓↓↓↓↓↓↓↓

    onError(res) {
      this.$alert('创建失败', '提示', {
        confirmButtonText: '确定',
        callback: action => {
          console.log('上传失败')
        }
      })
    },
    handleChange(file, fileList) {
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]] // 这一步，是 展示最后一次选择的csv文件
      }
    },
  }
}
</script>
