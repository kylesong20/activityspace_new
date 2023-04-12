package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kyle.activity.client.AdminClient;
import com.kyle.activity.client.UcenterClient;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.entity.Flow;
import com.kyle.activity.service.ActivityService;
import com.kyle.activity.service.IActFlowCustomService;
import com.kyle.activity.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther kyle
 * @creat 2023-01-18:13
 */
@Slf4j
@Service
@Transactional
public class ActFlowCommService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ManagementService managementService;

//    @Autowired
//    private AuditService auditService;
    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private AdminClient adminClient;

    @Autowired
    private ActivityService activityService;

    /**
     * 部署流程定义
     */
    public void saveNewDeploy(Flow flow) {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(flow.getPath()) // 添加bpmn资源
                .name(flow.getKey())
                .deploy();
//        4、输出部署信息
        System.out.println("流程部署id：" + deployment.getId());
        System.out.println("流程部署名称：" + deployment.getName());
    }
    /**
     * 删除流程定义
     */
    public void delDeployment(Flow flowInfo) {
        String flowkey = flowInfo.getKey();
        String deploymentId = repositoryService.createProcessDefinitionQuery().processDefinitionKey(flowkey).list().get(0).getDeploymentId();
        repositoryService.deleteDeployment(deploymentId,true);
    }

    /**
     * 启动流程实例
     */
    public ProcessInstance startProcess(String formKey, String beanName, String bussinessKey, String activityId, String userId, String Aid, String[] venueIds) {
        IActFlowCustomService customService = (IActFlowCustomService) SpringContextUtil.getBean(beanName);
//		修改业务的状态
//        customService.startRunTask(activityId);
        Map<String, Object> variables = customService.setvariables(activityId,userId, Aid,venueIds);
        variables.put("bussinessKey", bussinessKey);
//		启动流程
        log.info("【启动流程】，formKey ：{},bussinessKey:{}", formKey, bussinessKey);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(formKey, bussinessKey, variables);
//		流程实例ID
        String processDefinitionId = processInstance.getProcessDefinitionId();
        log.info("【启动流程】- 成功，processDefinitionId：{}", processDefinitionId);
        return processInstance;
    }

    /**
     * 查看个人任务列表
     */
    public List<Map<String, Object>> myTaskList(String auditId) {

        /**
         * 根据负责人id  查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(auditId);

        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();

        log.info("tasklist:{}",list);

        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("taskid", task.getId());
            map.put("taskname", task.getName());
            map.put("description", task.getDescription());
            map.put("priority", task.getPriority());
            map.put("owner", task.getOwner());
            map.put("assignee", task.getAssignee());
            map.put("delegationState", task.getDelegationState());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            map.put("taskDefinitionKey", task.getTaskDefinitionKey());
            map.put("dueDate", task.getDueDate());
            map.put("category", task.getCategory());
            map.put("parentTaskId", task.getParentTaskId());
            map.put("tenantId", task.getTenantId());

//            Audit auditInfo = auditService.findOneAuditById(Long.valueOf(task.getAssignee()));
//            Audit auditInfo = auditService.getById(task.getAssignee());
//            map.put("assigneeAudit", auditInfo.getAuditname());
            listmap.add(map);
        }

        return listmap;
    }

    /**
     * 查看个人任务信息
     */
    public List<Map<String, Object>> myTaskInfoList(String auditId, long current, long limit) {
        /**
         * 根据负责人id  查询任务
         */
        TaskQuery taskQuery = taskService.createTaskQuery().taskAssignee(auditId);
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().listPage((int) ((current-1)*limit), (int) limit);
        long count = taskService.createNativeTaskQuery()
                .sql("SELECT count(*) FROM " + managementService.getTableName(Task.class))
                .count();

        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        for (Task task : list) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("taskid", task.getId());
            map.put("assignee", task.getAssignee());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("executionId", task.getExecutionId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            map.put("createTime", task.getCreateTime());
            ProcessInstance processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (processInstance != null) {
                String businessKey = processInstance.getBusinessKey();
                if (!StringUtils.isBlank(businessKey)) {
                    String type = businessKey.split(":")[0];
                    String id = businessKey.split(":")[1];
                    if (type.equals("activity")) {
//                        Activity activity = activityService.findOne(Long.valueOf(id));
                        Activity activity = activityService.getById(id);
//                        Audit auditInfo = auditService.findOneAuditById(activity.getAuditid());
                        Object user = ucenterClient.getById(activity.getUserId()).getData().get("user");
                        if (ObjectUtils.isEmpty(user))
                             user = adminClient.doAssign(activity.getUserId()).getData().get("item");
//                        map.put("flowAuditName", auditInfo.getAuditname());
                        map.put("flowUser", user);
                        map.put("flowType", "活动申请");
                        map.put("flowReson", activity.getReson());
                        map.put("flowBeginTime", activity.getBeginTime());
                        map.put("flowEndTime", activity.getEndTime());
                        map.put("flowName", activity.getName());
                        map.put("flowState", activity.getState());
                    }
                }
            }
            listmap.add(map);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",count);
        listmap.add(map);
        return listmap;
    }


    /**
     * 完成提交任务
     */
    public void completeProcess(String remark, String taskId, String auditId,String bid) {

        if (!StringUtils.isEmpty(bid))
            taskService.setVariable(taskId,"assignee2",bid);


        //任务Id 查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            log.error("completeProcess - task is null!!");
            return;
        }


        //任务对象  获取流程实例Id
        String processInstanceId = task.getProcessInstanceId();

        //设置审批人的auditId
        Authentication.setAuthenticatedUserId(auditId);

        //添加记录
        taskService.addComment(taskId, processInstanceId, remark);
        System.out.println("-----------完成任务操作 开始----------");
        System.out.println("任务Id=" + taskId);
        System.out.println("负责人id=" + auditId);
        System.out.println("流程实例id=" + processInstanceId);
        //完成办理
        taskService.complete(taskId);
        System.out.println("-----------完成任务操作 结束----------");
    }

    /**
     * 不同意
     * @param taskId
     */
    @Transactional
    public void endProcess(String taskId) {
        //设置活动状态
        Activity activity = (Activity)taskService.getVariable(taskId, "activity");
        activityService.update(new UpdateWrapper<Activity>().eq("id",activity.getId()).set("state","4"));

        //任务Id 查询任务对象
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            log.error("completeProcess - task is null!!");
            return;
        }

        //任务对象  获取流程实例Id
        String processInstanceId = task.getProcessInstanceId();

        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .singleResult();

        if(pi==null){
            //该流程实例已经完成了
            historyService.deleteHistoricProcessInstance(processInstanceId);
        }else{
            //该流程实例未结束的
            runtimeService.deleteProcessInstance(processInstanceId,"");
            historyService.deleteHistoricProcessInstance(processInstanceId);//(顺序不能换)
        }
    }

    /**
     * 查询历史记录
     *
     * @param businessKey
     */
    public void searchHistory(String businessKey) {
        List<HistoricProcessInstance> list1 = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).list();
        if (CollectionUtils.isEmpty(list1)) {
            return;
        }
        String processDefinitionId = list1.get(0).getProcessDefinitionId();
        // 历史相关Service
        List<HistoricActivityInstance> list = historyService
                .createHistoricActivityInstanceQuery()
                .processDefinitionId(processDefinitionId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        for (HistoricActivityInstance hiact : list) {
            if (StringUtils.isBlank(hiact.getAssignee())) {
                continue;
            }
            System.out.println("活动ID:" + hiact.getId());
            System.out.println("流程实例ID:" + hiact.getProcessInstanceId());
//            Audit audit = auditService.findOneAuditById(Long.valueOf(hiact.getAssignee()));
//            Audit audit = auditService.getById(hiact.getAssignee());
            System.out.println("办理人ID：" + hiact.getAssignee());
//            System.out.println("办理人名字：" + audit.getAuditname());
            System.out.println("开始时间：" + hiact.getStartTime());
            System.out.println("结束时间：" + hiact.getEndTime());
            System.out.println("==================================================================");
        }
    }


    public void delActivity(String activityId) {
        String formKey = "activity";
        String key = formKey+":" +activityId;
        HistoricProcessInstance hpi = historyService.createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(key)
                .singleResult();

        String processInstanceId = hpi.getId(); //流程实例ID
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
                .processInstanceId(processInstanceId)// 使用流程实例ID查询
                .singleResult();

        if(pi==null){
            //该流程实例已经完成了
            historyService.deleteHistoricProcessInstance(processInstanceId);
        }else{
            //该流程实例未结束的
            runtimeService.deleteProcessInstance(processInstanceId,"");
            historyService.deleteHistoricProcessInstance(processInstanceId);//(顺序不能换)
        }
    }


}
