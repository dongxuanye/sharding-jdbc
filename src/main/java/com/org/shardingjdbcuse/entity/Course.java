package com.org.shardingjdbcuse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("course")
public class Course {
    // 使用 IdType.INPUT 以便让 ShardingSphere 在执行时生成（如果不手动赋值）
    @TableId(type = IdType.ASSIGN_ID)
    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;
}
