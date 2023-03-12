package com.kyle.venue.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther kyle
 * @creat 2023-01-2:28
 */
@Data
public class FacilityVenueVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Date createTime;

    private Date updateTime;

    private Boolean isDelete;

    private String name;

    private Boolean state;

    private String venueId;

    private String venueName;
}
