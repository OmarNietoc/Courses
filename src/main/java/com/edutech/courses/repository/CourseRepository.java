package com.edutech.courses.repository;

import com.edutech.courses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsCoursesByCategoryId(Long categoryId);
    boolean existsCoursesByLevelId(Long levelId);
    Page<Course> findByCategoryId(Long categoryId, Pageable pageable);
    Page<Course> findByLevelId(Long levelId, Pageable pageable);
    Page<Course> findByCategoryIdAndLevelId(Long categoryId, Long levelId, Pageable pageable);
}
