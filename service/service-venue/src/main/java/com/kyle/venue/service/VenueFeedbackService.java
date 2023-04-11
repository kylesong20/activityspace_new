package com.kyle.venue.service;

import com.kyle.venue.entity.VenueFeedback;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-04-11
 */
public interface VenueFeedbackService extends IService<VenueFeedback> {

    Map<String, Object> pageVenueFeedbackCondition(long current, long limit, VenueFeedback venueFeedback);
}
