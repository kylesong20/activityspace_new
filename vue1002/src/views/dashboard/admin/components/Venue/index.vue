<template>

  <div id="gdkjMap" style="height: 700px;" />

</template>

<script>
import geoJson from '@/geojson/gdkj'
import echarts from 'echarts'
import venue from '@/api/activityspace/venue'
export default {
  data() {
    return {
      dataCount: []
      // venueInfo:{}
    }
  },
  created() {
    console.log(this.dataCount)
  },
  mounted() {
    this.initData()
  },
  methods: {
    async initData() {
      try {
        const res = await venue.mapClock()
        this.dataCount = res.data.venueClockCounts
        console.log(res.data.venueClockCounts)
        this.initMap()
      } catch (error) {
        console.error(error)
      }
    },
    initMap() {
      console.log(geoJson)
      const chartDom = document.getElementById('gdkjMap')// 这里放的“gdkjMap”就是你上方创建盒子的id
      const myChart = echarts.init(chartDom)
      var option
      myChart.showLoading() // 这个geoJson数据可以自己在网上找相关的数据，或者从后端的接口来调用数据
      myChart.hideLoading()// 下面这个geoJSON就是上面通过import 引入的自己制作的mapData.js文件
      echarts.registerMap('GDKJ', geoJson.mapData)
      console.log(echarts)
      const _this = this
      console.log(this.dataCount)
      console.log(_this.dataCount)
      myChart.setOption(option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>{c} (人)'
        },
        title: {
          left: 'center',
          text: '高校社团活动场地热度分布地图(当天)'
        },
        visualMap: {
          show: true,
          x: 'left',
          y: '65%', // 可以设置左边提示的位置；
          itemWidth: 12,
          itemHeight: 12,
          splitList: [
            { start: 0, end: 0, label: '没人打卡', color: '#dbdbdb' },
            { start: 1, end: 20, label: '人迹罕至', color: '#b1d7c5' }, {
              start: 20,
              end: 50,
              label: '寥寥无几',
              color: '#84c3b7'
            },
            { start: 50, end: 60, label: '舒适', color: '#58afa3' }, {
              start: 60,
              end: 80,
              label: '人来人往',
              color: '#508b9b'
            },
            { start: 80, end: 100, label: '人山人海', color: '#507b87' }
          ],
          textStyle: {
            color: '#1e1e1e'
          }
        },
        series: [
          {
            name: '高校场地地图',
            type: 'map',
            mapType: 'GDKJ',
            label: {
              normal: {
                show: true,
                formatter: function(params) {
                  return params.name
                },
                textStyle: {
                  fontWeight: 'normal',
                  fontSize: 12,
                  color: '#000000'
                }
              }
            },
            emphasis: { // 对应的鼠标悬浮效果
              label: {
                textStyle: { color: '#030303' }

              },
              itemStyle: {
                areaColor: '#11874f'
              }
            },
            itemStyle: {
              // 设置边框为白色
              normal: {
                borderWidth: 0.5, // 边际线大小
                borderColor: '#1c1c1c'// 边界线颜色
              }
            },
            data: _this.dataCount
          }
        ]
      })
      option && myChart.setOption(option)
      // 下面这个是做屏幕适配的
      window.addEventListener('resize', function() {
        myChart.resize()
      })
      myChart.on('click', function(params) {
        console.log(params.data)
        _this.$emit('handleVenueInfo', params.data)
        // this.venueInfo = params.data
      })
    }
  }
}
</script>

<style scoped>

</style>
