package com.kyle.activity.service;

import com.kyle.activity.entity.ActivityFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-04-10
 */
public interface ActivityFeedbackService extends IService<ActivityFeedback> {

    Map<String, Object> pageActivityFeedbackCondition(long current, long limit, ActivityFeedback activityFeedback);
}
