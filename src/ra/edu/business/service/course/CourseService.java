package ra.edu.business.service.course;

import ra.edu.business.model.Course;
import ra.edu.business.service.AppService;

import java.util.List;

public interface CourseService extends AppService<Course> {
    boolean checkExistCourseId(String courseId);

    boolean checkExistCourseName(String courseName);

    List<Course> paginationCourse(int currentPage);

    Course findCourseById(String courseId);
}
