package ra.edu.business.model;

import java.time.LocalDateTime;

public class EnrolledCourses {
    private String courseId;
    private String courseName;
    private int duration;
    private String instructor;
    private LocalDateTime registeredAt;
    private Status status;

    public EnrolledCourses() {
    }

    public EnrolledCourses(String courseId, String courseName, int duration, String instructor, LocalDateTime registeredAt, Status status) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.instructor = instructor;
        this.registeredAt = registeredAt;
        this.status = status;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void displayData(){
        String cornflowerBlueText = "\u001B[38;5;33m";  // Mã màu ANSI cho chữ Cornflower Blue
        String reset = "\u001B[0m";  // Mã reset màu

        // Cập nhật trạng thái
        String displayStatus = status == Status.WAITING ? "Chờ phê duyệt" :
                status == Status.CONFIRMED ? "Đã được duyệt" :
                        status == Status.CANCELLED ? "Đã hủy" : "Từ chối";

        // In ra bảng với nền Cornflower Blue
        System.out.printf(cornflowerBlueText + "| %-15s | %-40s | %-15s | %-24s | %-20s | %-20s |" + reset + "\n",
                courseId, courseName, duration, instructor, registeredAt, displayStatus);
    }
}
