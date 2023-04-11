package com.kyle.venue.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther kyle
 * @creat 2022-12-22:56
 */
@Data
public class VenueGroupVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    private String num;

    private String name;

    private Boolean state;

    private String groupId;

    private String groupName;

    private Boolean stared;
}
