package com.kyle.activity.client;

import com.kyle.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther kyle
 * @creat 2023-01-15:51
 */
@FeignClient("service-ucenter")
@Component("ucenterClient")
public interface UcenterClient {
    @GetMapping("/ucenter/user/getUser/{id}")
    public R getById(@PathVariable("id") String id);
}
