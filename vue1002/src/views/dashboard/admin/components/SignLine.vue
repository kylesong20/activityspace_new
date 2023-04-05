<template>
  <div id="line" style="height: 700px;" />
</template>

<script>
import echarts from 'echarts'
import venue from '@/api/activityspace/venue'
export default {
  name: 'SignLine',
  props:{
    "venueId":{
      type: String,
      required:true,
      default: '0'
    },
    "venueName":{
      type: String,
      required:true,
      default: '总场地'
    }
  },
  mounted() {
    this.getWeekData()
  },
  watch:{
    venueId(newV,oldV){
      this.getWeekData(newV)
    },
  },
  methods: {
    getWeekData(newV){
      console.log(newV)
      venue.venueClockWeek(newV).then(res=>{
        const venueClockWeeks = res.data.venueClockWeeks
        this.initLine(venueClockWeeks)
      })

    },
    initLine(venueClockWeeks) {
      console.log(venueClockWeeks)
      let value = []
      let j = 0
      for (let i = 0; i < 7; i++) {
        if (j<venueClockWeeks.length && venueClockWeeks[j].dayOfWeek == i){
          value.push(venueClockWeeks[j].value)
          j++
        }else {
          value.push(0)
        }
      }
      console.log(value)
      const chartDom = document.getElementById('line')// 这里放的“gdkjMap”就是你上方创建盒子的id
      const myChart = echarts.init(chartDom)
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>{c} (次)'
        },
        title: {
          left: 'center',
          text: '本周'+this.venueName+'打卡次数'
        },
        xAxis: {
          type: 'category',
          data: ['周日','周一', '周二', '周三', '周四', '周五', '周六']
        },
        yAxis: {
          type: 'value',
          // min: function(value) { // 取最小值向下取整为最小刻度
          //   return Math.floor(value.min)
          // },
          // max: function(value) { // 取最大值向上取整为最大刻度
          //   return Math.ceil(value.max)
          // }
        },
        series: [
          {
            data: value,
            type: 'line'
          }
        ]
      }
      option && myChart.setOption(option)
      window.addEventListener('resize', function() {
        myChart.resize()
      })
    }
  }
}

</script>

<style scoped>

</style>
