import request from '@/utils/request'

export default {

  // 场地列表
  getVenueListPage(current, limit, venueQuery) {
    return request({
      url: `/venue/venue/pageVenueCondition/${current}/${limit}`,
      method: 'post',
      data: venueQuery
    })
  },
  // 场地列表带组
  getVenueGroupListPage(current, limit, venueQuery) {
    return request({
      url: `/venue/venue/pageVenueGroupCondition/${current}/${limit}`,
      method: 'post',
      data: venueQuery
    })
  },

  // 所有场地
  getAllVenue() {
    return request({
      url: `/venue/venue/findAll`,
      method: 'get'
    })
  },

  // 获取所有场地组
  getAllVenueGroup() {
    return request({
      url: `/venue/group/findAll`,
      method: 'get'
    })
  },

  // 删除场地
  deleteVenueById(id) {
    return request({
      url: `/venue/venue/${id}`,
      method: 'delete'
    })
  },

  // 添加场地
  addVenue(venue) {
    return request({
      url: `/venue/venue/addVenue`,
      method: 'post',
      data: venue
    })
  },

  // 根据id查询场地
  getVenueInfo(id) {
    return request({
      url: `/venue/venue/getVenue/${id}`,
      method: 'get'
    })
  },

  // 根据id查询场地
  getVenueGroupInfo(id) {
    return request({
      url: `/venue/venue/getVenueGroup/${id}`,
      method: 'get'
    })
  },

  // 修改场地
  updateVenue(venue) {
    return request({
      url: `/venue/venue/updateVenue`,
      method: 'post',
      data: venue
    })
  },
  // 同步map
  sync(venue) {
    return request({
      url: `/venue/venue/syncMap`,
      method: 'post'
    })
  }

}
