<template>
  <div class="app-container">
    <!-- 表格 -->
    <el-table ref="multipleTable" :data="list" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column fixed="left" type="index" width="55" />
      <el-table-column fixed="left" label="活动名称" width="200">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.flowName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请人" width="200">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.flowUser.name || scope.row.flowUser.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请理由" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.flowReson }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动开始时间" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.flowBeginTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动结束时间" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.flowEndTime }}</span>
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
          <el-button v-if="hasPerm('audit.agree')" size="mini" type="primary" @click="agreeTask(scope.row.taskid)">同意
          </el-button>
          <el-button v-if="hasPerm('audit.end')" size="mini" type="danger" @click="endTask(scope.row.taskid)">不同意
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
import flow from '@/api/activityspace/flow'
import activity from '@/api/activityspace/activity'

export default {
  data() {
    return {
      multipleSelection: [],

      list: null,
      page: 1,
      limit: 5,
      total: null
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      flow.findTaskInfo(this.page, this.limit)
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
    agreeTask(taskid) {
      flow.completeTask(taskid)
        .then(response => {
          this.$message({
            type: 'success',
            message: '审核通过!'
          })
          this.getList()
        })
    },

    endTask(id) {
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
        // return activity.removeRows(idList)
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
    }
  }
}
</script>
