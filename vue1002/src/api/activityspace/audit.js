import request from '@/utils/request'

export default {

  // 审核员列表
  getAuditListPage(current, limit, auditQuery) {
    return request({
      url: `/ucenter/audit/pageAuditCondition/${current}/${limit}`,
      method: 'post',
      data: auditQuery
    })
  },

  // 所有审核员
  getAllAudit() {
    return request({
      url: `/ucenter/audit/findAll`,
      method: 'get'
    })
  },

  // 删除审核员
  deleteAuditById(id) {
    return request({
      url: `/ucenter/audit/${id}`,
      method: 'delete'
    })
  },

  // 添加审核员
  addAudit(audit) {
    return request({
      url: `/ucenter/audit/addAudit`,
      method: 'post',
      data: audit
    })
  },

  // 根据id查询审核员
  getAuditInfo(id) {
    return request({
      url: `/ucenter/audit/getAudit/${id}`,
      method: 'get'
    })
  },

  // 修改审核员
  updateAudit(audit) {
    return request({
      url: `/ucenter/audit/updateAudit`,
      method: 'post',
      data: audit
    })
  },

  getAuditAvatar(fileUrl) {
    return request({
      url: `/actfile/file/getAuditAvatar`,
      method: 'post',
      data: {
        'fileUrl': fileUrl
      }
    })
  }

}
