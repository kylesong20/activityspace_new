package com.kyle.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.ucenter.entity.BUser;
import com.kyle.ucenter.entity.vo.UserQuery;
import com.kyle.ucenter.mapper.BUserMapper;
import com.kyle.ucenter.service.BUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
public class BUserServiceImpl extends ServiceImpl<BUserMapper, BUser> implements BUserService {

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
            wrapper.like("num",num);
        }
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(organizationName)){
            wrapper.like("organization_name",organizationName);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("create_time",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("create_time",end);
        }

        wrapper.orderByDesc("create_time");

        page(userPage,wrapper);

        long total = userPage.getTotal();
        List<BUser> records = userPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

}
