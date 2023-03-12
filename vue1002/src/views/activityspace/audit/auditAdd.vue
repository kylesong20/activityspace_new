<template>
  <div class="app-container">
    <el-form ref="audit" :model="audit" :rules="rules" class="demo-ruleForm" label-width="100px" status-icon>
      <el-form-item label="审核员账号" prop="num">
        <el-input v-model="audit.num" />
      </el-form-item>
      <el-form-item label="审核员名" prop="name">
        <el-input v-model="audit.name" />
      </el-form-item>
      <el-form-item label="审核员密码" prop="password">
        <el-input v-model="audit.password" type="password" />
      </el-form-item>
      <el-form-item label="审核员等级" prop="grade">
        <el-select v-model="audit.grade" clearable filterable placeholder="请选择审核员等级">
          <el-option :value="1" label="一级" />
          <el-option :value="2" label="二级" />
        </el-select>
      </el-form-item>
      <el-form-item label="审核员头像">
        <pan-thumb :image="audit.avatar" />

        <el-button
          icon="el-icon-upload"
          style="position: absolute;bottom: 15px;margin-left: 40px;"
          type="primary"
          @click="imagecropperShow=true"
        >
          更换头像
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

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('audit')">保存</el-button>
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
import audit from '@/api/activityspace/audit'
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
        num: [{
          required: true,
          message: '请输入审核员账号',
          trigger: 'blur'
        }
        ],
        name: [{
          required: true,
          message: '请输入审核员名',
          trigger: 'blur'
        }
        ],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }
        ],
        grade: [{
          required: true,
          message: '请选择审核员等级',
          trigger: 'blur'
        }
        ]
      },

      audit: {
        num: '',
        name: '',
        grade: '',
        avatar: '',
        password: ''
      },

      // avatar_thumb: '',

      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件Key值
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false // 保存按钮是否禁用,

    }
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getInfo(id)
    }
  },

  methods: {

    // 根据id查询学生
    getInfo(id) {
      audit.getAuditInfo(id)
        .then(response => {
          this.audit = response.data.audit
          this.audit.introduction = response.data.auditIntroduce
        })
    },

    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if (!this.audit.id) {
            this.addAudit()
          } else {
            this.updateAudit()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 修改学生
    updateAudit() {
      audit.updateAudit(this.audit)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({
            path: '/audit/auditList'
          })
        })
    },

    // 添加学生
    addAudit() {
      audit.addAudit(this.audit)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/audit/auditList'
          })
        })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    // 头像保存
    cropSuccess(resData) {
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.audit.avatar = resData.fileUrl
      // this.avatar_thumb = resData.fileUrl
      // this.getImg(resData.fileUrl)
    },
    // 关闭弹窗
    close() {
      this.imagecropperShow = false
      console.log(this.audit.avatar)
    }

  }
}
</script>
