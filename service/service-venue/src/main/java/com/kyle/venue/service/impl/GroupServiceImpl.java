package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.Group;
import com.kyle.venue.entity.vo.GroupQuery;
import com.kyle.venue.mapper.GroupMapper;
import com.kyle.venue.service.GroupService;
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
 * @since 2022-12-31
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

    @Override
    public Map<String, Object> pageGroupCondition(long current, long limit, GroupQuery groupQuery) {
        Page<Group> groupPage = new Page<>(current, limit);
        QueryWrapper<Group> groupQueryWrapper = pageCondition(groupQuery);

        page(groupPage,groupQueryWrapper);

        long total = groupPage.getTotal();
        List<Group> records = groupPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    private QueryWrapper<Group> pageCondition(GroupQuery groupQuery){

        //构建条件
        QueryWrapper<Group> wrapper = new QueryWrapper<>();

        //动态SQL
        String name = groupQuery.getName();

        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        return wrapper;
    }
}
