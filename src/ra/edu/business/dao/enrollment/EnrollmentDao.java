package ra.edu.business.dao.enrollment;

import ra.edu.business.dao.AppDao;
import ra.edu.business.model.Course;
import ra.edu.business.model.CourseRegistrationInfo;
import ra.edu.business.model.EnrolledCourses;
import ra.edu.business.model.Enrollment;

import java.util.List;

public interface EnrollmentDao extends AppDao<Enrollment> {
    boolean registerCourse(Enrollment enrollment);

    boolean existsEnrollment(String studentId, String courseId);

    List<EnrolledCourses> getEnrolledCoursesByStudent(String studentId);

    boolean cancelRegistration(String studentId, String courseId);

    Enrollment findEnrollment(String studentId, String courseId);

    List<CourseRegistrationInfo> findCourseRegistrationByStudent(String courseId);

    boolean studentApproval(String courseId, String studentId, int option);

    boolean removeStudentOfEnrollment(String studentId, String courseId);
}
