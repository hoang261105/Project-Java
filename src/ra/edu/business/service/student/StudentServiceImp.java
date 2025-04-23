package ra.edu.business.service.student;

import ra.edu.business.dao.student.StudentDao;
import ra.edu.business.dao.student.StudentDaoImp;
import ra.edu.business.model.Student;

import java.util.List;

public class StudentServiceImp implements StudentService {
    private StudentDao studentDao;

    public StudentServiceImp() {
        studentDao = new StudentDaoImp();
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public boolean add(Student student) {
        return studentDao.add(student);
    }

    @Override
    public boolean update(Student student, int option) {
        return studentDao.update(student, option);
    }

    @Override
    public boolean delete(Student student) {
        return studentDao.delete(student);
    }

    @Override
    public List<Student> paginationStudents(int currentPage, int itemPerPage) {
        return studentDao.paginationStudents(currentPage, itemPerPage);
    }

    @Override
    public boolean isExistStudentId(String studentId) {
        return studentDao.isExistStudentId(studentId);
    }

    @Override
    public boolean isExistEmail(String studentName) {
        return studentDao.isExistEmail(studentName);
    }

    @Override
    public boolean isExistPhone(String studentPhone) {
        return studentDao.isExistPhone(studentPhone);
    }

    @Override
    public Student findStudentById(String studentId) {
        return studentDao.findStudentById(studentId);
    }

    @Override
    public boolean createAccount(Student student) {
        return studentDao.createAccount(student);
    }

    @Override
    public List<Student> searchStudent(String attribute, int option) {
        return studentDao.searchStudent(attribute, option);
    }

    @Override
    public List<Student> sortStudent(int option) {
        return studentDao.sortStudent(option);
    }

    @Override
    public boolean changePassword(String studentId, String newPassword) {
        return studentDao.changePassword(studentId, newPassword);
    }
}
