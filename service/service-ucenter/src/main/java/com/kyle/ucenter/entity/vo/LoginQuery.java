package com.kyle.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther kyle
 * @creat 2022-12-18:41
 */
@ApiModel(value = "Login查询对象", description = "登录查询对象封装")
@Data
public class LoginQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号")
    private String username;

}
