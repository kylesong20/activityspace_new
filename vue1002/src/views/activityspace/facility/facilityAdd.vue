<template>
  <div class="app-container">
    <el-form ref="facility" :model="facility" :rules="rules" class="demo-ruleForm" label-width="100px" status-icon>
      <el-form-item label="设施名称" prop="name">
        <el-input v-model="facility.name" />
      </el-form-item>
      <el-form-item label="设施状态" prop="state">
        <el-select v-model="facility.state" filterable clearable placeholder="请选择">
          <el-option :value="true" label="可用" />
          <el-option :value="false" label="不可用" />
        </el-select>
      </el-form-item>
      <el-form-item label="设施场地" prop="venueId">
        <el-select
          v-model="facility.venueName"
          clearable
          filterable
          placeholder="请选择"
          @visible-change="getAllFacilityVenue()"
        >
          <el-option
            v-for="item in allFacilityVenue"
            :key="item.id"
            :label="item.name"
            :value="item.name"
            @click.native="click(item.id)"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('facility')">保存</el-button>
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
import facility from '@/api/activityspace/vFacility'

export default {
  data() {
    return {
      rules: {
        venueId: [{
          required: true,
          message: '请选择场地',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入设施名称',
          trigger: 'blur'
        }
        ],
        state: [{
          required: true,
          message: '请选择状态',
          trigger: 'blur'
        }]
      },

      facility: {
        name: '',
        state: '',
        venueId: '',
        venueName: ''
      },

      // avatar_thumb: '',

      imagecropperShow: false,
      imagecropperKey: 0, // 上传场地件Key值
      BASE_API: process.env.VUE_APP_BASE_API,
      saveBtnDisabled: false, // 保存按钮是否禁用,

      allFacilityVenue: null

    }
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      const id = this.$route.params.id
      this.getInfo(id)
    }
  },

  methods: {
    getInfo(id) {
      facility.getFacilityVenueInfo(id)
        .then(response => {
          this.facility = response.data.facility
        })
    },

    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.saveBtnDisabled = true
          if (!this.facility.id) {
            this.addFacility()
          } else {
            this.updateFacility()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    updateFacility() {
      facility.updateFacility(this.facility)
        .then(response => {
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({
            path: '/venue/facilityList'
          })
        })
    },

    addFacility() {
      facility.addFacility(this.facility)
        .then(response => {
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/venue/facilityList'
          })
        })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    getAllFacilityVenue() {
      if (this.allFacilityVenue === null) {
        facility.getAllFacilityVenue()
          .then(response => {
            this.allFacilityVenue = response.data.items
            console.log(this.allFacilityVenue)
          })
      }
    },
    // 设置场地id
    click(id) {
      this.facility.venueId = id
    }
  }
}
</script>
