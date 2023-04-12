package com.kyle.activity.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.activity.client.VenueClient;
import com.kyle.activity.entity.ActVen;
import com.kyle.activity.entity.Activity;
import com.kyle.activity.entity.Venue;
import com.kyle.activity.entity.vo.ActivityQuery;
import com.kyle.activity.service.ActVenService;
import com.kyle.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 前端控制器
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
    @Autowired
    VenueClient venueClient;

    @GetMapping("{activityId}")
    public R getActVen(@PathVariable String activityId) {
        ActVen actVen = actVenService.actVenList(activityId);
        return R.ok().data("actVen", actVen);
    }

    //根据场地ID
    @GetMapping("getActivitiesByVenueId/{venueId}")
    public R getActivitiesByVenueId(@PathVariable String venueId) {
        List<Activity> list = actVenService.getActivitiesByVenueId(venueId);
        return R.ok().data("list", list);
    }

    @PostMapping("getVenueAble")
    public R getVenueAble(@RequestBody Map<String, String> time) {
        List<Activity> venueNoAble = actVenService.getVenueAble(time);
        R allVenue = venueClient.findAllVenue();
        List<Map<String,Object>> venueList = (List<Map<String,Object>>) allVenue.getData().get("items");
        ArrayList<Object> list = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : venueList) {
            HashMap<String, Object> activityVenue = new HashMap<>();
            activityVenue.put("venue", stringObjectMap);
            for (Activity activity : venueNoAble) {
                if (Objects.equals(stringObjectMap.get("id"), activity.getVenId())) {
                    activityVenue.put("activity", activity);
                    break;
                }
            }
            list.add(activityVenue);
        }
        return R.ok().data("list",list);
    }
}

