package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoGateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoGateWayApplication.class, args);
    }
}
