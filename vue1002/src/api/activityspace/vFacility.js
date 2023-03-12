import request from '@/utils/request'

export default {

  // 设施列表
  getFacilityListPage(current, limit, facilityQuery) {
    return request({
      url: `/venue/facility/pageFacilityCondition/${current}/${limit}`,
      method: 'post',
      data: facilityQuery
    })
  },
  // 设施列表带场地
  getFacilityVenueListPage(current, limit, facilityQuery) {
    return request({
      url: `/venue/facility/pageFacilityVenueCondition/${current}/${limit}`,
      method: 'post',
      data: facilityQuery
    })
  },

  // 所有设施
  getAllFacility() {
    return request({
      url: `/venue/facility/findAll`,
      method: 'get'
    })
  },

  // 获取所有设施场地
  getAllFacilityVenue() {
    return request({
      url: `/venue/venue/findAll`,
      method: 'get'
    })
  },

  // 删除设施
  deleteFacilityById(id) {
    return request({
      url: `/venue/facility/${id}`,
      method: 'delete'
    })
  },

  // 添加设施
  addFacility(facility) {
    return request({
      url: `/venue/facility/addFacility`,
      method: 'post',
      data: facility
    })
  },

  // 根据id查询设施
  getFacilityInfo(id) {
    return request({
      url: `/venue/facility/getFacility/${id}`,
      method: 'get'
    })
  },

  // 根据id查询设施
  getFacilityVenueInfo(id) {
    return request({
      url: `/venue/facility/getFacilityVenue/${id}`,
      method: 'get'
    })
  },

  // 修改设施
  updateFacility(facility) {
    return request({
      url: `/venue/facility/updateFacility`,
      method: 'post',
      data: facility
    })
  }

}
