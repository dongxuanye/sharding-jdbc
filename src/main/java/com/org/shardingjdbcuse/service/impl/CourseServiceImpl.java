package com.org.shardingjdbcuse.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.org.shardingjdbcuse.entity.Course;
import com.org.shardingjdbcuse.mapper.CourseMapper;
import com.org.shardingjdbcuse.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Override
    @Transactional
    public Long addCourse(Course course) {
        // 不设置 cid，让 ShardingSphere 的 key-generator 生成
        this.save(course);
        return course.getCid();
    }

    @Override
    public Course getById(Long cid) {
        return this.baseMapper.selectById(cid);
    }
}
