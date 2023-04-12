package com.kyle.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kyle.security.security.TokenManager;
import com.kyle.ucenter.client.FileClient;
import com.kyle.ucenter.entity.BUser;
import com.kyle.ucenter.entity.vo.PasswordChange;
import com.kyle.ucenter.entity.vo.UserQuery;
import com.kyle.ucenter.service.BUserService;
import com.kyle.util.JwtUtils;
import com.kyle.util.MD5;
import com.kyle.util.R;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
 * @since 2022-12-29
 */
@RestController
@RequestMapping("/ucenter/user")
//@CrossOrigin //解决跨域
public class BUserController {
    @Autowired
    BUserService userService;

    @Autowired
    FileClient fileClient;

    @Autowired
    TokenManager tokenManager;

    @ApiOperation(value = "查询所有用户")
    @GetMapping(value="findAll")
    public R findAllUser(){
        List<BUser> list = userService.list(null);
        return R.ok().data("items",list);
    }

    @ApiOperation(value = "逻辑删除用户")
    @DeleteMapping("{id}")
    public R removeUser(@ApiParam(name="id",value = "用户ID",required = true) @PathVariable String id){
//        User user = userService.getById(id);
//        String avatarUrl = user.getAvatar();
//        if (!StringUtils.isEmpty(avatarUrl)){
//            R r = fileClient.delAvatar(avatarUrl);
//            if(r.getCode() == 20001){
//                throw new MyException(20001,"删除头像失败...");
//            }
//        }

        boolean flag = userService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "分页查询用户")
    @GetMapping("pageUser/{current}/{limit}")
    public R pageListUser(@PathVariable long current, @PathVariable long limit){
        //创建Page对象
        Page<BUser> userPage = new Page<>(current,limit);
        //调用方法实现分页
        userService.page(userPage,null);
        long total = userPage.getTotal();
        List<BUser> records = userPage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @ApiOperation(value = "带条件的分页查询用户")
    @PostMapping("pageUserCondition/{current}/{limit}")
    public R pageUserCondition(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) UserQuery userQuery){
        Map<String, Object> map = userService.pageUserCondition(current, limit, userQuery);
        return R.ok().data("total",map.get("total")).data("rows",map.get("rows"));
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("addUser")
    public R addUser(@RequestBody BUser user){
        user.setPassword(MD5.encrypt(user.getPassword()));
        boolean save = userService.save(user);
        if (save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation(value = "根据用户ID查询")
    @GetMapping("getUser/{id}")
    public R getUser(@PathVariable("id") String id){
        System.out.println(id);
        BUser user = userService.getById(id);
        return R.ok().data("user",user);
    }

    @ApiOperation(value = "修改用户")
    @PostMapping("updateUser")
    public R updateUser(@RequestBody BUser user){
        user.setPassword(MD5.encrypt(user.getPassword()));
        boolean flag = userService.updateById(user);
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }

    @PostMapping("updateUserNameOrAvatar")
    public R updateUserNameOrAvatar(@RequestBody BUser user){
        if (StringUtils.isEmpty(user.getName())||StringUtils.isEmpty(user.getAvatar()))
            return R.error().message("请输入信息");
        String avatar = user.getAvatar();
        user.setAvatar(avatar.substring(avatar.lastIndexOf("file")-1));
        boolean flag = userService.update(new UpdateWrapper<BUser>()
                .set("name",user.getName())
                .set("avatar",user.getAvatarEnd())
                .eq("id",user.getId()));
        if (flag){
            return  R.ok();
        }else {
            return R.error();
        }
    }

    @ApiOperation(value = "查询负责人")
    @GetMapping("getOrganizationUser/{oid}")
    public R getOrganizationUser(@PathVariable String oid){
        QueryWrapper<BUser> wrapper = new QueryWrapper<>();
        wrapper.eq("organization_id",oid);
        wrapper.orderByDesc("create_time");
        List<BUser> list = userService.list(wrapper);
        return R.ok().data("items",list);
    }

    @PostMapping("changePassword")
    public R changePassword(@RequestBody PasswordChange passwordChange, HttpServletRequest request) {
        String token = request.getHeader("X-token");
        String userId = tokenManager.getUserIDToken(token);
        if (StringUtils.isEmpty(userId))
            return R.error().message("账号异常");
        BUser user = userService.getById(userId);
        if (!MD5.encrypt(passwordChange.getOldValue()).equals(user.getPassword())) {
            return R.error().message("密码错误");
        }
        if (!passwordChange.getNewValue().equals(passwordChange.getNewValue2())) {
            return R.error().message("两次密码不一致");
        }
        user.setPassword(MD5.encrypt(passwordChange.getNewValue()));
        userService.update(new UpdateWrapper<BUser>().set("password",user.getPassword()).eq("id",user.getId()));
        return R.ok();
    }

}

