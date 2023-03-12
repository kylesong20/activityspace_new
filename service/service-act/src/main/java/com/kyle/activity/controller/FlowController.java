package com.kyle.activity.controller;


import com.kyle.activity.entity.Flow;
import com.kyle.activity.service.FlowService;
import com.kyle.activity.service.impl.ActFlowCommService;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@RestController
@RequestMapping("/activity/flow")
public class FlowController {

    @Autowired
    private FlowService flowService;
    @Autowired
    private ActFlowCommService actFlowCommService;
    @Autowired
    private TokenManager tokenManager;

    /**
     * 查询所有流程
     * @return
     */
    @GetMapping("findAll")
    public R findAllFlow(){
        List<Flow> list = flowService.list();
        return R.ok().data("list",list);
    }

    /**
     * 部署流程
     * @param
     * @return 0-部署失败  1- 部署成功  2- 已经部署过
     */
    @PutMapping("deployment/{id}")
    public R deployment( @PathVariable("id")String id){
        Flow flowInfo = flowService.getById(id);
        if(Objects.equals(flowInfo.getState(), "0")){
            return R.ok().data("deploymentCode","2");
        }
        actFlowCommService.saveNewDeploy(flowInfo);
        flowInfo.setState("0");
        return flowService.updateById(flowInfo) ? R.ok().data("deploymentCode","1") : R.ok().data("deploymentCode","0");
    }

    /**
     * 删除流程
     * @param
     * @return 0-删除失败  1- 删除成功
     */
    @PutMapping("delDeployment/{id}")
    public R delDeployment(@PathVariable("id") String id){
        Flow flowInfo = flowService.getById(id);
        if ("1".equals(flowInfo.getState()))
            return R.error().message("未部署无法删除流程定义");
        actFlowCommService.delDeployment(flowInfo);
        flowInfo.setState("1");
        return flowService.updateById(flowInfo) ? R.ok().data("deploymentCode","1") : R.ok().data("deploymentCode","0");
    }

    /**
     * 查询用户任务
     * @param request
     * @return
     */
    @GetMapping("findUserTask")
    public R findUserTask(HttpServletRequest request){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        List<Map<String, Object>> myTaskList = actFlowCommService.myTaskList(userId);
        return R.ok().data("myTaskList",myTaskList);
    }

    /**
     * 完成任务
     * @param request
     */
    @PutMapping("completeTask/{taskId}/{Bid}")
    public R completeTask(HttpServletRequest request, @PathVariable("taskId") String taskId, @PathVariable String Bid){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        actFlowCommService.completeProcess("同意",taskId,userId,Bid);
        return R.ok().message("完成任务");
    }

    /**
     * 不同意
     * @param request
     */
    @PutMapping("endTask/{taskId}")
    public R endTask(HttpServletRequest request, @PathVariable("taskId") String taskId){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        actFlowCommService.endProcess(taskId);
        return R.ok().message("完成任务");
    }

    /**
     * 查询
     * @return
     */
    @GetMapping("findFlowTask/{id}")
    public R findFlowTask(@PathVariable(name = "id")Long id){
        String businessKey = "activity:"+id;
        actFlowCommService.searchHistory(businessKey);
        return R.ok();
    }
    /**
     * 查询任务详细信息
     * @param request
     * @return
     */
    @GetMapping("findTaskInfo/{current}/{limit}")
    public R findTaskInfo(HttpServletRequest request, @PathVariable long current, @PathVariable long limit){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        List<Map<String, Object>> taskInfo = flowService.findTaskInfo(userId,current,limit);
        Map<String, Object> total = taskInfo.get(taskInfo.size()-1);
        taskInfo.remove(taskInfo.size() - 1);
        return R.ok().data("total",total.get("total")).data("rows",taskInfo);
    }
}

