package com.kyle.activity.listener;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kyle.activity.client.VenueClient;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.service.ActivityService;
import com.kyle.activity.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution delegateExecution) {
        ActivityService activityService = (ActivityService) SpringContextUtil.getBean("activityService");
        VenueClient venueClient = (VenueClient) SpringContextUtil.getBean(VenueClient.class);
        log.info("delegateExecution is {}",delegateExecution);
        log.info(delegateExecution.getCurrentActivityId());
        if("sid-c23e0f52-9f5c-4554-b748-da66645d4d51".equals(delegateExecution.getCurrentActivityId())){
            if ("start".equals(delegateExecution.getEventName())){
                log.info("---------审核完成-----------等待活动开启-----------------");
                Activity activity = (Activity)delegateExecution.getVariable("activity");
                activityService.update(new UpdateWrapper<Activity>().eq("id",activity.getId()).set("state","1"));
            }
        }
        if("sid-c23e0f52-9f5c-4554-b748-da66645d4d51".equals(delegateExecution.getCurrentActivityId())){
            if ("end".equals(delegateExecution.getEventName())){
                log.info("--------------------活动开启-----------------");
                Activity activity = (Activity)delegateExecution.getVariable("activity");
                String[] venueIds = (String[])delegateExecution.getVariable("venueIds");
                activityService.update(new UpdateWrapper<Activity>().eq("id",activity.getId()).set("state","2"));
                venueClient.updateVenueState(venueIds, false);
            }
        }
        if("sid-d6f1a6ae-e704-404d-a6c6-242622f197cc".equals(delegateExecution.getCurrentActivityId())){
            log.info("--------------------活动结束-----------------");
            Activity activity = (Activity)delegateExecution.getVariable("activity");
            String[] venueIds = (String[])delegateExecution.getVariable("venueIds");
            activityService.update(new UpdateWrapper<Activity>().eq("id",activity.getId()).set("state","3"));
            venueClient.updateVenueState(venueIds,true);
        }
        if("sid-91720e15-9e6d-477c-8045-730083e4d301".equals(delegateExecution.getCurrentActivityId())){
            log.info("--------------------一级审核超时-------自动拒绝----------");
            Activity activity = (Activity)delegateExecution.getVariable("activity");
            activityService.update(new UpdateWrapper<Activity>().eq("id",activity.getId()).set("state","4"));
        }
        if("sid-73a67fcd-2b37-46d7-a37e-ca4b3081f2b2".equals(delegateExecution.getCurrentActivityId())){
            log.info("--------------------二级审核超时-------自动拒绝----------");
            Activity activity = (Activity)delegateExecution.getVariable("activity");
            activityService.update(new UpdateWrapper<Activity>().eq("id",activity.getId()).set("state","4"));
        }
    }
}
