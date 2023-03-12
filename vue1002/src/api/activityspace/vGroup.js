import request from '@/utils/request'

export default {

  // 组列表
  getGroupListPage(current, limit, groupQuery) {
    return request({
      url: `/venue/group/pageGroupCondition/${current}/${limit}`,
      method: 'post',
      data: groupQuery
    })
  },

  // 所有组
  getAllGroup() {
    return request({
      url: `/venue/group/findAll`,
      method: 'get'
    })
  },

  // 删除组
  deleteGroupById(id) {
    return request({
      url: `/venue/group/${id}`,
      method: 'delete'
    })
  },

  // 添加组
  addGroup(group) {
    return request({
      url: `/venue/group/addGroup`,
      method: 'post',
      data: group
    })
  },

  // 根据id查询组
  getGroupInfo(id) {
    return request({
      url: `/venue/group/getGroup/${id}`,
      method: 'get'
    })
  },

  // 修改组
  updateGroup(group) {
    return request({
      url: `/venue/group/updateGroup`,
      method: 'post',
      data: group
    })
  }

}
