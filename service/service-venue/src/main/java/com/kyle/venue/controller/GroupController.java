package com.kyle.venue.controller;


import com.kyle.util.R;
import com.kyle.venue.entity.Group;
import com.kyle.venue.entity.vo.GroupQuery;
import com.kyle.venue.service.GroupService;
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
@RequestMapping("/venue/group")
//@CrossOrigin //解决跨域
public class GroupController {
    @Autowired
    GroupService groupService;

    @ApiOperation(value = "查询所有场地组")
    @GetMapping(value="findAll")
    public R findAllGroup(){
        List<Group> list = groupService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除场地组")
    @DeleteMapping("{id}")
    public R removeGroup(@ApiParam(name="id",value = "场地ID",required = true) @PathVariable String id){
        boolean flag = groupService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "带条件的分页查询场地组")
    @PostMapping("pageGroupCondition/{current}/{limit}")
    public R pageGroupCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) GroupQuery groupQuery){
        Map<String, Object> map = groupService.pageGroupCondition(current, limit, groupQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加场地组")
    @PostMapping("addGroup")
    public R addGroup(@RequestBody Group group){
        boolean save = groupService.save(group);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据场地组ID查询")
    @GetMapping("getGroup/{id}")
    public R getGroup(@PathVariable String id){
        Group group = groupService.getById(id);
        return R.ok().data("group",group);
    }

    @ApiOperation(value = "修改场地组")
    @PostMapping("updateGroup")
    public R updateGroup(@RequestBody Group group){
        boolean flag = groupService.updateById(group);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }
}

