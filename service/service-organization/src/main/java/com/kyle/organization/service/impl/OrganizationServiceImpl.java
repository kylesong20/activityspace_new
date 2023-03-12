package com.kyle.organization.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.organization.entity.Organization;
import com.kyle.organization.entity.vo.OrganizationQuery;
import com.kyle.organization.mapper.OrganizationMapper;
import com.kyle.organization.service.OrganizationService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Override
    public Map<String, Object> pageOrganizationCondition(long current, long limit, OrganizationQuery organizationQuery) {
        Page<Organization> organizationPage = new Page<>(current, limit);

        //构建条件
        QueryWrapper<Organization> wrapper = new QueryWrapper<>();

        //动态SQL
        String name = organizationQuery.getName();
        String leaderName = organizationQuery.getLeaderName();
        String begin = organizationQuery.getBegin();
        String end = organizationQuery.getEnd();


        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(leaderName)){
            wrapper.like("leader_name",leaderName);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("create_time",end);
        }

        wrapper.orderByDesc("create_time");

        page(organizationPage,wrapper);

        long total = organizationPage.getTotal();
        List<Organization> records = organizationPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Resource
    OrganizationMapper organizationMapper;

    @Override
    public String getIntroduceById(String id) {


        String introduceById = organizationMapper.getIntroduceById(id);
        System.out.println(introduceById);
        return introduceById;
    }
}
