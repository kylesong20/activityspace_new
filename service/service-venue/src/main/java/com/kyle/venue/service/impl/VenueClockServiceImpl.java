package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.VenueClock;
import com.kyle.venue.entity.vo.VenueClockCount;
import com.kyle.venue.entity.vo.VenueClockWeek;
import com.kyle.venue.mapper.VenueClockMapper;
import com.kyle.venue.service.VenueClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    VenueClockMapper venueClockMapper;

    @Override
    public Map<String, Object> pageClock(long current, long limit, String userId) {
        Page<VenueClock> groupPage = new Page<>(current, limit);
        QueryWrapper<VenueClock> wrapper = new QueryWrapper<VenueClock>();
        wrapper.orderByDesc("create_time");
        wrapper.eq("user_id",userId);
        page(groupPage,wrapper);

        long total = groupPage.getTotal();
        List<VenueClock> records = groupPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }

    @Override
    public List<VenueClockCount> venueClockCount() {
        QueryWrapper<VenueClock> wrapper = new QueryWrapper<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        wrapper.between("create_time",df.format(new Date())+" 00:00:00",df.format(new Date())+" 23:59:59");//今天的打卡情况
        wrapper.groupBy("venue_id","venue_name","user_id");
        return venueClockMapper.venueClockCount(wrapper);
    }

    @Override
    public List<VenueClockWeek> venueClockWeek(String venueId) {
        if ("0".equals(venueId))
            return venueClockMapper.venueClockWeekAll();
        return venueClockMapper.venueClockWeek(venueId);
    }
}
