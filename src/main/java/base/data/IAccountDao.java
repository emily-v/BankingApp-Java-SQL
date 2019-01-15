package base.data;

import base.pojos.Account;

import java.util.List;

public interface IAccountDao {
    List<Account> getAllAccounts (int userId);
    Account getById(int id);
//    Account getByType(String accountType);
    double updateBalance(Account a);
}
