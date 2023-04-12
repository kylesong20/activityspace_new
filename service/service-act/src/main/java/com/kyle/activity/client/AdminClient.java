package com.kyle.activity.client;

import com.kyle.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @auther kyle
 * @creat 2023-04-1:06
 */
@FeignClient("service-acl")
@Component("adminClient")
public interface AdminClient {
    @GetMapping("/admin/acl/user/get/{id}")
    public R doAssign(@PathVariable String id);
}
