package com.org.shardingjdbcuse.controller;

import com.org.shardingjdbcuse.entity.Course;
import com.org.shardingjdbcuse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/add")
    public Long add(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping("/{cid}")
    public Course get(@PathVariable Long cid) {
        return courseService.getById(cid);
    }
}
