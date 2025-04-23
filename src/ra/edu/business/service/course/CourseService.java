package ra.edu.business.service.course;

import ra.edu.business.model.Course;
import ra.edu.business.service.AppService;

import java.util.List;

public interface CourseService extends AppService<Course> {
    boolean checkExistCourseId(String courseId);

    boolean checkExistCourseName(String courseName, String excludeId);

    List<Course> paginationCourse(int currentPage, int itemPerPage);

    Course findCourseById(String courseId);

    List<Course> searchCourseByName(String courseName);

    List<Course> sortCourse(int option);

    List<Course> findAllOfRole(String role);
}
