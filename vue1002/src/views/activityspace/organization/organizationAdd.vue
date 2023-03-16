<template>
  <div class="app-container">
    <el-form
      ref="organization"
      :model="organization"
      :rules="rules"
      class="demo-ruleForm"
      label-width="100px"
      status-icon
    >
      <el-form-item label="组织名" prop="name">
        <el-input v-model="organization.name" />
      </el-form-item>
      <el-form-item label="组织简介" prop="introduction">
        <el-input v-model="organization.introduction" @input="change($event)" />
      </el-form-item>
      <el-form-item label="组织头像">
        <pan-thumb :image="organization.avatar" />

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
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('organization')">保存</el-button>
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
import organization from '@/api/activityspace/organization'
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
          message: '请输入组织名',
          trigger: 'blur'
        }
        ],
        leaderName: [{
          required: true,
          message: '请选择负责人',
          trigger: 'blur'
        }
        ],
        introduction: [{
          required: true,
          message: '请输入组织简介',
          trigger: 'blur'
        }
        ]
      },

      organization: {
        name: '',
        leaderId: '',
        leaderName: '',
        avatar: '',
        introductionId: '',
        introduction: ''
      },

      // avatar_thumb: '',

      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件Key值
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 保存按钮是否禁用,

      fileList: []
    }
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getInfo(id)
      this.fileList = [{ name: '简历', url: this.organization.resume }]
      console.log(this.fileList)
    }
  },

  methods: {

    // 根据id查询学生
    getInfo(id) {
      organization.getOrganizationInfo(id)
        .then(response => {
          this.organization = response.data.organization
          this.organization.introduction = response.data.organizationIntroduce
        })
    },

    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if (!this.organization.id) {
            this.addOrganization()
          } else {
            this.updateOrganization()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 修改学生
    updateOrganization() {
      organization.updateOrganization(this.organization, this.organization.introduction)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({
            path: '/organization/organizationList'
          })
        })
    },

    // 添加学生
    addOrganization() {
      organization.addOrganization(this.organization, this.organization.introduction)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/organization/organizationList'
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
      this.organization.avatar = resData.fileUrl
      // this.avatar_thumb = resData.fileUrl
      // this.getImg(resData.fileUrl)
    },
    // 关闭弹窗
    close() {
      this.imagecropperShow = false
      console.log(this.organization.avatar)
    },

    // 获取头像
    // getImg(fileUrl) {
    //   console.log(fileUrl)
    //   organization.getOrganizationAvatar(fileUrl)
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

    change(e) {
      this.$forceUpdate()
    }
  }
}
</script>
