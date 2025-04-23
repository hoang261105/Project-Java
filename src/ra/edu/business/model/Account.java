package ra.edu.business.model;

import ra.edu.business.IApp;
import ra.edu.validate.RegexRule;
import ra.edu.validate.StringRule;
import ra.edu.validate.Validator;

import java.util.Scanner;

public class Account implements IApp {
    private String id;
    private String email;
    private String password;
    private Role role;
    private AccStatus status;

    public Account() {
    }

    public Account(String id, String email, String password, Role role, AccStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public AccStatus getStatus() {
        return status;
    }

    public void setStatus(AccStatus status) {
        this.status = status;
    }

    @Override
    public void inputData(Scanner sc) {
        this.email = Validator.validateInputRegex(sc, "Nhập email: ", new RegexRule("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$", "Email không hợp lệ"));

        this.password = Validator.validateInputString(sc, "Nhập mật khẩu: ", new StringRule(0, 255, false, "Mật khẩu không hợp lệ"));
    }

    public void displayData() {

    }
}
