package com.kyle.activity.client;

import com.kyle.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @auther kyle
 * @creat 2023-04-1:06
 */

public class AdminDegradeFeignClient implements AdminClient{

    @Override
    public R doAssign(String id) {
        return R.error().message("获取用户出错！");
    }
}
