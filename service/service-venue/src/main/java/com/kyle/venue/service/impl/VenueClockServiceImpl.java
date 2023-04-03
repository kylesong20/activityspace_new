package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.venue.entity.Group;
import com.kyle.venue.entity.VenueClock;
import com.kyle.venue.entity.VenueClock;
import com.kyle.venue.mapper.VenueClockMapper;
import com.kyle.venue.service.VenueClockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-04-03
 */
@Service
public class VenueClockServiceImpl extends ServiceImpl<VenueClockMapper, VenueClock> implements VenueClockService {

    @Override
    public Map<String, Object> pageClock(long current, long limit, String userId) {
        Page<VenueClock> groupPage = new Page<>(current, limit);
        QueryWrapper<VenueClock> wrapper = new QueryWrapper<VenueClock>();
        wrapper.orderByDesc("create_time");
        page(groupPage,wrapper);

        long total = groupPage.getTotal();
        List<VenueClock> records = groupPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
}
