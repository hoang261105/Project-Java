package ra.edu.business.model;

import ra.edu.business.IApp;

import java.util.Scanner;

public class Admin implements IApp {
    private int id;
    private String userName;
    private String password;

    public Admin() {
    }

    public Admin(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void inputData(Scanner sc) {
        System.out.println("Nhập tên tài khoản:");
        this.userName = sc.nextLine();

        System.out.println("Nhập mật khẩu:");
        this.password = sc.nextLine();
    }

    @Override
    public void displayData() {

    }
}
