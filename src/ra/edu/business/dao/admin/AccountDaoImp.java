package ra.edu.business.dao.admin;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.AccStatus;
import ra.edu.business.model.Account;
import ra.edu.business.model.Role;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImp implements AccountDao {
    @Override
    public Account login(String email, String password) {
        Connection conn = null;
        CallableStatement callSt = null;
        Account account = null;

        try {
            conn = ConnectionDB.openConnection();
            callSt = conn.prepareCall("call check_account(?,?)");

            callSt.setString(1, email);
            callSt.setString(2, password);

            ResultSet rs = callSt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setEmail(rs.getString("email"));
                account.setPassword(rs.getString("password"));
                account.setRole(Role.valueOf(rs.getString("role")));
                account.setStatus(AccStatus.valueOf(rs.getString("status")));
            }
        } catch (SQLException e) {
            System.err.println("Có lỗi xử lý SQL " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            ConnectionDB.closeConnection(conn, callSt);
        }
        return account;
    }
}
