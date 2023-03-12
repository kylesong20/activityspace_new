package com.kyle.ucenter.client;

import com.kyle.util.R;
import org.springframework.stereotype.Component;

/**
 * @auther kyle
 * @creat 2023-01-17:52
 */
@Component
public class FileDegradeFeignClient implements FileClient {
    @Override
    public R delAvatar(String url) {
        return R.error().message("删除头像出错！");
    }
}
