package com.kyle.activity.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActApplyQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private String num;
    private String name;
    private String clock;

}
