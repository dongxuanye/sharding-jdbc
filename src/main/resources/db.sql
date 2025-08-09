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
