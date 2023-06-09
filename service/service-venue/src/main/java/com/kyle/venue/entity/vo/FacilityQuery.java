package com.kyle.venue.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther kyle
 * @creat 2023-01-2:29
 */
@ApiModel(value = "Facility查询对象", description = "设施查询对象封装")
@Data
public class FacilityQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称模糊查询")
    private String name;

    @ApiModelProperty(value = "场地状态")
    private String state;

    @ApiModelProperty(value = "场地查询")
    private String venueId;

    @ApiModelProperty(value = "查询开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2019-12-01 10:10:10")
    private String end;
}
