package com.kyle.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kyle.activity.entity.Flow;
import org.activiti.engine.delegate.DelegateTask;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
public interface FlowService extends IService<Flow> {

    void createTaskEvent(DelegateTask delegateTask);

    List<Map<String, Object>> findTaskInfo(String userId,long current, long limit);
}
