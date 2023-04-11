package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.activity.entity.ActivityFeedback;
import com.kyle.activity.mapper.ActivityFeedbackMapper;
import com.kyle.activity.service.ActivityFeedbackService;
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
 * @since 2023-04-10
 */
@Service
public class ActivityFeedbackServiceImpl extends ServiceImpl<ActivityFeedbackMapper, ActivityFeedback> implements ActivityFeedbackService {
    @Override
    public Map<String, Object> pageActivityFeedbackCondition(long current, long limit, ActivityFeedback activityFeedback) {
        Page<ActivityFeedback> activityFeedbackPage = new Page<>(current, limit);
        QueryWrapper<ActivityFeedback> wrapper = new QueryWrapper<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        wrapper.between("create_time",df.format(new Date())+" 00:00:00",df.format(new Date())+" 23:59:59");
        wrapper.orderByDesc("create_time");

        if (!StringUtils.isEmpty(activityFeedback.getActId()))
            wrapper.eq("act_id",activityFeedback.getActId());
        if (!StringUtils.isEmpty(activityFeedback.getUserId()))
            wrapper.eq("user_id",activityFeedback.getUserId());
        page(activityFeedbackPage,wrapper);

        long total = activityFeedbackPage.getTotal();
        List<ActivityFeedback> records = activityFeedbackPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",records);
        return map;
    }
}
