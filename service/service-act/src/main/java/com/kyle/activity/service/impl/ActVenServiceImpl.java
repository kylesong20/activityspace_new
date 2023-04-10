package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.activity.entity.ActVen;
import com.kyle.activity.mapper.ActVenMapper;
import com.kyle.activity.service.ActVenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
