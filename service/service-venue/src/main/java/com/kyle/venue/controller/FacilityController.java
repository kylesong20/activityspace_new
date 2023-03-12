package com.kyle.venue.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.util.R;
import com.kyle.venue.entity.Facility;
import com.kyle.venue.entity.vo.FacilityVenueVo;
import com.kyle.venue.entity.vo.FacilityQuery;
import com.kyle.venue.service.FacilityService;
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
@RequestMapping("/venue/facility")
public class FacilityController {
    @Autowired
    FacilityService facilityService;

    @ApiOperation(value = "查询所有设施")
    @GetMapping(value="findAll")
    public R findAllFacility(){
        List<Facility> list = facilityService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除设施")
    @DeleteMapping("{id}")
    public R removeFacility(@ApiParam(name="id",value = "设施ID",required = true) @PathVariable String id){
        boolean flag = facilityService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询设施")
    @GetMapping("pageFacility/{current}/{limit}")
    public R pageListFacility(@PathVariable long current, @PathVariable long limit){
        //创建Page对象
        Page<Facility> facilityPage = new Page<>(current,limit);
        //调用方法实现分页
        facilityService.page(facilityPage,null);
        long total = facilityPage.getTotal();
        List<Facility> records = facilityPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "带条件的分页查询设施")
    @PostMapping("pageFacilityCondition/{current}/{limit}")
    public R pageFacilityCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) FacilityQuery facilityQuery){
        Map<String, Object> map = facilityService.pageFacilityCondition(current, limit, facilityQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "带条件的分页连表查询设施和场地")
    @PostMapping("pageFacilityVenueCondition/{current}/{limit}")
    public R pageFacilityVenueCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) FacilityQuery facilityQuery, FacilityVenueVo facilityVenueVo){
        Map<String, Object> map = facilityService.pageFacilityVenueCondition(current, limit, facilityVenueVo,facilityQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加设施")
    @PostMapping("addFacility")
    public R addFacility(@RequestBody Facility facility){
        boolean save = facilityService.save(facility);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据设施ID查询")
    @GetMapping("getFacility/{id}")
    public R getFacility(@PathVariable String id){
        Facility facility = facilityService.getById(id);
        return R.ok().data("facility",facility);
    }

    @ApiOperation(value = "根据设施ID查询场地和设施")
    @GetMapping("getFacilityVenue/{id}")
    public R getFacilityVenue(@PathVariable String id){
        FacilityVenueVo facilityVenueVo = facilityService.getFacilityVenueById(id);
        return R.ok().data("facility",facilityVenueVo);
    }

    @ApiOperation(value = "修改设施")
    @PostMapping("updateFacility")
    public R updateFacility(@RequestBody Facility facility){
        boolean flag = facilityService.updateById(facility);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }
}

