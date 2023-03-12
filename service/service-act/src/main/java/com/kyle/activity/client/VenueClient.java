package com.kyle.activity.client;

import com.kyle.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther kyle
 * @creat 2023-01-15:51
 */
@FeignClient("service-venue")
@Component
public interface VenueClient {
    @PostMapping("/venue/venue/updateVenueState")
    public R updateVenueState(@RequestParam("venueIds") String[] venueIds,@RequestParam("flag") boolean flag);
}
