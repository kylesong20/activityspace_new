import request from '@/utils/request'

export default {

  // 用户列表
  getUserListPage(current, limit, userQuery) {
    return request({
      url: `/ucenter/user/pageUserCondition/${current}/${limit}`,
      method: 'post',
      data: userQuery
    })
  },

  // 获取负责人用户
  getOrganizationUser() {
    return request({
      url: `/ucenter/user/getOrganizationUser`,
      method: 'get'
    })
  },

  // 删除用户
  deleteUserById(id) {
    return request({
      url: `/ucenter/user/${id}`,
      method: 'delete'
    })
  },

  // 添加用户
  addUser(user) {
    return request({
      url: `/ucenter/user/addUser/`,
      method: 'post',
      data: user
    })
  },

  // 根据id查询用户
  getUserInfo(id) {
    return request({
      url: `/ucenter/user/getUser/${id}`,
      method: 'get'
    })
  },

  // 修改用户
  updateUser(user) {
    return request({
      url: `/ucenter/user/updateUser`,
      method: 'post',
      data: user
    })
  },

  getUserAvatar(fileUrl) {
    return request({
      url: `/actfile/file/getUserAvatar`,
      method: 'post',
      data: {
        'fileUrl': fileUrl
      }
    })
  }

}
