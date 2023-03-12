package com.kyle.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.entity.vo.ActivityQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
public interface ActivityService extends IService<Activity> , IActFlowCustomService {
    Map<String, Object> pageActivityCondition(long current, long limit, ActivityQuery activityQuery);
    Map<String, Object> pageActivityCondition(long current, long limit, ActivityQuery activityQuery,String userId);
}
