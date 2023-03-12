package com.kyle.activity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.activity.entity.SiteMessage;
import com.kyle.activity.service.SiteMessageService;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2023-01-04
 */
@RestController
@RequestMapping("/activity/sitemessage")
public class SiteMessageController {
    @Autowired
    private SiteMessageService siteMessageService;

    @Autowired
    private TokenManager tokenManager;
    /**
     * 查询所有站内消息
     * @return
     */
    @GetMapping("/msg/findAll")
    public R findAll(HttpServletRequest request){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        List<SiteMessage> siteMessages = siteMessageService.list(new QueryWrapper<SiteMessage>().eq("user_id", userId));
        return R.ok().data("siteMessages",siteMessages);
    }

    @GetMapping("/msg/findOne/{id}")
    public R findOne(@PathVariable("id")String id,
                               HttpServletRequest request){
//        String token = request.getHeader("X-token");
//        String userId = tokenManager.getUserIDToken(token);
        SiteMessage siteMessage = siteMessageService.getById(id);
        return R.ok().data("siteMessage",siteMessage);
    }

//    /**
//     * 修改消息
//     * @param id
//     */
//    @PutMapping("/msg/{id}")
//    public void readMsg(@PathVariable("id")String id,HttpServletRequest request){
//        String token = request.getHeader("X-token");
//        String userId = tokenManager.getUserIDToken(token);
//        SiteMessage siteMessage = siteMessageService.getById(userId);
////        siteMessage.setIsRead()
//        siteMessageService.updateById(siteMessage);
//    }


}

