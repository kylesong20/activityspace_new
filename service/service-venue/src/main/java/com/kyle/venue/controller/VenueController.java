package com.kyle.venue.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.util.R;
import com.kyle.venue.entity.Venue;
import com.kyle.venue.entity.vo.VenueGroupVo;
import com.kyle.venue.entity.vo.VenueQuery;
import com.kyle.venue.service.VenueService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kyle
 * @since 2022-12-31
 */
@RestController
@RequestMapping("/venue/venue")
//@CrossOrigin //解决跨域
public class VenueController {
    @Autowired
    VenueService venueService;

    @ApiOperation(value = "查询所有场地")
    @GetMapping(value="findAll")
    public R findAllVenue(){
        List<Venue> list = venueService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除场地")
    @DeleteMapping("{id}")
    public R removeVenue(@ApiParam(name="id",value = "场地ID",required = true) @PathVariable String id){
        boolean flag = venueService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询场地")
    @GetMapping("pageVenue/{current}/{limit}")
    public R pageListVenue(@PathVariable long current, @PathVariable long limit){
        //创建Page对象
        Page<Venue> venuePage = new Page<>(current,limit);
        //调用方法实现分页
        venueService.page(venuePage,null);
        long total = venuePage.getTotal();
        List<Venue> records = venuePage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "带条件的分页查询场地")
    @PostMapping("pageVenueCondition/{current}/{limit}")
    public R pageVenueCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) VenueQuery venueQuery){
        Map<String, Object> map = venueService.pageVenueCondition(current, limit, venueQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "带条件的分页连表查询场地和场地组")
    @PostMapping("pageVenueGroupCondition/{current}/{limit}")
    public R pageVenueGroupCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) VenueQuery venueQuery, VenueGroupVo venueGroupVo){
        Map<String, Object> map = venueService.pageVenueGroupCondition(current, limit, venueGroupVo,venueQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加场地")
    @PostMapping("addVenue")
    public R addVenue(@RequestBody Venue venue){
        boolean save = venueService.save(venue);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据场地ID查询")
    @GetMapping("getVenue/{id}")
    public R getVenue(@PathVariable String id){
        Venue venue = venueService.getById(id);
        return R.ok().data("venue",venue);
    }

    @ApiOperation(value = "根据场地ID查询场地和组")
    @GetMapping("getVenueGroup/{id}")
    public R getVenueGroup(@PathVariable String id){
        VenueGroupVo venueGroupVo = venueService.getVenueGroupById(id);
        return R.ok().data("venue",venueGroupVo);
    }

    @ApiOperation(value = "修改场地")
    @PostMapping("updateVenue")
    public R updateVenue(@RequestBody Venue venue){
        boolean flag = venueService.updateById(venue);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("updateVenueState")
    public R updateVenueState(@RequestParam("venueIds") String[] venueIds,@RequestParam("flag") boolean flag){
        System.out.println("---------------"+flag);
        if (!flag){
            for (String venueId : venueIds) {
                venueService.update(new UpdateWrapper<Venue>().eq("id", venueId).set("state", 0));
            }
        }else {
            for (String venueId : venueIds) {
                venueService.update(new UpdateWrapper<Venue>().eq("id", venueId).set("state", 1));
            }
        }
        return  R.ok();
    }
}

