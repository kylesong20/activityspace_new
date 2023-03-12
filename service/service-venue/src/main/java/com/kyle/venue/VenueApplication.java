package com.kyle.venue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @auther kyle
 * @creat 2022-12-15:57
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.kyle"})
@EnableDiscoveryClient
public class VenueApplication {
    public static void main(String[] args) {
        SpringApplication.run(VenueApplication.class,args);
    }
}
