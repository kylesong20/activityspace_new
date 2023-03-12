package com.kyle.venue.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @auther kyle
 * @creat 2023-01-13:39
 */
@Data
public class GroupQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称模糊查询")
    private String name;

}
