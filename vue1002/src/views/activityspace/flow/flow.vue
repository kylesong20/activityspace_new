<template>
  <div class="app-container">
    <!-- 表格 -->
    <el-table ref="multipleTable" :data="list" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column fixed="left" type="index" width="55" />
      <el-table-column fixed="left" label="流程名" width="150">
        <template slot-scope="scope">
          <span style="margin-left: 6px">{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="KEY" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.key }}</span>
        </template>
      </el-table-column>
      <el-table-column label="路径" width="250">
        <template slot-scope="scope">
          <span>{{ scope.row.path }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.state }}</span>
        </template>
      </el-table-column>
      <el-table-column label="添加时间" width="150">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column />
      <el-table-column align="center" fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <!--          <router-link :to="'/flowManagement/editFlow/'+scope.row.id">
                      <el-button style="margin-right: 10px;" size="mini">修改</el-button>
                    </router-link>
                    <el-button size="mini" type="danger" @click="removeById(scope.row.id)">删除</el-button> -->
          <el-button size="mini" type="primary" @click="deploymentFlow(scope.row.id)">部署</el-button>
          <el-button size="mini" type="danger" @click="delDeploymentFlow(scope.row.id)">删除</el-button>
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

export default {
  data() {
    return {
      multipleSelection: [],

      list: null,
      page: 1,
      limit: 5,
      total: null,
      flowQuery: {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      flow.getFlowListPage(this.page, this.limit, this.flowQuery)
        .then(response => {
          console.log(response)
          console.log(response.data.total)
          this.list = response.data.list
          this.total = response.data.total
        })
        .catch(error => {
          console.log(error)
        })
    },

    deploymentFlow(id) {
      flow.deployment(id).then(res => {
        console.log(res)
        if (res.data.deploymentCode === '2') {
          this.$message('已经部署过')
        }
        if (res.data.deploymentCode === '1') {
          this.$message({
            message: '部署成功',
            type: 'success'
          })
        }
        if (res.data.deploymentCode === '0') {
          this.$message.error('部署失败')
        }
      })
    },

    delDeploymentFlow(id) {
      flow.delDeployment(id).then(res => {
        console.log(res)
        if (res.code === 20000) {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
        }
      })
    },

    // removeById(id) {

    //   this.$confirm('是否执行删除操作?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     flow.deleteStuById(id)
    //       .then(response => {
    //         this.$message({
    //           type: 'success',
    //           message: '删除成功!'
    //         });
    //         this.getList()
    //       })
    //   });
    // },

    // toggleSelection(rows) {
    //   if (rows) {
    //     rows.forEach(row => {
    //       this.$refs.multipleTable.toggleRowSelection(row);
    //     });
    //   } else {
    //     this.$refs.multipleTable.clearSelection();
    //   }
    // },
    handleSelectionChange(val) {
      console.log(val)
    },
    resetData() {
      this.flowQuery = {}
      this.getList()
    }
    // downloadFile(resume){
    //   let link = document.createElement("a");
    //   link.style.display = "none"
    //   link.href = resume
    //   link.target="_blank"
    //   document.body.appendChild(link)
    //   link.click()
    // },
    // addFlow(){
    //   this.$router.push({ path: '/flowManagement/addFlow' })
    // },
    // removeRows() {
    //   console.log('removeRows......')

    //   if (this.multipleSelection.length === 0) {
    //     this.$message({
    //       type: 'warning',
    //       message: '请选择要删除的记录!'
    //     })
    //     return
    //   }

    //   this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => { // promise
    //     // 点击确定，远程调用ajax
    //     // 遍历selection，将id取出放入id列表
    //     var idList = []
    //     this.multipleSelection.forEach(item => {
    //       idList.push(item.id)
    //     // console.log(idList)
    //     })
    //     // 调用api
    //     return flow.removeRows(idList)
    //   }).then((response) => {
    //     this.fetchData(this.page)
    //     if (response.success) {
    //       this.$message({
    //         type: 'success',
    //         message: '删除成功!'
    //       })
    //     }
    //   }).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消删除'
    //     })
    //   })
    // },
  }
}
</script>
