package ra.edu.business.service.admin;

import ra.edu.business.dao.admin.AccountDao;
import ra.edu.business.dao.admin.AccountDaoImp;
import ra.edu.business.model.Account;

import java.util.List;

public class AccountServiceImp implements AccountService {
    private AccountDao accountDao;

    public AccountServiceImp() {
        accountDao = new AccountDaoImp();
    }

    @Override
    public Account login(String email, String password) {
        return accountDao.login(email, password);
    }

    @Override
    public List<Account> findAll() {
        return List.of();
    }

    @Override
    public boolean add(Account account) {
        return false;
    }

    @Override
    public boolean update(Account account, int option) {
        return false;
    }

    @Override
    public boolean delete(Account account) {
        return false;
    }
}
