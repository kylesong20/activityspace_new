package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.activity.entity.ActVen;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.mapper.ActVenMapper;
import com.kyle.activity.service.ActVenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-01-08
 */
@Service("actVenService")
public class ActVenServiceImpl extends ServiceImpl<ActVenMapper, ActVen> implements ActVenService {

    @Autowired
    ActVenMapper actVenMapper;

    @Override
    public ActVen actVenList(String activityId) {
        ActVen actVen = actVenMapper.actVenList(activityId);
        List<String> venueList = actVenMapper.getVenueList(activityId);
        actVen.setVenueName(venueList);
        return actVen;
    }

    @Override
    public List<Activity> getActivitiesByVenueId(String venueId) {
        QueryWrapper<Activity> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(venueId)&&!"0".equals(venueId)){
            wrapper.eq("v.Id",venueId);
        }
        return actVenMapper.getActivitiesByVenueId(wrapper);
    }
}
