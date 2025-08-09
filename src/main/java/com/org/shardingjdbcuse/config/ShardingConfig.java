package com.org.shardingjdbcuse.config;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@Configuration
public class ShardingConfig {

    @Value("${spring.profiles.active}")
    private String active;

    @Bean
    public DataSource initShardingSphereDataSource() throws SQLException, IOException {
        Resource resource = new ClassPathResource("shardingsphere-config-"+active+".yaml");
        // 检查资源是否存在
        if (!resource.exists()) {
            throw new IOException("配置文件不存在: shardingsphere-config-"+active+".yaml");
        }
        // 通过 InputStream 读取配置,**如果通过file读取打包后会报错**
        try (InputStream inputStream = resource.getInputStream()) {
            // 使用 ShardingSphere 的 YAML 工厂创建数据源
            return YamlShardingSphereDataSourceFactory.createDataSource(inputStream.readAllBytes());
        }
    }

}

