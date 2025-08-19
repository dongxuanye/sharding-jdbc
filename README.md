# 分库分表 sharding-jdbc例子
## 一、导入依赖
```xml
        <!-- Spring Boot starter web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- MyBatis-Plus -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>3.5.7</version>
        </dependency>

        <!-- ShardingSphere Spring Boot Starter (确认版本与你的配置兼容，例如 5.x) -->
        <!-- 升级后现依赖 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.24</version>
        </dependency>
        <!-- 升级后现依赖 -->
        <dependency>
            <groupId>org.apache.shardingsphere</groupId>
            <artifactId>shardingsphere-jdbc</artifactId>
            <version>5.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.yaml</groupId>
            <artifactId>snakeyaml</artifactId>
            <version>2.2</version>
        </dependency>

        <!-- MySQL 驱动（建议用新版驱动） -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>

        <!-- Lombok（可选，但推荐） -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- 测试 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```
## 二、运行sql文件
```sql
-- ========================
-- 库：sharding（db0）
-- ========================
USE sharding;

CREATE TABLE IF NOT EXISTS course_0 (
                                        cid BIGINT NOT NULL COMMENT '课程ID，分片键',
                                        cname VARCHAR(50) NOT NULL COMMENT '课程名称',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    cstatus VARCHAR(10) NOT NULL COMMENT '课程状态',
    PRIMARY KEY (cid) USING BTREE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci
    ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS course_1 (
                                        cid BIGINT NOT NULL COMMENT '课程ID，分片键',
                                        cname VARCHAR(50) NOT NULL COMMENT '课程名称',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    cstatus VARCHAR(10) NOT NULL COMMENT '课程状态',
    PRIMARY KEY (cid) USING BTREE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci
    ROW_FORMAT=DYNAMIC;

-- ========================
-- 库：sharding2（db1）
-- ========================
USE sharding2;

CREATE TABLE IF NOT EXISTS course_0 (
                                        cid BIGINT NOT NULL COMMENT '课程ID，分片键',
                                        cname VARCHAR(50) NOT NULL COMMENT '课程名称',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    cstatus VARCHAR(10) NOT NULL COMMENT '课程状态',
    PRIMARY KEY (cid) USING BTREE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci
    ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS course_1 (
                                        cid BIGINT NOT NULL COMMENT '课程ID，分片键',
                                        cname VARCHAR(50) NOT NULL COMMENT '课程名称',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    cstatus VARCHAR(10) NOT NULL COMMENT '课程状态',
    PRIMARY KEY (cid) USING BTREE
    ) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_general_ci
    ROW_FORMAT=DYNAMIC;

```
## 三、编写application.yml文件
```yml
server:
  port: 8013
  servlet:
    context-path: /api
mybatis-plus:
  configuration:
    # MyBatis 配置
    map-underscore-to-camel-case: true
    # 仅在开发环境打印日志
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
spring:
  profiles:
    active: dev
    application:
    name: sharding-jdbc-use
  datasource:
    driver-class-name: org.apache.shardingsphere.driver.ShardingSphereDriver
    url: jdbc:shardingsphere:classpath:shardingsphere-config-dev.yaml
```

## 四、编写分库分表规则配置文件
```yaml
dataSources:
  db0:
    dataSourceClassName: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/sharding?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: 123456
  db1:
    dataSourceClassName: com.alibaba.druid.pool.DruidDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/sharding2?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&allowMultiQueries=true&useSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: 123456
#配置分库分表规则
rules:
- !SHARDING
  keyGenerators:
    snowflake:
      type: SNOWFLAKE
      props:
        worker-id: 1
  shardingAlgorithms:
    db_alg:
      type: INLINE
      props:
        algorithm-expression: db${cid % 2}
    course_tbl_alg:
      type: INLINE
      props:
        algorithm-expression: course_${cid % 2}
  tables:
    course:
      actualDataNodes: db${0..1}.course_${0..1}
      keyGenerateStrategy:
        column: cid
        keyGeneratorName: snowflake
      # 分库策略--MOD
      databaseStrategy:
        standard:
          shardingColumn: cid
          shardingAlgorithmName: db_alg

      # 分表策略--Groovy表达式
      tableStrategy:
        standard:
          shardingColumn: cid # 分片键
          shardingAlgorithmName: course_tbl_alg
props:
  #是否打印分表SQL
  sql-show: true
  sql-comment-parse-enabled: true
```

## 五、编写读取配置
```java
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
```

## 六、编写业务代码
省略说明