package ra.edu.business.dao.student;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Student;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImp implements StudentDao {
    @Override
    public List<Student> findAll() {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> students = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call get_all_students()");

            ResultSet rs = callSt.executeQuery();
            students = new ArrayList<>();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("name"));
                student.setDateOfBirth(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhoneNumber(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                student.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), formatter));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return students;
    }

    @Override
    public boolean add(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call add_student(?,?,?,?,?,?,?)");

            callSt.setString(1, student.getStudentId());
            callSt.setString(2, student.getStudentName());
            callSt.setString(3, student.getDateOfBirth().toString());
            callSt.setString(4, student.getEmail());
            callSt.setBoolean(5, student.isSex());
            callSt.setString(6, student.getPhoneNumber());
            callSt.setString(7, student.getPassword());

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
    public boolean update(Student student, int option) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call update_student(?,?,?,?,?,?,?,?)");

            callSt.setString(1, student.getStudentId());
            callSt.setInt(2, option);

            if(option == 1){
                callSt.setString(3, student.getStudentName());
                callSt.setNull(4, Types.DATE);
                callSt.setNull(5, Types.VARCHAR);
                callSt.setNull(6, Types.BIT);
                callSt.setNull(7, Types.VARCHAR);
                callSt.setNull(8, Types.VARCHAR);
            } else if (option == 2) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setString(4, student.getDateOfBirth().toString());
                callSt.setNull(5, Types.VARCHAR);
                callSt.setNull(6, Types.BIT);
                callSt.setNull(7, Types.VARCHAR);
                callSt.setNull(8, Types.VARCHAR);
            } else if (option == 3) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setNull(4, Types.DATE);
                callSt.setString(5, student.getEmail());
                callSt.setNull(6, Types.BIT);
                callSt.setNull(7, Types.VARCHAR);
                callSt.setNull(8, Types.VARCHAR);
            } else if (option == 4) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setNull(4, Types.DATE);
                callSt.setNull(5, Types.VARCHAR);
                callSt.setBoolean(6, student.isSex());
                callSt.setNull(7, Types.VARCHAR);
                callSt.setNull(8, Types.VARCHAR);
            } else if (option == 5) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setNull(4, Types.DATE);
                callSt.setNull(5, Types.VARCHAR);
                callSt.setNull(6, Types.BIT);
                callSt.setString(7, student.getPhoneNumber());
                callSt.setNull(8, Types.VARCHAR);
            } else if (option == 6) {
                callSt.setNull(3, Types.VARCHAR);
                callSt.setNull(4, Types.DATE);
                callSt.setNull(5, Types.VARCHAR);
                callSt.setNull(6, Types.BIT);
                callSt.setNull(7, Types.VARCHAR);
                callSt.setString(8, student.getPassword());
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
    public boolean delete(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call delete_student(?)");

            callSt.setString(1, student.getStudentId());

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
    public List<Student> paginationStudents(int currentPage, int itemPerPage) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> students = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call paginate_students(?, ?)");

            callSt.setInt(1, currentPage);
            callSt.setInt(2, itemPerPage);

            ResultSet rs = callSt.executeQuery();
            students = new ArrayList<>();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("name"));
                student.setDateOfBirth(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhoneNumber(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                student.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), formatter));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return students;
    }

    @Override
    public boolean isExistStudentId(String studentId) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call is_exist_student_id(?)");
            callSt.setString(1, studentId);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("exist_stu_id");
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
    public boolean isExistEmail(String studentName) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call is_exist_email(?)");
            callSt.setString(1, studentName);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("exist_email");
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
    public boolean isExistPhone(String studentPhone) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call is_exist_phone(?)");
            callSt.setString(1, studentPhone);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("exist_phone");
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
    public Student findStudentById(String studentId) {
        Connection conn = null;
        CallableStatement callSt = null;
        Student student = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call find_student_by_id(?)");

            callSt.setString(1, studentId);
            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("name"));
                student.setDateOfBirth(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhoneNumber(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                student.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), formatter));
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return student;
    }

    @Override
    public boolean createAccount(Student student) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call add_account(?,?,?)");

            callSt.setString(1, student.getStudentId());
            callSt.setString(2, student.getEmail());
            callSt.setString(3, student.getPassword());

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
    public List<Student> searchStudent(String attribute, int option) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> students = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call search_student(?, ?, ?, ?)");

            if (option == 1) { // Tìm theo ID
                callSt.setString(1, attribute);
                callSt.setInt(2, option);
                callSt.setNull(3, Types.VARCHAR);
                callSt.setNull(4, Types.VARCHAR);
            } else if (option == 2) { // Tìm theo tên
                callSt.setNull(1, Types.CHAR);
                callSt.setInt(2, option);
                callSt.setString(3, attribute);
                callSt.setNull(4, Types.VARCHAR);
            } else if (option == 3) {
                callSt.setNull(1, Types.CHAR);
                callSt.setInt(2, option);
                callSt.setNull(3, Types.VARCHAR);
                callSt.setString(4, attribute);
            }

            ResultSet rs = callSt.executeQuery();
            students = new ArrayList<>();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("name"));
                student.setDateOfBirth(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhoneNumber(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                student.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), formatter));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return students;
    }

    @Override
    public List<Student> sortStudent(int option) {
        Connection conn = null;
        CallableStatement callSt = null;
        List<Student> students = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call sort_student(?)");

            callSt.setInt(1, option);

            ResultSet rs = callSt.executeQuery();
            students = new ArrayList<>();

            while (rs.next()) {
                Student student = new Student();
                student.setStudentId(rs.getString("student_id"));
                student.setStudentName(rs.getString("name"));
                student.setDateOfBirth(LocalDate.parse(rs.getString("dob")));
                student.setEmail(rs.getString("email"));
                student.setSex(rs.getBoolean("sex"));
                student.setPhoneNumber(rs.getString("phone"));
                student.setPassword(rs.getString("password"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                student.setCreatedAt(LocalDateTime.parse(rs.getString("created_at"), formatter));
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return students;
    }

    @Override
    public boolean changePassword(String studentId, String newPassword) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call change_password(?, ?)");

            callSt.setString(1, studentId);
            callSt.setString(2, newPassword);

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
