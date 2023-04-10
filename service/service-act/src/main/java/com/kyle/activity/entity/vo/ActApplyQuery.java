package com.kyle.activity.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActApplyQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    private String num;
    private String name;
    private String clock;
    private String state;
    private String selectType;


}
