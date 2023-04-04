<template>
  <div class="app-container">
    <el-form ref="venue" :model="venue" :rules="rules" class="demo-ruleForm" label-width="100px" status-icon>
      <el-form-item label="场地号" prop="num">
        <el-input v-model="venue.num" />
      </el-form-item>
      <el-form-item label="场地名称" prop="name">
        <el-input v-model="venue.name" />
      </el-form-item>
      <el-form-item label="场地状态" prop="state">
        <el-select v-model="venue.state" filterable clearable placeholder="请选择">
          <el-option :value="true" label="可用" />
          <el-option :value="false" label="不可用" />
        </el-select>
      </el-form-item>
      <el-form-item label="场地组" prop="groupName">
        <el-select
          v-model="venue.groupName"
          clearable
          filterable
          placeholder="请选择"
          @visible-change="getAllVenueGroup()"
        >
          <el-option
            v-for="item in allVenueGroup"
            :key="item.id"
            :label="item.name"
            :value="item.name"
            @click.native="click(item.id)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="场地特征" prop="mapJson">
        <el-input v-model="venue.mapJson" :rows="5" type="textarea" :placeholder="'请按正确格式输入:' + jsonTemp" />
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate('venue')">保存</el-button>
        <el-button @click="resetForm('venue')">重置</el-button>
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
import venue from '@/api/activityspace/venue'

export default {
  data() {
    return {
      jsonTemp: '{ "geometry": { "coordinates": [ [ [ 113.977489, 23.065883 ], [ 113.977329, 23.065896 ], [ 113.977252, 23.065883 ], [ 113.977224, 23.065262 ], [ 113.977475, 23.065255 ], [ 113.977489, 23.065883 ] ] ], "type": "Polygon" }, "type": "Feature", "properties": { "name": "后勤楼1" } }',
      rules: {
        num: [{
          required: true,
          message: '请输入场地号',
          trigger: 'blur'
        }],
        groupId: [{
          required: true,
          message: '请选择组',
          trigger: 'blur'
        }],
        name: [{
          required: true,
          message: '请输入场地名称',
          trigger: 'blur'
        }
        ],
        state: [{
          required: true,
          message: '请选择状态',
          trigger: 'blur'
        }],
        mapJson: [{
          required: true,
          message: '请输入场地json',
          trigger: 'blur'
        }]
      },

      venue: {
        num: '',
        name: '',
        state: '',
        groupId: '',
        groupName: '',
        mapJson: ''
      },

      saveBtnDisabled: false, // 保存按钮是否禁用,

      allVenueGroup: null

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
      venue.getVenueGroupInfo(id)
        .then(response => {
          this.venue = response.data.venue
        })
    },

    saveOrUpdate(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          // this.saveBtnDisabled = true
          if (!this.venue.id) {
            this.addVenue()
          } else {
            this.updateVenue()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },

    // 修改学生
    updateVenue() {
      venue.updateVenue(this.venue)
        .then(response => {
          this.saveBtnDisabled = false
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          this.$router.push({
            path: '/venue/venueList'
          })
        })
    },

    // 添加学生
    addVenue() {
      venue.addVenue(this.venue)
        .then(response => {
          this.saveBtnDisabled = true
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          this.$router.push({
            path: '/venue/venueList'
          })
        })
    },

    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },

    getAllVenueGroup() {
      if (this.allVenueGroup === null) {
        venue.getAllVenueGroup()
          .then(response => {
            this.allVenueGroup = response.data.items
            console.log(this.allVenueGroup)
          })
      }
    },
    // 设置组id
    click(id) {
      this.venue.groupId = id
    }
  }
}
</script>
