import request from '@/utils/request'

export default {
  findTaskInfo(current, limit, activityQuery) {
    return request({
      url: `/activity/flow/findTaskInfo/${current}/${limit}`,
      method: 'get'
    })
  },
  completeTask(taskId, Bid) {
    return request({
      url: `/activity/flow/completeTask/${taskId}/${Bid}`,
      method: 'put'
    })
  },
  endTask(taskId) {
    return request({
      url: `/activity/flow/endTask/${taskId}`,
      method: 'put'
    })
  },
  deployment(flowId) {
    return request({
      url: `/activity/flow/deployment/${flowId}`,
      method: 'put'
    })
  },

  delDeployment(flowId) {
    return request({
      url: `/activity/flow/delDeployment/${flowId}`,
      method: 'put'
    })
  },

  getFlowListPage() {
    return request({
      url: `/activity/flow/findAll`,
      method: 'get'
    })
  }
}
