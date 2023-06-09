package com.kyle.ucenter.entity.vo;

import com.kyle.ucenter.entity.BUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther kyle
 * @creat 2022-12-17:13
 */
@ApiModel(value = "User查询对象", description = "用户查询对象封装")
@Data
public class UserQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号模糊查询")
    private String num;

    @ApiModelProperty(value = "名称模糊查询")
    private String name;

    @ApiModelProperty(value = "所属组织")
    private String organizationName;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
