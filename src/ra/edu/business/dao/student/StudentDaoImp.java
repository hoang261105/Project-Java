package ra.edu.business.dao.student;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Student;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDaoImp implements StudentDao {
    @Override
    public boolean checkLoginUser(String email, String password) {
        Connection conn = null;
        CallableStatement callSt = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call check_account_user(?,?)");
            callSt.setString(1, email);
            callSt.setString(2, password);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                return true;
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
    public List<Student> findAll() {
        return List.of();
    }

    @Override
    public boolean add(Student student) {
        return false;
    }

    @Override
    public boolean update(Student student, int option) {
        return false;
    }

    @Override
    public boolean delete(Student student) {
        return false;
    }


}
