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
        <el-select v-model="venues" filterable multiple placeholder="请选择" @visible-change="getAllVenue()">
          <el-option
            v-for="item in AllVenue"
            :key="item.id"
            :label="item.name"
            :value="item.id"
            @click.native="click(item.id)"
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
            @click.native="click(item.id)"
          />
        </el-select>
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
import venue from '@/api/activityspace/venue'
import user from '@/api/activityspace/acl/user'

export default {
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

      saveBtnDisabled: false, // 保存按钮是否禁用,

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
      if (this.AllVenue === null) {
        venue.getAllVenue()
          .then(response => {
            this.AllVenue = response.data.items
            console.log(this.AllVenue)
          })
      }
    },

    getAuditA() {
      user.getAuditA().then(response => {
        this.AuditA = response.data.users
        console.log(this.AuditA)
      })
    },

    // 设置组id
    click(id) {
      this.venueId = id
    }
  }
}
</script>
