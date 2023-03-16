<template>
  <div class="app-container">
    <el-form ref="user" :model="user" :rules="rules" class="demo-ruleForm" label-width="120px" status-icon>
      <el-form-item label="账号" prop="num">
        <el-input v-model="user.num" />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password" auto-complete="new-password" type="password" />
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="user.name" />
      </el-form-item>
      <el-form-item label="所属组织" prop="organizationName">
        <el-select
          v-model="user.organizationName"
          filterable
          clearable
          placeholder="请选择"
          @visible-change="getOrganizationList()"
        >
          <el-option
            v-for="item in allOrganization"
            :key="item.id"
            :label="item.name"
            :value="item.name"
            @click.native="click(item.id)"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="是否为负责人" prop="isLeader">-->
      <!--        <el-select v-model="user.isLeader" clearable placeholder="请选择">-->
      <!--          <el-option label="是" value="1" />-->
      <!--          <el-option label="否" value="0" />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="学生头像">
        <pan-thumb :image="user.avatar" />

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
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('user')">保存</el-button>
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
import user from '@/api/activityspace/user'
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
        num: [{
          required: true,
          message: '请输入学号',
          trigger: 'blur'
        }],
        password: [{
          required: true,
          message: '请输入密码',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入姓名',
          trigger: 'blur'
        },
        {
          min: 2,
          max: 10,
          message: '长度在 3 到 10 个字符',
          trigger: 'blur'
        }
        ],
        organizationName: [{
          required: true,
          message: '请选择组织',
          trigger: 'blur'
        }]
        // isLeader: [{
        //   required: true,
        //   message: '请选择',
        //   trigger: 'blur'
        // }]
      },

      user: {
        num: '',
        name: '',
        organizationName: '',
        organizationId: '',
        password: '',
        avatar: ''
      },

      // avatar_thumb: '',

      imagecropperShow: false,
      imagecropperKey: 0, // 上传组件Key值
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 保存按钮是否禁用,

      fileList: [],

      allOrganization: null

    }
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getInfo(id)
      this.fileList = [{ name: '简历', url: this.user.resume }]
      console.log(this.fileList)
    }
  },

  methods: {

    // 根据id查询学生
    getInfo(id) {
      user.getUserInfo(id)
        .then(response => {
          this.user = response.data.user
          console.log(this.user)
        })
    },

    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if (!this.user.id) {
            this.addUser()
          } else {
            this.updateUser()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 修改学生
    updateUser() {
      user.updateUser(this.user)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({
            path: '/user/userList'
          })
        })
    },

    // 添加学生
    addUser() {
      user.addUser(this.user)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/user/userList'
          })
        })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    // 头像保存
    cropSuccess(resData) {
      console.log(resData)
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
      this.user.avatar = resData.fileUrl
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
    getOrganizationList() {
      if (this.allOrganization === null) {
        organization.getAllOrganization()
          .then(response => {
            this.allOrganization = response.data.items
            console.log(this.allOrganization)
          })
      }
    },
    // 设置组织id
    click(id) {
      this.user.organizationId = id
      console.log(this.user)
    }
  }
}
</script>
