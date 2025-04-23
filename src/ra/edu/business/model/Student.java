package ra.edu.business.model;

import ra.edu.business.IApp;
import ra.edu.validate.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Student implements IApp {
    private String studentId;
    private String studentName;
    private LocalDate dateOfBirth;
    private String email;
    private boolean sex;
    private String phoneNumber;
    private String password;
    private LocalDateTime createdAt;

    public Student() {
    }

    public Student(String studentId, String studentName, LocalDate dateOfBirth, String email, boolean sex, String phoneNumber, String password, LocalDateTime createdAt) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.createdAt = createdAt;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public void inputData(Scanner sc) {
        this.studentId = inputStudentId(sc);

        this.studentName = Validator.validateInputString(sc, "Nhập tên học viên: ", new StringRule(0, 100, false, "Tên không hợp lệ"));

        this.dateOfBirth = Validator.validateInputLocalDate(sc, "Nhập ngày sinh:");

        this.email = inputEmail(sc);

        this.sex = Validator.validateInputBoolean(sc, "Nhâp giới tính:");

        this.phoneNumber = inputPhoneNumber(sc);

        this.password = Validator.validateInputString(sc, "Nhập mật khẩu: ", new StringRule(0, 255, false, "Mật khẩu không hop lệ"));
    }

    public String inputStudentId(Scanner sc){
        String studentId = Validator.validateInputRegex(sc, "Nhập id học viên:", new RegexRule("^(SV).{3}$", "Id không hợp lệ"));
        return ExistValidator.validateExist(sc, studentId, new ExistRule("studentId", "Id học viên đã tồn tại"));
    }

    public String inputEmail(Scanner sc){
        String email = Validator.validateInputRegex(sc, "Nhập email: ", new RegexRule("^[a-zA-Z0-9._]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,6}$", "Email không hợp lệ"));
        return ExistValidator.validateExist(sc, email, new ExistRule("email", "Email đã tồn tại"));
    }

    public String inputPhoneNumber(Scanner sc){
        String phoneNumber = Validator.validateInputRegex(sc, "Nhập số điện thoại: ", new RegexRule("^(0(3|5|7|8|9)\\d{8}|\\+84(3|5|7|8|9)\\d{8})$", "Số điêện thoại không hợp lệ"));
        return ExistValidator.validateExist(sc, phoneNumber, new ExistRule("phone", "Số điện thoại đã tồn tại"));
    }

    public void displayData() {
        String royalBlueTextColor = "\u001B[38;5;69m";  // Mã màu ANSI cho màu chữ royal blue
        String reset = "\u001B[0m";  // Mã reset màu

        // In ra bảng với nền màu Royal Blue
        System.out.printf(royalBlueTextColor + "| %-10s | %-30s | %-15s | %-20s | %-10s | %-15s | %-19s |" + reset + "\n",
                studentId, studentName, dateOfBirth, email, sex ? "Nam" : "Nữ", phoneNumber, createdAt);
    }

}
