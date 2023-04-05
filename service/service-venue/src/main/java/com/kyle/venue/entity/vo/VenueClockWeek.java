package com.kyle.venue.entity.vo;

import lombok.Data;

/**
 * @auther kyle
 * @creat 2023-04-17:55
 */
@Data
public class VenueClockWeek {
    private String venueId;
    private String name;
    private String value;
    private String dayOfWeek;
}
