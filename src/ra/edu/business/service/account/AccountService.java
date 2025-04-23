package ra.edu.business.service.account;

import ra.edu.business.model.Account;
import ra.edu.business.service.AppService;

public interface AccountService extends AppService<Account> {
    Account login(String email, String password);
}
