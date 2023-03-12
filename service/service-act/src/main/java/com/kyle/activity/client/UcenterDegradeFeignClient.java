package com.kyle.activity.client;

import com.kyle.util.R;
import org.springframework.stereotype.Component;

/**
 * @auther kyle
 * @creat 2023-01-17:52
 */
@Component
public class UcenterDegradeFeignClient implements UcenterClient {
    @Override
    public R getById(String id) {
        return R.error().message("获取用户出错！");
    }
}
