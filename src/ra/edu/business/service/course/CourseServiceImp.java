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
    public boolean checkExistCourseName(String courseName) {
        return courseDao.checkExistCourseName(courseName);
    }

    @Override
    public List<Course> paginationCourse(int currentPage) {
        return courseDao.paginationCourse(currentPage);
    }

    @Override
    public Course findCourseById(String courseId) {
        return courseDao.findCourseById(courseId);
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
        return false;
    }
}
