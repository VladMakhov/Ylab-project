package dao.classes;

import config.DatabaseConnectionConfig;
import model.Account;
import model.Transaction;
import dao.FinanceDao;

import java.sql.*;
import java.util.List;

public class FinanceDaoImpl implements FinanceDao {

    DatabaseConnectionConfig data = new DatabaseConnectionConfig();
    private final String URL;
    private final String USERNAME;
    private final String PASSWORD;

    public FinanceDaoImpl() {
        List<String> databaseConnection = data.loadDatabaseProperties();
        this.URL = databaseConnection.get(0);
        this.USERNAME = databaseConnection.get(1);
        this.PASSWORD = databaseConnection.get(2);
    }

    @Override
    public void deposit(Account account, long amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update private.accounts set balance = ? where id = ?;
                    """);
            preparedStatement.setLong(1, account.getBalance() + amount);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void withdraw(Account account, long amount) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    update private.accounts set balance = ? where id = ?;
                    """);
            preparedStatement.setLong(1, account.getBalance() - amount);
            preparedStatement.setInt(2, account.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Transaction transaction) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    insert into private.transactions (account_id, amount, type)
                    values (?, ?, ?);
                    """);
            preparedStatement.setInt(1, transaction.accountId());
            preparedStatement.setInt(2, transaction.amount());
            preparedStatement.setInt(3, transaction.type().getTypeId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
