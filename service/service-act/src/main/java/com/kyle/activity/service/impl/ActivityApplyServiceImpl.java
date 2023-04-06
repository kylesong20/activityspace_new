package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.entity.ActivityApply;
import com.kyle.activity.entity.vo.ActApplyQuery;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.activity.mapper.ActivityApplyMapper;
import com.kyle.activity.service.ActivityApplyService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2023-04-06
 */
@Service
public class ActivityApplyServiceImpl extends ServiceImpl<ActivityApplyMapper, ActivityApply> implements ActivityApplyService {

    @Autowired
    private ActivityApplyMapper activityApplyMapper;

    @Override
    public Map<String, Object> pageActivityCondition(long current, long limit, ActApplyQuery actApplyQuery, String activityId) {
        Page<ActivityApply> venuePage = new Page<>(current, limit);
        QueryWrapper<ActivityApply> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(actApplyQuery.getNum()))
            wrapper.eq("num",actApplyQuery.getNum());
        if (!StringUtils.isEmpty(actApplyQuery.getName()))
            wrapper.eq("name",actApplyQuery.getName());
        if (!StringUtils.isEmpty(actApplyQuery.getClock()) && "Y".equals(actApplyQuery.getClock())){
            wrapper.isNotNull("clock_time");
        }
        if (!StringUtils.isEmpty(actApplyQuery.getClock()) && "N".equals(actApplyQuery.getClock())){
            wrapper.isNull("clock_time");
        }
        wrapper.eq("act_id",activityId);
//        page(venuePage,venueQueryWrapper);
        activityApplyMapper.getPage(venuePage,wrapper);
        long total = venuePage.getTotal();
        List<ActivityApply> records = venuePage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
}
