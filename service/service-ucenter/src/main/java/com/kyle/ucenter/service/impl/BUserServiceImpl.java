package com.kyle.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.ucenter.entity.BUser;
import com.kyle.ucenter.entity.vo.UserQuery;
import com.kyle.ucenter.mapper.BUserMapper;
import com.kyle.ucenter.service.BUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2022-12-29
 */
@Service
public class BUserServiceImpl extends ServiceImpl<BUserMapper, BUser> implements BUserService {
    @Autowired
    BUserMapper bUserMapper;

    @Override
    public Map<String, Object> pageUserCondition(long current, long limit, UserQuery userQuery) {
        Page<BUser> userPage = new Page<>(current, limit);

        //构建条件
        QueryWrapper<BUser> wrapper = new QueryWrapper<>();

        //动态SQL
        String num = userQuery.getNum();
        String name = userQuery.getName();
        String organizationName = userQuery.getOrganizationName();
        String begin = userQuery.getBegin();
        String end = userQuery.getEnd();

        if (!StringUtils.isEmpty(num)){
            wrapper.like("u.num",num);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("u.name",name);
        }
        if (!StringUtils.isEmpty(organizationName)){
            wrapper.like("u.organization_name",organizationName);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("u.create_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("u.create_time",end);
        }
        wrapper.eq("u.is_delete",0);
        wrapper.orderByDesc("u.create_time");

//        page(userPage,wrapper);

        IPage<BUser> bUserIPage = bUserMapper.listPage(userPage, wrapper);

        long total = bUserIPage.getTotal();
        List<BUser> records = bUserIPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public boolean isLeader(String organizationId,String userId) {
        String leaderId = bUserMapper.getLeaderId(organizationId);
        if (StringUtils.isEmpty(leaderId)||StringUtils.isEmpty(userId))
            return false;
        return Objects.equals(leaderId, userId);
    }

}
