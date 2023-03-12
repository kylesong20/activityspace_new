package com.kyle.organization.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther kyle
 * @creat 2022-12-19:42
 */
@ApiModel(value = "Organization查询对象", description = "组织查询对象封装")
@Data
public class OrganizationQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "组织名模糊查询")
    private String name;

    @ApiModelProperty(value = "负责人模糊查询")
    private String leaderName;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
