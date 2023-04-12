package com.kyle.organization.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.organization.service.OrganizationIntroductionService;
import com.kyle.organization.service.OrganizationService;
import com.kyle.organization.entity.Organization;
import com.kyle.organization.entity.OrganizationIntroduction;
import com.kyle.organization.entity.vo.OrganizationQuery;
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
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/organization/organization")
//@CrossOrigin //解决跨域
public class OrganizationController {
    @Autowired
    OrganizationService organizationService;

    @Autowired
    OrganizationIntroductionService organizationIntroductionService;

    @ApiOperation(value = "查询所有组织")
    @GetMapping(value="findAll")
    public R findAllOrganization(){
        List<Organization> list = organizationService.list(null);
        return R.ok().data("items",list);
    }
    @PutMapping(value="changeLeader/{leaderId}/{leaderName}/{rowId}")
    public R changeLeader(@PathVariable String leaderId, @PathVariable String leaderName, @PathVariable String rowId){
        organizationService.update(new UpdateWrapper<Organization>().set("leader_id",leaderId).set("leader_name",leaderName).eq("id",rowId));
        return R.ok();
    }

    @ApiOperation(value = "逻辑删除组织")
    @DeleteMapping("{id}")
    public R removeOrganization(@ApiParam(name="id",value = "组织ID",required = true) @PathVariable String id){
        boolean flag = organizationService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询组织")
    @GetMapping("pageOrganization/{current}/{limit}")
    public R pageListOrganization(@PathVariable long current, @PathVariable long limit){
        //创建Page对象
        Page<Organization> organizationPage = new Page<>(current,limit);
        //调用方法实现分页
        organizationService.page(organizationPage,null);
        long total = organizationPage.getTotal();
        List<Organization> records = organizationPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "带条件的分页查询组织")
    @PostMapping("pageOrganizationCondition/{current}/{limit}")
    public R pageOrganizationCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) OrganizationQuery organizationQuery){
        Map<String, Object> map = organizationService.pageOrganizationCondition(current, limit, organizationQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加组织")
    @PostMapping("addOrganization/{organizationIntroduction}")
    public R addOrganization(@RequestBody Organization organization,@PathVariable String organizationIntroduction){

        OrganizationIntroduction introduction = new OrganizationIntroduction();
        introduction.setIntroduction(organizationIntroduction);

        boolean organizationIntroductionId = organizationIntroductionService.save(introduction);
        boolean save = false;
        if (organizationIntroductionId){
            organization.setIntroductionId(introduction.getId());
            save = organizationService.save(organization);
        }
        if (organizationIntroductionId&&save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据组织ID查询")
    @GetMapping("getOrganization/{id}")
    public R getOrganization(@PathVariable String id){
        Organization organization = organizationService.getById(id);
        String organizationIntroduce = organizationService.getIntroduceById(organization.getIntroductionId());
        return R.ok().data("organization",organization).data("organizationIntroduce",organizationIntroduce);
    }

    @ApiOperation(value = "修改组织")
    @PostMapping("updateOrganization/{organizationIntroduction}")
    public R updateOrganization(@RequestBody Organization organization,@PathVariable String organizationIntroduction){
        OrganizationIntroduction introduction = new OrganizationIntroduction();
        introduction.setIntroduction(organizationIntroduction);
        introduction.setId(organization.getIntroductionId());
        boolean organizationIntroductionId = organizationIntroductionService.updateById(introduction);

        boolean flag = organizationService.updateById(organization);
        if (organizationIntroductionId&&flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据组织ID查询组织简介")
    @GetMapping("getIntroduce/{id}")
    public R getOrganizationsIntroduce(@PathVariable String id){
        String organizationIntroduce = organizationService.getIntroduceById(id);
        return R.ok().data("organizationIntroduce",organizationIntroduce);
    }

    @GetMapping("getOrganizationIntroduce/{id}")
    public R getOrganizationIntroduce(@PathVariable String id){
        Map<String, Object> organization = organizationService.getOrganizationIntroduce(id);
        return R.ok().data("organization",organization);
    }
}

