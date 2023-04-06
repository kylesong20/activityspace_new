<template>
  <div class="app-container">
    <!-- 查询 -->
    <el-form :inline="true" :model="UserQuery" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="UserQuery.num" placeholder="学号" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="UserQuery.name" placeholder="姓名" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="UserQuery.clock" filterable clearable placeholder="请选择">
          <el-option value="Y" label="已打卡" />
          <el-option value="N" label="未打卡" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <!--    <div>-->
    <!--      <el-button v-if="hasPerm('activity.add')" size="mini" type="primary" @click="addActivity()">添加</el-button>-->
    <!--      <el-button v-if="hasPerm('activity.remove')" size="mini" type="danger" @click="removeRows()">批量删除</el-button>-->
    <!--    </div>-->
    <!-- 表格 -->
    <el-table ref="multipleTable" :data="list" style="width: 100%">
      <el-table-column label="报名人学号">
        <template slot-scope="scope">
          <span>{{ scope.row.num }}</span>
        </template>
      </el-table-column>
      <el-table-column label="报名人姓名">
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="打卡状态">
        <template slot-scope="scope">
          <span>{{
            scope.row.clockTime
          }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column fixed="right" label="操作" width="200" align="center">-->
      <!--        <template slot-scope="scope">-->
      <!--          &lt;!&ndash;          <router-link :to="'/activity/activityEdit/'+scope.row.id">-->
      <!--            <el-button style="margin-right: 10px;" size="mini" v-if="hasPerm('activity.update')">修改</el-button>-->
      <!--          </router-link> &ndash;&gt;-->
      <!--&lt;!&ndash;          <el-button v-if="hasPerm('activity.remove')" size="mini" type="danger" @click="removeById(scope.row.id)">删除&ndash;&gt;-->
      <!--          </el-button>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
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
export default {
  name: 'ActivityApply',
  props: {
    'activityId': {
      type: String,
      required: true,
      default: ''
    }
  },
  data() {
    return {
      UserQuery: {},
      list: null,
      page: 1,
      limit: 5,
      total: null
    }
  },
  watch: {
    activityId: {
      handler: function(newV, oldV) {
        this.getList()
      },
      deep: true, // 深度监听
      immediate: true // 立即执行
    }
  },
  methods: {
    getList(page = 1) {
      this.page = page
      console.log(this.activityId)
      activity.pageActivityApplyCondition(this.page, this.limit, this.activityId, this.UserQuery)
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
    resetData() {
      this.ActivityQuery = {}
      this.getList()
    }
  }
}
</script>

<style scoped>

</style>
