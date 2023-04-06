import request from '@/utils/request'

export default {

  // 全部活动列表
  getActivityListPage(current, limit, activityQuery) {
    return request({
      url: `/activity/activity/pageActivityCondition/${current}/${limit}`,
      method: 'post',
      data: activityQuery
    })
  },

  // 当前用户活动列表
  getUserActivityListPage(current, limit, activityQuery) {
    return request({
      url: `/activity/activity/pageUserActivityCondition/${current}/${limit}`,
      method: 'post',
      data: activityQuery
    })
  },

  // 添加活动
  addActivity(activity, AId, venueIds) {
    return request({
      url: `/activity/activity/addActivity/${AId}/${venueIds}`,
      method: 'post',
      data: activity
    })
  },

  // 根据id查询活动
  getActivityInfo(id) {
    return request({
      url: `/activity/activity/getActivity/${id}`,
      method: 'get'
    })
  },

  // 删除活动
  deleteActivityById(activityId) {
    return request({
      url: `/activity/activity/delActivity/${activityId}`,
      method: 'delete'
    })
  },
  // 活动报名列表
  pageActivityApplyCondition(current, limit, activityId, activityQuery) {
    return request({
      url: `/activity/apply/pageActivityApplyCondition/${current}/${limit}/${activityId}`,
      method: 'post',
      data: activityQuery
    })
  }
}
