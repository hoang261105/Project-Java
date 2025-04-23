package ra.edu.business.dao.account;

import ra.edu.business.model.Account;

public interface AccountDao {
    Account login(String email, String password);
}
