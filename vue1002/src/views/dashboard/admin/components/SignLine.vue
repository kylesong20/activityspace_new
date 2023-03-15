<template>
  <div id="line" style="height: 700px;" />
</template>

<script>
import echarts from 'echarts'
export default {
  name: 'SignLine',
  mounted() {
    this.initLine()
  },
  methods: {
    initLine() {
      const chartDom = document.getElementById('line')// 这里放的“gdkjMap”就是你上方创建盒子的id
      const myChart = echarts.init(chartDom)
      const option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>{c} (个)'
        },
        title: {
          left: 'center',
          text: '本周场地打卡次数'
        },
        xAxis: {
          type: 'category',
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value',
          min: function(value) { // 取最小值向下取整为最小刻度
            return Math.floor(value.min)
          },
          max: function(value) { // 取最大值向上取整为最大刻度
            return Math.ceil(value.max)
          }
        },
        series: [
          {
            data: [150, 230, 224, 218, 135, 147, 260],
            type: 'line'
          }
        ]
      }
      option && myChart.setOption(option)
    }
  }
}

</script>

<style scoped>

</style>
