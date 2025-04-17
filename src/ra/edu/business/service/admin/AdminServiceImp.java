package ra.edu.business.service.admin;

import ra.edu.business.dao.admin.AdminDao;
import ra.edu.business.dao.admin.AdminDaoImp;
import ra.edu.business.model.Admin;

import java.util.List;

public class AdminServiceImp implements AdminService {
    private AdminDao adminDao;

    public AdminServiceImp() {
        adminDao = new AdminDaoImp();
    }

    @Override
    public boolean login(String username, String password) {
        return adminDao.login(username, password);
    }

    @Override
    public List<Admin> findAll() {
        return List.of();
    }

    @Override
    public boolean add(Admin admin) {
        return false;
    }

    @Override
    public boolean update(Admin admin, int option) {
        return false;
    }

    @Override
    public boolean delete(Admin admin) {
        return false;
    }
}
