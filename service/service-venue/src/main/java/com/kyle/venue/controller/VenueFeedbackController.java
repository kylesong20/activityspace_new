package com.kyle.venue.controller;


import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import com.kyle.venue.entity.VenueFeedback;
import com.kyle.venue.service.VenueFeedbackService;
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
 * @since 2023-04-11
 */
@RestController
@RequestMapping("/venue/feedback")
public class VenueFeedbackController {
    @Autowired
    VenueFeedbackService venueFeedbackService;
    @Autowired
    private TokenManager tokenManager;

    @ApiOperation(value = "逻辑删除场地反馈")
    @DeleteMapping("{id}")
    public R removeVenueFeedback(@ApiParam(name="id",value = "场地ID",required = true) @PathVariable String id){
        boolean flag = venueFeedbackService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "带条件的分页查询场地反馈")
    @PostMapping("pageVenueFeedbackCondition/{current}/{limit}")
    public R pageVenueFeedbackCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) VenueFeedback venueFeedback){
        Map<String, Object> map = venueFeedbackService.pageVenueFeedbackCondition(current, limit, venueFeedback);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加场地反馈")
    @PostMapping("addVenueFeedback")
    public R addVenueFeedback(HttpServletRequest request, @RequestBody VenueFeedback venueFeedback){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        venueFeedback.setUserId(userId);
        boolean save = venueFeedbackService.save(venueFeedback);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据场地反馈ID查询")
    @GetMapping("getVenueFeedback/{id}")
    public R getVenueFeedback(@PathVariable String id){
        VenueFeedback venueFeedback = venueFeedbackService.getById(id);
        return R.ok().data("venueFeedback",venueFeedback);
    }

    @ApiOperation(value = "修改场地反馈")
    @PostMapping("updateVenueFeedback")
    public R updateVenueFeedback(@RequestBody VenueFeedback venueFeedback){
        boolean flag = venueFeedbackService.updateById(venueFeedback);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }
}

