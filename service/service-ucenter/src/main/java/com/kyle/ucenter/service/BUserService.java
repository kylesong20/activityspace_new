package com.kyle.ucenter.service;

import com.kyle.ucenter.entity.BUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.ucenter.entity.vo.UserQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
public interface BUserService extends IService<BUser> {

    Map<String, Object> pageUserCondition(long current, long limit, UserQuery userQuery);

    boolean isLeader(String organizationId,String userId);
}
