package com.kyle.activity.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Venue implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;
    private String num;
    private String name;
    private Boolean state;
    private String groupId;
    private String mapJson;
}