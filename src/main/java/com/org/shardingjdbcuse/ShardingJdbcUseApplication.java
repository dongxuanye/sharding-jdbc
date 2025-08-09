package com.org.shardingjdbcuse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.org.shardingjdbcuse.mapper")
@SpringBootApplication
public class ShardingJdbcUseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcUseApplication.class, args);
    }

}
