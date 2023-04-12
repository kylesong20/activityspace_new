import request from '@/utils/request'

export default {

  // 组织列表
  getOrganizationListPage(current, limit, organizationQuery) {
    return request({
      url: `/organization/organization/pageOrganizationCondition/${current}/${limit}`,
      method: 'post',
      data: organizationQuery
    })
  },

  // 所有组织
  getAllOrganization() {
    return request({
      url: `/organization/organization/findAll`,
      method: 'get'
    })
  },

  // 删除组织
  deleteOrganizationById(id) {
    return request({
      url: `/organization/organization/${id}`,
      method: 'delete'
    })
  },

  // 添加组织
  addOrganization(organization, organizationIntroductions) {
    return request({
      url: `/organization/organization/addOrganization/${organizationIntroductions}`,
      method: 'post',
      data: organization
    })
  },

  // 根据id查询组织
  getOrganizationInfo(id) {
    return request({
      url: `/organization/organization/getOrganization/${id}`,
      method: 'get'
    })
  },

  // 修改组织
  updateOrganization(organization, organizationIntroductions) {
    return request({
      url: `/organization/organization/updateOrganization/${organizationIntroductions}`,
      method: 'post',
      data: organization
    })
  },

  getOrganizationAvatar(fileUrl) {
    return request({
      url: `/actfile/file/getOrganizationAvatar`,
      method: 'post',
      data: {
        'fileUrl': fileUrl
      }
    })
  },
  changeLeader(userId,leaderName,rowId){
    return request({
      url: `/organization/organization/changeLeader/${userId}/${leaderName}/${rowId}`,
      method: 'put'
    })
  },

  // 根据id获取组织简介
  getIntroduceById(id) {
    return request({
      url: `/organization/organization/getIntroduce/${id}`,
      method: 'get'
    })
  }
}
