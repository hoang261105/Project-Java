package ra.edu.business.model;

import java.time.LocalDateTime;

public class Enrollment {
    private int enrollmentId;
    private String studentId;
    private String courseId;
    private LocalDateTime registeredAt;
    private Status status;

    public Enrollment() {
    }

    public Enrollment(int enrollmentId, String studentId, String courseId, LocalDateTime registeredAt, Status status) {
        this.enrollmentId = enrollmentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.registeredAt = registeredAt;
        this.status = status;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getRegistratedAt() {
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
}
