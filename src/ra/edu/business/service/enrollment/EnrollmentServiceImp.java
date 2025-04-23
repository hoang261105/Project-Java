package ra.edu.business.service.enrollment;

import ra.edu.business.dao.enrollment.EnrollmentDao;
import ra.edu.business.dao.enrollment.EnrollmentDaoImp;
import ra.edu.business.model.CourseRegistrationInfo;
import ra.edu.business.model.EnrolledCourses;
import ra.edu.business.model.Enrollment;

import java.util.List;

public class EnrollmentServiceImp implements EnrollmentService {
    private EnrollmentDao enrollmentDao;

    public EnrollmentServiceImp() {
        enrollmentDao = new EnrollmentDaoImp();
    }

    @Override
    public List<Enrollment> findAll() {
        return List.of();
    }

    @Override
    public boolean add(Enrollment enrollment) {
        return false;
    }

    @Override
    public boolean update(Enrollment enrollment, int option) {
        return false;
    }

    @Override
    public boolean delete(Enrollment enrollment) {
        return false;
    }

    @Override
    public boolean registerCourse(Enrollment enrollment) {
        return enrollmentDao.registerCourse(enrollment);
    }

    @Override
    public boolean existsEnrollment(String studentId, String courseId) {
        return enrollmentDao.existsEnrollment(studentId, courseId);
    }

    @Override
    public List<EnrolledCourses> getEnrolledCoursesByStudent(String studentId) {
        return enrollmentDao.getEnrolledCoursesByStudent(studentId);
    }

    @Override
    public boolean cancelRegistration(String studentId, String courseId) {
        return enrollmentDao.cancelRegistration(studentId, courseId);
    }

    @Override
    public Enrollment findEnrollment(String studentId, String courseId) {
        return enrollmentDao.findEnrollment(studentId, courseId);
    }

    @Override
    public List<CourseRegistrationInfo> findCourseRegistrationByStudent(String courseId) {
        return enrollmentDao.findCourseRegistrationByStudent(courseId);
    }

    @Override
    public boolean studentApproval(String courseId, String studentId, int option) {
        return enrollmentDao.studentApproval(courseId, studentId, option);
    }

    @Override
    public boolean removeStudentOfEnrollment(String studentId, String courseId) {
        return enrollmentDao.removeStudentOfEnrollment(studentId, courseId);
    }
}
