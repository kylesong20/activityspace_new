package com.kyle.ucenter.controller;


import com.kyle.ucenter.entity.vo.LoginQuery;
import com.kyle.ucenter.service.AdminService;
import com.kyle.util.JwtUtils;
import com.kyle.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @auther kyle
 * @creat 2022-11-20:22
 */
@RestController
@RequestMapping("/ucenter/user")
//@CrossOrigin //解决跨域
public class LoginController {

    @Autowired
    AdminService adminService;
    
    @PostMapping("login")
    public R login(@RequestBody LoginQuery loginQuery){
        System.out.println(loginQuery);
        String token = adminService.login(loginQuery);

        return R.ok().data("token",token);
    }

    @GetMapping("info")
    public R info(HttpServletRequest request){
        Map<String,String> info = JwtUtils.getMemberInfoByJwtToken(request);
        if (info != null) {
            String[] str = new String[]{"admin"};
            return R.ok().data("id", info.get("id"))
                    .data("name", info.get("name"))
                    .data("avatar", info.get("avatar"))
                    .data("roles",str)
                    .data("introduction", "hello world");
        }else{
            return R.error();
        }
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
