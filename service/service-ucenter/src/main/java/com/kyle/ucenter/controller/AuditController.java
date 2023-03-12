package com.kyle.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.ucenter.entity.Audit;
import com.kyle.ucenter.entity.vo.AuditQuery;
import com.kyle.ucenter.service.AuditService;
import com.kyle.util.MD5;
import com.kyle.util.R;
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
 * @since 2022-12-30
 */
@RestController
@RequestMapping("/ucenter/audit")
//@CrossOrigin //解决跨域
public class AuditController {
    @Autowired
    AuditService auditService;

    @ApiOperation(value = "查询所有用户")
    @GetMapping(value="findAll")
    public R findAllAudit(){
        List<Audit> list = auditService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除用户")
    @DeleteMapping("{id}")
    public R removeAudit(@ApiParam(name="id",value = "用户ID",required = true) @PathVariable String id){
        boolean flag = auditService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询用户")
    @GetMapping("pageAudit/{current}/{limit}")
    public R pageListAudit(@PathVariable long current, @PathVariable long limit){
        //创建Page对象
        Page<Audit> auditPage = new Page<>(current,limit);
        //调用方法实现分页
        auditService.page(auditPage,null);
        long total = auditPage.getTotal();
        List<Audit> records = auditPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "带条件的分页查询用户")
    @PostMapping("pageAuditCondition/{current}/{limit}")
    public R pageAuditCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) AuditQuery auditQuery){
        Map<String, Object> map = auditService.pageAuditCondition(current, limit, auditQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("addAudit")
    public R addAudit(@RequestBody Audit audit){
        audit.setPassword(MD5.encrypt(audit.getPassword()));
        boolean save = auditService.save(audit);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据用户ID查询")
    @GetMapping("getAudit/{id}")
    public R getAudit(@PathVariable String id){
        Audit audit = auditService.getById(id);
        return R.ok().data("audit",audit);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("updateAudit")
    public R updateAudit(@RequestBody Audit audit){
        audit.setPassword(MD5.encrypt(audit.getPassword()));
        boolean flag = auditService.updateById(audit);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "查询负责人")
    @GetMapping("getOrganizationAudit")
    public R getOrganizationAudit(){
        QueryWrapper<Audit> wrapper = new QueryWrapper<>();
        wrapper.eq("is_leader",1);
        wrapper.orderByDesc("create_time");
        List<Audit> list = auditService.list(wrapper);
        return R.ok().data("items",list);
    }

}

