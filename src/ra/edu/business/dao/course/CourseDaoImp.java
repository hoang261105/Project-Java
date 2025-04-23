package ra.edu.business.dao.course;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImp implements CourseDao {
    @Override
    public boolean checkExistCourseId(String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call is_exist_course_id(?)");
            callSt.setString(1, courseId);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_exist_courseId");
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
    public boolean checkExistCourseName(String courseName, String excludeId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call is_exist_name(?,?)");
            callSt.setString(1, courseName);

            if (excludeId == null || excludeId.isEmpty()) {
                callSt.setNull(2, Types.VARCHAR);
            } else {
                callSt.setString(2, excludeId);
            }

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_exist_name");
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
    public List<Course> paginationCourse(int currentPage, int itemPerPage) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> courses = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call pagination_courses(?,?)");
            callSt.setInt(1, currentPage);
            callSt.setInt(2, itemPerPage);

            ResultSet rs = callSt.executeQuery();
            courses = new ArrayList<>();

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreatedAt(rs.getDate("created_at").toLocalDate());
                courses.add(course);
            }

        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return courses;
    }

    @Override
    public List<Course> findAll() {
        return List.of();
    }

    @Override
    public Course findCourseById(String courseId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Course course = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call find_course_by_id(?)");
            callSt.setString(1, courseId);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreatedAt(rs.getDate("created_at").toLocalDate());
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return course;
    }

    @Override
    public List<Course> searchCourseByName(String courseName) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> courses = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call search_course(?)");
            callSt.setString(1, courseName);

            ResultSet rs = callSt.executeQuery();
            courses = new ArrayList<>();

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreatedAt(rs.getDate("created_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return courses;
    }

    @Override
    public List<Course> sortCourse(int option) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> courses = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call sort_course(?)");
            callSt.setInt(1, option);

            ResultSet rs = callSt.executeQuery();
            courses = new ArrayList<>();

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getString("course_id"));
                course.setCourseName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreatedAt(rs.getDate("created_at").toLocalDate());
                courses.add(course);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return courses;
    }

    @Override
    public List<Course> findAllOfRole(String role) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Course> courses = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call get_all_courses(?)");

            callSt.setString(1, role);

            ResultSet rs = callSt.executeQuery();
            courses = new ArrayList<>();

            while (rs.next()) {
                Course course = new Course();

                if (role.equalsIgnoreCase("admin")){
                    course.setCourseId(rs.getString("course_id"));
                }
                course.setCourseName(rs.getString("name"));
                course.setDuration(rs.getInt("duration"));
                course.setInstructor(rs.getString("instructor"));
                course.setCreatedAt(rs.getDate("created_at").toLocalDate());
                courses.add(course);
            }

        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return courses;
    }

    @Override
    public boolean add(Course course) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call add_course(?, ?, ?, ?)");

            callSt.setString(1, course.getCourseId());
            callSt.setString(2, course.getCourseName());
            callSt.setInt(3, course.getDuration());
            callSt.setString(4, course.getInstructor());

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
    public boolean update(Course course, int option) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call update_course(?, ?, ?, ?, ?)");

            callSt.setString(1, course.getCourseId());
            callSt.setInt(2, option);
            if (option == 1) {
                callSt.setString(3, course.getCourseName());
                callSt.setNull(4, Types.INTEGER);
                callSt.setNull(5, Types.VARCHAR);
            } else if (option == 2) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setInt(4, course.getDuration());
                callSt.setNull(5, Types.VARCHAR);
            } else if (option == 3) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setNull(4, Types.INTEGER);
                callSt.setString(5, course.getInstructor());
            }
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
    public boolean delete(Course course) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call delete_course(?)");

            callSt.setString(1, course.getCourseId());

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
