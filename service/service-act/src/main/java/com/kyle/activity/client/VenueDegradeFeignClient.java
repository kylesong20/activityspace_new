package com.kyle.activity.client;

import com.kyle.util.R;
import org.springframework.stereotype.Component;

/**
 * @auther kyle
 * @creat 2023-01-17:52
 */
@Component
public class VenueDegradeFeignClient implements VenueClient {
    @Override
    public R updateVenueState(String[] venueIds, boolean flag) {
        return R.error().message("更新场地状态出错！");
    }
}
