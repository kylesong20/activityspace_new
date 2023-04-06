package com.kyle.activity.service;

import com.kyle.activity.entity.ActivityApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.activity.entity.vo.ActApplyQuery;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-04-06
 */
public interface ActivityApplyService extends IService<ActivityApply> {

    Map<String, Object> pageActivityCondition(long current, long limit, ActApplyQuery actApplyQuery, String activityId);
}
