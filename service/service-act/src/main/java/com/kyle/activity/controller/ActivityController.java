package com.kyle.activity.controller;


import com.kyle.activity.entity.ActVen;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.entity.vo.ActivityQuery;
import com.kyle.activity.service.ActVenService;
import com.kyle.activity.service.ActivityService;
import com.kyle.activity.service.impl.ActFlowCommService;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@Slf4j
@RestController
@RequestMapping("/activity/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private ActVenService actVenService;


    @GetMapping("/{activityId}")
    public R getActivityInfo(@PathVariable String activityId){
        Activity activityInfo = activityService.getById(activityId);
        return R.ok().data("activityInfo",activityInfo);
    }

    /**
     * 查询本人的活动申请
     * @return
     */
    @PostMapping("pageUserActivityCondition/{current}/{limit}")
    public R findActivity(HttpServletRequest request,@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) ActivityQuery activityQuery){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        Map<String, Object> map = activityService.pageUserActivityCondition(current, limit, activityQuery,userId);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    /**
     * 查询所有活动申请
     * @return
     */
    @PostMapping("pageActivityCondition/{current}/{limit}")
    public R pageActivityCondition(HttpServletRequest request,@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) ActivityQuery activityQuery){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        Map<String, Object> map = activityService.pageActivityCondition(current, limit, activityQuery,userId);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    /**
     * 根据ID查询活动申请
     * @return
     */
    @GetMapping("getActivity/{id}")
    public R findAll(@PathVariable("id")String activityId){
        Activity activity = activityService.getById(activityId);
        return R.ok().data("activity",activity);
    }
    @Autowired
    private ActFlowCommService actFlowCommService;

    /**
     * 新增活动申请
     * @param request
     * @param activity
     */
    @PostMapping("/addActivity/{AId}/{venueIds}")
    @Transactional
    public R addActivity(HttpServletRequest request, @RequestBody Activity activity, @PathVariable String AId, @PathVariable String[] venueIds){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        activity.setUserId(userId);
        activity.setState("0");
        boolean code = activityService.save(activity);
        if(code){
            String activityId = activity.getId();

            //保存场地-活动信息
            ArrayList<ActVen> actVens = new ArrayList<>();
            for (String venueId : venueIds) {
                actVens.add(new ActVen(activityId, venueId));
            }
            actVenService.saveBatch(actVens);
            //
            String formKey = "activity";
            String beanName = formKey + "Service";
            //使用流程变量设置字符串（格式 ： Evection:Id 的形式）
            //使用正在执行对象表中的一个字段BUSINESS_KEY(Activiti提供的一个字段)，让启动的流程（流程实例）关联业务
            String bussinessKey = formKey+":" +activityId;
            ProcessInstance processInstance = actFlowCommService.startProcess(formKey, beanName, bussinessKey, activityId,userId, AId,venueIds);
            //		流程实例ID
            String processDefinitionId = processInstance.getProcessDefinitionId();
            log.info("processDefinitionId is {}",processDefinitionId);
            log.info("负责人ID:{}",userId);
            List<Map<String, Object>> taskList = actFlowCommService.myTaskList(userId.toString());
            log.info("taskList:{}",taskList);
            if(!CollectionUtils.isEmpty(taskList)){
                for (Map<String, Object> map : taskList) {
                    if(map.get("assignee").toString().equals(userId.toString()) &&
                            map.get("processDefinitionId").toString().equals(processDefinitionId)){
                        log.info("processDefinitionId is {}",map.get("processDefinitionId").toString());
                        log.info("taskid is {}",map.get("taskid").toString());
                        actFlowCommService.completeProcess("同意",map.get("taskid").toString(),userId,null);
                    }

                }
            }
        }
        return R.ok();
    }

    /**
     * 删除流程实例
     */
    @DeleteMapping("/delActivity/{activityId}")
    @Transactional
    public R delActivity(@PathVariable String activityId){
        Activity byId = activityService.getById(activityId);
        if (!"3".equals(byId.getState()) && !"4".equals(byId.getState()))
            actFlowCommService.delActivity(activityId);
        activityService.removeById(activityId);
        return R.ok();
    }
}

