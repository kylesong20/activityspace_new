package com.kyle.organization.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.organization.entity.Organization;
import com.kyle.organization.entity.vo.OrganizationQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
public interface OrganizationService extends IService<Organization> {

    Map<String, Object> pageOrganizationCondition(long current, long limit, OrganizationQuery organizationQuery);

    String getIntroduceById(String id);

    Map<String, Object> getOrganizationIntroduce(String id);
}
