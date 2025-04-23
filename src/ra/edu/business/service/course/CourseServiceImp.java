package ra.edu.business.service.course;

import ra.edu.business.dao.course.CourseDao;
import ra.edu.business.dao.course.CourseDaoImp;
import ra.edu.business.model.Course;

import java.util.List;

public class CourseServiceImp implements CourseService {
    private CourseDao courseDao;

    public CourseServiceImp() {
        courseDao = new CourseDaoImp();
    }

    @Override
    public boolean checkExistCourseId(String courseId) {
        return courseDao.checkExistCourseId(courseId);
    }

    @Override
    public boolean checkExistCourseName(String courseName, String excludeId) {
        return courseDao.checkExistCourseName(courseName, excludeId);
    }

    @Override
    public List<Course> paginationCourse(int currentPage, int itemPerPage) {
        return courseDao.paginationCourse(currentPage, itemPerPage);
    }

    @Override
    public Course findCourseById(String courseId) {
        return courseDao.findCourseById(courseId);
    }

    @Override
    public List<Course> searchCourseByName(String courseName) {
        return courseDao.searchCourseByName(courseName);
    }

    @Override
    public List<Course> sortCourse(int option) {
        return courseDao.sortCourse(option);
    }

    @Override
    public List<Course> findAllOfRole(String role) {
        return courseDao.findAllOfRole(role);
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public boolean add(Course course) {
        return courseDao.add(course);
    }

    @Override
    public boolean update(Course course, int option) {
        return courseDao.update(course, option);
    }

    @Override
    public boolean delete(Course course) {
        return courseDao.delete(course);
    }
}
