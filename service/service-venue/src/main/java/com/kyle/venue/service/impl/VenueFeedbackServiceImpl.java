package com.kyle.venue.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.venue.entity.VenueFeedback;
import com.kyle.venue.mapper.VenueFeedbackMapper;
import com.kyle.venue.service.VenueFeedbackService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
 * @since 2023-04-11
 */
@Service
public class VenueFeedbackServiceImpl extends ServiceImpl<VenueFeedbackMapper, VenueFeedback> implements VenueFeedbackService {

    @Override
    public Map<String, Object> pageVenueFeedbackCondition(long current, long limit, VenueFeedback venueFeedback) {
        Page<VenueFeedback> venueFeedbackPage = new Page<>(current, limit);
        QueryWrapper<VenueFeedback> wrapper = new QueryWrapper<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        wrapper.between("create_time",df.format(new Date())+" 00:00:00",df.format(new Date())+" 23:59:59");
        wrapper.orderByDesc("create_time");

        if (!StringUtils.isEmpty(venueFeedback.getVenueId()))
            wrapper.eq("venue_id",venueFeedback.getVenueId());
        if (!StringUtils.isEmpty(venueFeedback.getUserId()))
            wrapper.eq("user_id",venueFeedback.getUserId());
        page(venueFeedbackPage,wrapper);

        long total = venueFeedbackPage.getTotal();
        List<VenueFeedback> records = venueFeedbackPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
}
