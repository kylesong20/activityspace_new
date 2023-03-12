package com.kyle.ucenter.client;

import com.kyle.util.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther kyle
 * @creat 2023-01-15:51
 */
@FeignClient("service-file")
@Component
public interface FileClient {

    @PostMapping("/actfile/file/delAvatar")
    public R delAvatar(@RequestParam("url") String url);
}
