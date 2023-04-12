package com.kyle.venue.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VenueClockCount implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private Long value;
    private String name;
    private String userId;

}

