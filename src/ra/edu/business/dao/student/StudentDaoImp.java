package ra.edu.business.dao.student;

import ra.edu.business.model.Student;
import java.util.List;

public class StudentDaoImp implements StudentDao {
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
