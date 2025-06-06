package ra.edu.business.dao.course;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Course;

import java.util.List;

public interface CourseDao extends AppDao<Course> {
    boolean checkExistCourseId(String courseId);

    boolean checkExistCourseName(String courseName, String excludeId);

    List<Course> paginationCourse(int currentPage, int itemPerPage);

    List<Course> findAll();

    Course findCourseById(String courseId);

    List<Course> searchCourseByName(String courseName);

    List<Course> sortCourse(int option);

    List<Course> findAllOfRole(String role);
}
