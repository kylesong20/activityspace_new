package com.kyle.activity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.activity.entity.ActVen;
import com.kyle.activity.service.ActVenService;
import com.kyle.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2023-01-08
 */
@RestController
@RequestMapping("/activity/act-ven")
public class ActVenController {
    @Autowired
    ActVenService actVenService;

    @GetMapping("{activityId}")
    public R getActVen(@PathVariable String activityId){
        List<ActVen> list = actVenService.list(new QueryWrapper<ActVen>().eq("act_id", activityId));
        return R.ok().data("list",list);
    }
}

