package com.kyle.file.config;

/**
 * @auther kyle
 * @creat 2022-12-11:26
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 图片绝对地址与虚拟地址映射
 */

@Configuration
public class MyConfig extends WebMvcConfigurerAdapter {
    @Value("${pic.dir}")
    private String picDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//文件磁盘图片url 映射
//配置server虚拟路径，handler为前台访问的目录，locations为files相对应的本地路径
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+picDir);
    }
}