package com.org.shardingjdbcuse.service;


import com.org.shardingjdbcuse.entity.Course;

public interface CourseService {
    Long addCourse(Course course);
    Course getById(Long cid);
}
