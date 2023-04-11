package com.kyle.venue.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kyle.security.security.TokenManager;
import com.kyle.util.R;
import com.kyle.venue.entity.VenUser;
import com.kyle.venue.entity.vo.VenueQuery;
import com.kyle.venue.service.VenUserService;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/venue/ven-user")
public class VenUserController {
    @Autowired
    VenUserService venUserService;
    @Autowired
    TokenManager tokenManager;

    @PutMapping("starVenue/{venueId}")
    public R starVenue(HttpServletRequest request,@PathVariable String venueId){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        VenUser venUser = new VenUser();
        venUser.setUserId(userId);
        venUser.setVenId(venueId);
        venUserService.save(venUser);
        return R.ok();
    }

    @ApiOperation(value = "带条件的分页连表查询场地和场地组")
    @PostMapping("pageVenueStarCondition/{current}/{limit}")
    public R pageVenueStarCondition(HttpServletRequest request,@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) VenueQuery venueQuery){
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        Map<String, Object> map = venUserService.pageVenueStarCondition(current, limit,venueQuery,userId);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @DeleteMapping("cancelStar/{venueId}")
    public R cancelStar(@PathVariable String venueId){
        venUserService.remove(new QueryWrapper<VenUser>().eq("ven_id",venueId));
        return R.ok();
    }

}

