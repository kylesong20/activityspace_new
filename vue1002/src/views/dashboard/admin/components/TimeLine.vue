<template>
  <div class="infinite-list-wrapper block" style="overflow:auto;height: 700px">
    <el-timeline>
      <el-timeline-item v-for="item in list" :key="item.avId" :timestamp="item.beginTime +'~'+ item.endTime" placement="top">
        <el-card>
          <h4>{{ item.name +'('+(item.open === '0'? '公开':'私有')+')' }}</h4>
          <p>{{ item.reson }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script>
import activity from '@/api/activityspace/activity'
export default {
  name: 'TimeLine',
  props: {
    'venueId': {
      type: String,
      required: true,
      default: '0'
    },
    'venueName': {
      type: String,
      required: true,
      default: '总场地'
    }
  },
  data() {
    return {
      list: []
      // page: 1,
      // limit: 5,
      // total: null,
      // ActivityQuery: {}
    }
  },
  watch: {
    venueId(newV, oldV) {
      this.getList(newV)
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(newV) {
      console.log(newV)
      activity.getActivitiesByVenueId(newV || '0')
        .then(response => {
          console.log(response.data.list)
          this.list = response.data.list
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
