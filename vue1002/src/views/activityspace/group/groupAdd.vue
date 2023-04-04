<template>
  <div class="app-container">
    <el-form ref="group" :model="group" :rules="rules" class="demo-ruleForm" label-width="100px" status-icon>
      <el-form-item label="场地名称" prop="name">
        <el-input v-model="group.name" />
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('group')">保存</el-button>
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
import group from '@/api/activityspace/vGroup'

export default {
  data() {
    return {
      rules: {
        name: [{
          required: true,
          message: '请输入场地名称',
          trigger: 'blur'
        }
        ]
      },

      group: {
        name: ''
      },

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
      group.getGroupInfo(id)
        .then(response => {
          this.group = response.data.group
        })
    },

    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if (!this.group.id) {
            this.addGroup()
          } else {
            this.updateGroup()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 修改学生
    updateGroup() {
      group.updateGroup(this.group)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({
            path: '/venue/groupList'
          })
        })
    },

    // 添加学生
    addGroup() {
      group.addGroup(this.group)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/venue/groupList'
          })
        })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    // 设置组id
    click(id) {
      this.group.groupId = id
    }
  }
}
</script>
