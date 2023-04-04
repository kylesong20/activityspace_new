package com.kyle.venue.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.VenueClock;
import com.kyle.venue.entity.vo.GroupQuery;
import com.kyle.venue.entity.vo.VenueClockCount;
import com.kyle.venue.mapper.VenueClockMapper;
import com.kyle.venue.service.VenueClockService;
import com.kyle.venue.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2023-04-03
 */
@RestController
@RequestMapping("/venue/clock")
public class VenueClockController {
    @Autowired
    VenueClockService venueClockService;
    @Autowired
    VenueService venueService;
    @Autowired
    private TokenManager tokenManager;

    @PutMapping("/create/{venueName}")
    public R create(HttpServletRequest request, @PathVariable String venueName){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        VenueClock venueClock = new VenueClock();
        venueClock.setUserId(userId);
        venueClock.setVenueId(venueService.getOne(new QueryWrapper<Venue>().eq("name",venueName)).getId());
        venueClock.setVenueName(venueName);
        venueClockService.save(venueClock);
        return R.ok();
    }

    @GetMapping("/pageClock/{current}/{limit}")
    public R getClockList(HttpServletRequest request ,@PathVariable long current, @PathVariable long limit){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        Map<String, Object> map = venueClockService.pageClock(current, limit ,userId);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @GetMapping("mapClock")
    public R mapClock(){
        List<VenueClockCount> venueClockCounts = venueClockService.venueClockCount();
        return R.ok().data("venueClockCounts",venueClockCounts);
    }

}

