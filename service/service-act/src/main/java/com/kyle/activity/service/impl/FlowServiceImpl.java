package com.kyle.activity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kyle.activity.entity.Flow;
import com.kyle.activity.entity.SiteMessage;
import com.kyle.activity.mapper.FlowMapper;
import com.kyle.activity.service.FlowService;
import com.kyle.activity.service.SiteMessageService;
import com.kyle.security.security.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@Slf4j
@Service("flowService")
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow> implements FlowService {
//    @Autowired
//    BUserService bUserService;

    @Autowired
    private ActFlowCommService actFlowCommService;
    @Autowired
    SiteMessageService siteMessageService;
    @Autowired
    TokenManager tokenManager;

    @Autowired
    HttpServletRequest request;

    @Override
    public void createTaskEvent(DelegateTask delegateTask) {
        log.info("delegateTask=={}", delegateTask);
//        负责人
        String assignee = delegateTask.getAssignee();
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
//        获取当前登录用户
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        BUser user = bUserService.getOne(new QueryWrapper<BUser>().eq("num",username));
//        String userId = user.getId();
//        任务id
        String taskId = delegateTask.getId();
        if (!assignee.equals(userId)) {
            SiteMessage siteMessage = new SiteMessage();
            siteMessage.setUserId(assignee);
            siteMessage.setTaskId(taskId);
            siteMessage.setType("1");
            siteMessage.setContent("有新的任务");
            siteMessage.setIsRead(false);
            siteMessageService.save(siteMessage);
//            siteMessageService.sendMsg(Long.valueOf(assignee),taskId,type,1);
        }
    }

    @Override
    public List<Map<String, Object>> findTaskInfo(String userId,long current, long limit) {
        List<Map<String, Object>> list = actFlowCommService.myTaskInfoList(userId,current,limit);
        return list;
    }
}
