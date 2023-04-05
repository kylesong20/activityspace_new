<template>
  <div class="infinite-list-wrapper block" style="overflow:auto;height: 700px">
    <el-timeline>
      <!--      <el-timeline-item v-for="item in list" timestamp="{{item.beginTime}}+'~'+{{item.endTime}}" placement="top">-->
      <!--        <el-card>-->
      <!--          <h4>{{item.name}}</h4>-->
      <!--          <p>{{item.}}</p>-->
      <!--        </el-card>-->
      <!--      </el-timeline-item>-->
      <el-timeline-item timestamp="2018-04-03 16:00:00 ~ 2018-04-03 18:00:00" placement="top">
        <el-card>
          <h4>分享故事、文学作品</h4>
          <p>以分享故事、文学作品为主，鼓励会员阅读和写作。</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018-04-03 16:00:00 ~ 2018-04-03 18:00:00" placement="top">
        <el-card>
          <h4>环保活动和义工服务</h4>
          <p>倡导环保理念，组织环保活动和义工服务。</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018-04-03 16:00:00 ~ 2018-04-03 18:00:00" placement="top">
        <el-card>
          <h4>环保活动和义工服务</h4>
          <p>倡导环保理念，组织环保活动和义工服务。</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/12" placement="top">
        <el-card>
          <h4>更新 Github 模板</h4>
          <p>王小虎 提交于 2018/4/12 20:46</p>
        </el-card>
      </el-timeline-item>
      <el-timeline-item timestamp="2018/4/12" placement="top">
        <el-card>
          <h4>更新 Github 模板</h4>
          <p>王小虎 提交于 2018/4/12 20:46</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import activity from '@/api/activityspace/activity'
export default {
  name: 'TimeLine',
  data() {
    return {
      list: [],
      page: 1,
      limit: 5,
      total: null,
      ActivityQuery: {}
    }
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
    }
  }
}
</script>

<style scoped>

</style>
