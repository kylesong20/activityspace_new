package com.kyle.ucenter.service;

import com.kyle.ucenter.entity.Audit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.ucenter.entity.vo.AuditQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-30
 */
public interface AuditService extends IService<Audit> {

    Map<String, Object> pageAuditCondition(long current, long limit, AuditQuery auditQuery);
}
