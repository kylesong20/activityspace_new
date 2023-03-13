package com.kyle.ucenter.controller;


import com.kyle.security.security.TokenManager;
import com.kyle.ucenter.entity.BUser;
import com.kyle.ucenter.entity.vo.LoginQuery;
import com.kyle.ucenter.service.AdminService;
import com.kyle.ucenter.service.BUserService;
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
    @Autowired
    BUserService bUserService;
    @Autowired
    TokenManager tokenManager;
    
    @PostMapping("login")
    public R login(@RequestBody LoginQuery loginQuery){
        System.out.println(loginQuery);
        String token = adminService.login(loginQuery);

        return R.ok().data("token",token);
    }

    @GetMapping("info")
    public R info(HttpServletRequest request){
        String jwtToken = request.getHeader("X-Token");
        String id = tokenManager.getUserIDToken(jwtToken);
        BUser user = bUserService.getById(id);
        if (user != null) {
//            String[] str = new String[]{"admin"};
            return R.ok().data("user",user);
        }else{
            return R.error();
        }
    }

    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }
}
