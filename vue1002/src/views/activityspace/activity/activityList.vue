<template>
  <div class="app-container">
    <!-- 查询 -->
    <el-form :inline="true" :model="ActivityQuery" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="ActivityQuery.name" placeholder="活动名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="ActivityQuery.state" placeholder="活动状态" />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="ActivityQuery.begin"
          default-time="00:00:00"
          placeholder="活动开始日期"
          style="width: 100%;"
          type="datetime"
          value-format="yyyy-MM-dd HH:mm:ss"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
    >
      <activity-apply :activity-id="activityId" />
    </el-dialog>
    <div>
      <el-button v-if="hasPerm('activity.add')" size="mini" type="primary" @click="addActivity()">添加</el-button>
      <el-button v-if="hasPerm('activity.remove')" size="mini" type="danger" @click="removeRows()">批量删除</el-button>
    </div>
    <!-- 表格 -->
    <el-table ref="multipleTable" :data="list" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column fixed="left" type="index" width="55" />
      <el-table-column fixed="left" label="活动名称" width="200">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动状态" width="200">
        <template slot-scope="scope">
          <span>{{
            scope.row.state === '0'?'待审核' :
            scope.row.state === '1'?'待开展' :
            scope.row.state === '2'?'正在开展' :
            scope.row.state === '3'?'已结束' : '未通过审核'
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" width="200">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请理由" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.reson }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报名列表" width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="openApplyList(scope.row.id)">查看列表</el-button>
        </template>
      </el-table-column>
      <el-table-column label="活动开始时间" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.beginTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动结束时间" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.endTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动创建时间" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column />
      <el-table-column fixed="right" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!--          <router-link :to="'/activity/activityEdit/'+scope.row.id">
            <el-button style="margin-right: 10px;" size="mini" v-if="hasPerm('activity.update')">修改</el-button>
          </router-link> -->
          <el-button v-if="hasPerm('activity.remove')" size="mini" type="danger" @click="removeById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      layout="total,prev, pager, next, jumper"
      style="padding: 30px 0;text-align: center;"
      @current-change="getList"
    />
  </div>
</template>

<script>
import activity from '@/api/activityspace/activity'
import ActivityApply from './activityApply'

export default {
  components: {
    // eslint-disable-next-line vue/no-unused-components
    ActivityApply
  },
  data() {
    return {
      multipleSelection: [],
      dialogVisible: false,
      activityId: '',

      list: null,
      page: 1,
      limit: 5,
      total: null,
      ActivityQuery: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      activity.getActivityListPage(this.page, this.limit, this.ActivityQuery)
        .then(response => {
          console.log(response.data.rows)
          console.log(response.data.total)
          this.list = response.data.rows
          this.total = response.data.total
        })
        .catch(error => {
          console.log(error)
        })
    },

    removeById(id) {
      this.$confirm('是否执行删除操作?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        activity.deleteActivityById(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.getList()
          })
      })
    },

    toggleSelection(rows) {
      if (rows) {
        rows.forEach(row => {
          this.$refs.multipleTable.toggleRowSelection(row)
        })
      } else {
        this.$refs.multipleTable.clearSelection()
      }
    },
    handleSelectionChange(val) {
      console.log(val)
    },
    resetData() {
      this.ActivityQuery = {}
      this.getList()
    },

    addActivity() {
      this.$router.push({ path: '/activity/activityAdd' })
    },

    removeRows() {
      console.log('removeRows......')

      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择要删除的记录!'
        })
        return
      }

      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { // promise
        // 点击确定，远程调用ajax
        // 遍历selection，将id取出放入id列表
        var idList = []
        this.multipleSelection.forEach(item => {
          idList.push(item.id)
          // console.log(idList)
        })
        // 调用api
        return activity.removeRows(idList)
      }).then((response) => {
        this.fetchData(this.page)
        if (response.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    openApplyList(id) {
      this.dialogVisible = true
      this.activityId = id
    }
  }
}
</script>
