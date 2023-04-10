package com.kyle.activity.controller;


import com.kyle.activity.entity.ActivityFeedback;
import com.kyle.activity.service.ActivityFeedbackService;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2023-04-10
 */
@RestController
@RequestMapping("/activity/feedback")
public class ActivityFeedbackController {
    @Autowired
    ActivityFeedbackService activityFeedbackService;
    @Autowired
    private TokenManager tokenManager;

    @ApiOperation(value = "逻辑删除活动反馈")
    @DeleteMapping("{id}")
    public R removeActivityFeedback(@ApiParam(name="id",value = "场地ID",required = true) @PathVariable String id){
        boolean flag = activityFeedbackService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "带条件的分页查询活动反馈")
    @PostMapping("pageActivityFeedbackCondition/{current}/{limit}")
    public R pageActivityFeedbackCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ActivityFeedback activityFeedback){
        Map<String, Object> map = activityFeedbackService.pageActivityFeedbackCondition(current, limit, activityFeedback);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加活动反馈")
    @PostMapping("addActivityFeedback")
    public R addActivityFeedback(HttpServletRequest request,@RequestBody ActivityFeedback activityFeedback){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        activityFeedback.setUserId(userId);
        boolean save = activityFeedbackService.save(activityFeedback);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据活动反馈ID查询")
    @GetMapping("getActivityFeedback/{id}")
    public R getActivityFeedback(@PathVariable String id){
        ActivityFeedback activityFeedback = activityFeedbackService.getById(id);
        return R.ok().data("activityFeedback",activityFeedback);
    }

    @ApiOperation(value = "修改活动反馈")
    @PostMapping("updateActivityFeedback")
    public R updateActivityFeedback(@RequestBody ActivityFeedback activityFeedback){
        boolean flag = activityFeedbackService.updateById(activityFeedback);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }
}

