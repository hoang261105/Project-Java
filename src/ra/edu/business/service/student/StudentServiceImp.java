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
    public boolean checkLoginUser(String email, String password) {
        return studentDao.checkLoginUser(email, password);
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
