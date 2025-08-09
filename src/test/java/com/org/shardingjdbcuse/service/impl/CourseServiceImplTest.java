package com.org.shardingjdbcuse.service.impl;

import com.org.shardingjdbcuse.entity.Course;
import com.org.shardingjdbcuse.service.CourseService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseServiceImplTest {

    @Resource
    private CourseService courseService;

    @Test
    void addCourse() {
        // 借助@builder()方法，创建 100个 Course 对象
        for (int i = 0; i < 10; i++) {
            Course course = Course.builder()
                    .cname("课程" + i)
                    .userId(1L)
                    .cstatus("正常")
                    .build();
            courseService.addCourse(course);
            System.out.println("添加课程：" + course);
        }
    }

    @Test
    void getById() {
        Course course = courseService.getById(1954131427708174338L);
    }
}