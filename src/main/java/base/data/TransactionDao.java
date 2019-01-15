package base.data;

import base.pojos.Transaction;
import base.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements ITransactionDao {

    private ConnectionUtil connectionUtil;

    public TransactionDao() {}

    public TransactionDao(ConnectionUtil connectionUtil) { this.connectionUtil = connectionUtil; }

    @Override
    public List<Transaction> getAllTransactions(int accountId) {
        Connection c = null;
        List<Transaction> transactions = null;

        try {
            c = this.connectionUtil.newConnection();
            c.setAutoCommit(false);
            transactions = new ArrayList<>();
            String sql = "Select accountid, amount from bank.transactions where accountid = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction temp = new Transaction();
//                temp.setTransId(rs.getInt("transid"));
                temp.setAccountId(rs.getInt("accountid"));
                temp.setAmount(rs.getDouble("amount"));
                transactions.add(temp);
            }

            ps.close();
            c.commit();
            c.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return transactions;
    }

    @Override
    public void newTransaction(Transaction t) {

        Connection c = null;

        try {
            c = connectionUtil.newConnection();
            c.setAutoCommit(false);
            String sql = "Insert into bank.transactions (accountid, amount) values (?, ?)";

            PreparedStatement ps = c.prepareStatement(sql);

            ps.setInt(1, t.getAccountId());
            ps.setDouble(2, t.getAmount());

            ps.executeUpdate(); // executeUpdate returns the number of rows affected

            c.commit();
            c.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if(c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
