package com.kyle.activity.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther kyle
 * @creat 2022-12-20:58
 */
@ApiModel(value = "Activity查询对象", description = "Activity查询对象封装")
@Data
public class ActivityQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称模糊查询")
    private String name;

    @ApiModelProperty(value = "场地状态")
    private String state;

    @ApiModelProperty(value = "搜索类型")
    private String selectType;

    private String open;
    private String organizationId;

    @ApiModelProperty(value = "查询活动开始时间", example = "2019-01-01 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

}
