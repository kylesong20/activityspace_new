<template>

  <div id="gdkjMap" style="height: 700px;" />

</template>

<script>
import geoJson from '@/geojson/gdkj'
import echarts from 'echarts'
export default {
  mounted() {
    this.initMap()
  },
  methods: {
    initMap() {
      console.log(geoJson)
      const chartDom = document.getElementById('gdkjMap')// 这里放的“gdkjMap”就是你上方创建盒子的id
      const myChart = echarts.init(chartDom)
      var option
      myChart.showLoading() // 这个geoJson数据可以自己在网上找相关的数据，或者从后端的接口来调用数据
      myChart.hideLoading()// 下面这个geoJSON就是上面通过import 引入的自己制作的mapData.js文件
      echarts.registerMap('GDKJ', geoJson.mapData)
      console.log(echarts)
      myChart.setOption(option = {
        tooltip: {
          trigger: 'item',
          formatter: '{b}<br/>{c} (人)'
        },
        title: {
          left: 'center',
          text: '高校社团活动场地热度分布地图'
        },
        visualMap: {
          show: true,
          x: 'left',
          y: '65%', // 可以设置左边提示的位置；
          itemWidth: 12,
          itemHeight: 12,
          splitList: [
            { start: 0, end: 20, label: '无人踏足此处', color: '#29A4D7' }, {
              start: 20,
              end: 50,
              label: '寥寥无几',
              color: '#E6C249'
            },
            { start: 50, end: 60, label: '舒适', color: '#DD970B' }, {
              start: 60,
              end: 80,
              label: '人来人往',
              color: '#5934EC'
            },
            { start: 80, end: 100, label: '人山人海', color: '#B52621' }
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
                  color: '#6e8788'
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
            data: [
              {
                name: '篮1',
                value: 10
              },
              {
                name: '篮2',
                value: 6
              },
              {
                name: '篮3',
                value: 6
              },
              {
                name: '排1',
                value: 20
              },
              {
                name: '排2',
                value: 20
              },
              {
                name: '网1',
                value: 4
              },
              {
                name: '网2',
                value: 6
              },
              {
                name: '网3',
                value: 7
              },
              {
                name: '足球场1',
                value: 50
              },
              {
                name: '风雨操场',
                value: 25
              },
              {
                name: '三饭',
                value: 80
              }
            ]
          }
        ]
      })
      option && myChart.setOption(option)
      // 下面这个是做屏幕适配的
      window.addEventListener('resize', function() {
        myChart.resize()
      })
    }
  }
}
</script>

<style scoped>

</style>
