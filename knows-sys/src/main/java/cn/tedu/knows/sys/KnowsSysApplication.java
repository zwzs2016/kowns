package cn.tedu.knows.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.tedu.knows.sys.mapper")

public class KnowsSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(KnowsSysApplication.class, args);
    }

}
