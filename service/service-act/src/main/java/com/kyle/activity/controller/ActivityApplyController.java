package com.kyle.activity.controller;


import com.kyle.activity.entity.ActivityApply;
import com.kyle.activity.entity.vo.ActApplyQuery;
import com.kyle.activity.entity.vo.ActivityQuery;
import com.kyle.activity.service.ActivityApplyService;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
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
 * @since 2023-04-06
 */
@RestController
@RequestMapping("/activity/apply")
public class ActivityApplyController {
    @Autowired
    private TokenManager tokenManager;
    @Autowired
    ActivityApplyService activityApplyService;

    @PutMapping("/{activityId}")
    public R addApply(HttpServletRequest request,@PathVariable String activityId){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        ActivityApply activityApply = new ActivityApply();
        activityApply.setActId(activityId);
        activityApply.setUserId(userId);
        activityApplyService.save(activityApply);
        return R.ok();
    }

    @PostMapping("pageActivityApplyCondition/{current}/{limit}/{activityId}")
    public R listByActivityId(@PathVariable long current, @PathVariable long limit, @PathVariable String activityId,@RequestBody(required = false) ActApplyQuery actApplyQuery){
        Map<String, Object> map = activityApplyService.pageActivityCondition(current, limit, actApplyQuery,activityId);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

}

