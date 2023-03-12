<template>
  <div class="app-container">
    <!-- 查询 -->

    <el-form :inline="true" :model="AuditQuery" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="AuditQuery.num" placeholder="审核员账号" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="AuditQuery.name" placeholder="审核员名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="AuditQuery.grade" placeholder="审核员等级" />
      </el-form-item>
      <el-form-item>
        <el-col :span="11">
          <el-form-item prop="begin">
            <el-date-picker
              v-model="AuditQuery.begin"
              default-time="00:00:00"
              placeholder="选择开始日期"
              style="width: 100%;"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
            />
          </el-form-item>
        </el-col>
        <el-col class="line" :span="1">-</el-col>
        <el-col :span="11">
          <el-form-item prop="end">
            <el-date-picker
              v-model="AuditQuery.end"
              default-time="00:00:00"
              placeholder="选择结束日期"
              style="width: 100%;"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
            />
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <div>
      <el-button v-if="hasPerm('audit.add')" size="mini" type="primary" @click="addAudit()">添加</el-button>
      <el-button v-if="hasPerm('audit.remove')" size="mini" type="danger" @click="removeRows()">批量删除</el-button>
    </div>
    <!-- 表格 -->
    <el-table ref="multipleTable" :data="list" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column fixed="left" type="index" width="55" />
      <el-table-column fixed="left" label="审核员账号" width="200">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.num }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="left" label="审核员名称" width="200">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核员等级" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.grade === 1?"一级":"二级" }}</span>
        </template>
      </el-table-column>
      <el-table-column label="添加时间" width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column />
      <el-table-column fixed="right" label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/user/auditEdit/'+scope.row.id">
            <el-button v-if="hasPerm('audit.update')" size="mini" style="margin-right: 10px;">修改</el-button>
          </router-link>
          <el-button v-if="hasPerm('audit.remove')" size="mini" type="danger" @click="removeById(scope.row.id)">删除
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
import audit from '@/api/activityspace/audit'

export default {
  data() {
    return {
      multipleSelection: [],

      list: null,
      page: 1,
      limit: 5,
      total: null,
      AuditQuery: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {

    getList(page = 1) {
      this.page = page
      audit.getAuditListPage(this.page, this.limit, this.AuditQuery)
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
        audit.deleteAuditById(id)
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
    handleSelectionChange(selection) {
      console.log(selection)
      this.multipleSelection = selection
    },
    resetData() {
      this.AuditQuery = {}
      this.getList()
    },

    addAudit() {
      this.$router.push({ path: '/user/auditAdd' })
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
        return audit.removeRows(idList)
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
