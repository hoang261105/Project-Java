package ra.edu.business.dao.enrollment;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDaoImp implements EnrollmentDao {
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
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call register_course(?, ?)");

            callSt.setString(1, enrollment.getStudentId());
            callSt.setString(2, enrollment.getCourseId());

            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean existsEnrollment(String studentId, String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call check_enrollment_exist(?,?)");
            callSt.setString(1, studentId);
            callSt.setString(2, courseId);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("enrollment_exist");
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public List<EnrolledCourses> getEnrolledCoursesByStudent(String studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<EnrolledCourses> listEnrolled = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call show_register_course(?)");

            callSt.setString(1, studentId);

            ResultSet rs = callSt.executeQuery();
            listEnrolled = new ArrayList<>();

            while (rs.next()) {
                EnrolledCourses enrollment = new EnrolledCourses();
                enrollment.setCourseId(rs.getString("course_id"));
                enrollment.setCourseName(rs.getString("name"));
                enrollment.setDuration(rs.getInt("duration"));
                enrollment.setInstructor(rs.getString("instructor"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                enrollment.setRegisteredAt(LocalDateTime.parse(rs.getString("registered_at"), formatter));
                enrollment.setStatus(Status.valueOf(rs.getString("status")));
                listEnrolled.add(enrollment);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listEnrolled;
    }

    @Override
    public boolean cancelRegistration(String studentId, String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call cancel_register_course(?,?)");

            callSt.setString(1, studentId);
            callSt.setString(2, courseId);

            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public Enrollment findEnrollment(String studentId, String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Enrollment enrollment = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call find_register_course(?,?)");

            callSt.setString(1, studentId);
            callSt.setString(2, courseId);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                enrollment = new Enrollment();
                enrollment.setEnrollmentId(rs.getInt("id"));
                enrollment.setStudentId(rs.getString("student_id"));
                enrollment.setCourseId(rs.getString("course_id"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                enrollment.setRegisteredAt(LocalDateTime.parse(rs.getString("registered_at"), formatter));
                enrollment.setStatus(Status.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return enrollment;
    }

    @Override
    public List<CourseRegistrationInfo> findCourseRegistrationByStudent(String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<CourseRegistrationInfo> listEnrolled = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call register_course_by_student(?)");

            callSt.setString(1, courseId);

            ResultSet rs = callSt.executeQuery();
            listEnrolled = new ArrayList<>();

            while (rs.next()) {
                CourseRegistrationInfo enrollment = new CourseRegistrationInfo();
                enrollment.setCourseId(rs.getString("course_id"));
                enrollment.setCourseName(rs.getString("course_name"));
                enrollment.setStudentId(rs.getString("student_id"));
                enrollment.setStudentName(rs.getString("student_name"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                enrollment.setRegisteredAt(LocalDateTime.parse(rs.getString("registered_at"), formatter));
                enrollment.setStatus(Status.valueOf(rs.getString("status")));
                listEnrolled.add(enrollment);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return listEnrolled;
    }

    @Override
    public boolean studentApproval(String courseId, String studentId, int option) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call student_approval(?,?,?)");

            callSt.setString(1, courseId);
            callSt.setString(2, studentId);
            callSt.setInt(3, option);

            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }

    @Override
    public boolean removeStudentOfEnrollment(String studentId, String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call remove_student(?,?)");

            callSt.setString(1, courseId);
            callSt.setString(2, studentId);

            callSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return false;
    }
}
