package service;

import model.Account;

import java.sql.SQLException;


public interface AccountService {
    void createAccount(String username, String password);
    Account validateAccount(String username, String password);
    String getAccountInfo(Account account);
}
