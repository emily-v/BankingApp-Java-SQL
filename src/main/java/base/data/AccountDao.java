package base.data;

import base.pojos.Account;
import base.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements IAccountDao {

    private ConnectionUtil connectionUtil;

    public AccountDao() {}

    public AccountDao(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    @Override
    public List<Account> getAllAccounts(int holderId){//(int holderId){
        Connection c = null;
        List<Account> accounts = null;

        try {
            c = this.connectionUtil.newConnection();
            c.setAutoCommit(false);
            accounts = new ArrayList<>();
            String sql = "Select accountid, holderid, accounttype, balance from bank.accounts where holderid = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, holderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Account temp = new Account();
                temp.setAccountId(rs.getInt("accountid"));
                temp.setAccountHolderId(rs.getInt("holderid"));
                temp.setAccountType(rs.getString("accounttype"));
                temp.setBalance(rs.getDouble("balance"));
                accounts.add(temp);
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
        return accounts;
    }

    @Override
    public Account getById(int id) {
        Connection c = null;
        Account a = null;

        try {
            c = this.connectionUtil.newConnection();
            c.setAutoCommit(false);
            String sql = "Select accountid, holderid, accounttype, balance from bank.accounts where accountid = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                a = new Account();
                a.setAccountId(rs.getInt("AccountId"));
                a.setAccountHolderId(rs.getInt("HolderId"));
                a.setAccountType(rs.getString("AccountType"));
                a.setBalance(rs.getDouble("Balance"));
            }
            ps.close();
            c.commit();
            c.setAutoCommit(true);

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                c.rollback(); // if it catches exception, roll back to previous state
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return a;
    }

//    @Override
//    public Account getByType(String accountType) {
//        Connection c = null;
//        Account a = null;
//
//        try {
//            c = this.connectionUtil.newConnection();
//            c.setAutoCommit(false);
//            String sql = "Select accounttype, balance from bank.accounts where accounttype = ?";
//            PreparedStatement ps = c.prepareStatement(sql);
//            ps.setString(1, accountType);
//
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()) {
//                a = new Account();
//                a.setAccountType(rs.getString("AccountType"));
//                a.setBalance(rs.getDouble("Balance"));
//            }
//            ps.close();
//            c.commit();
//            c.setAutoCommit(true);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            try {
//                c.rollback(); // if it catches exception, roll back to previous state
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        } finally {
//            if (c != null) {
//                try {
//                    c.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//        return a;
//    }

    @Override
    public double updateBalance(Account a) {
    Connection c = null;

    try {
        c = connectionUtil.newConnection();
        c.setAutoCommit(false);

        String sql = "Update bank.accounts set balance = ? where accountid = ?";
        PreparedStatement ps = c.prepareStatement(sql);

        ps.setDouble(1, a.getBalance());
        ps.setInt(2, a.getAccountId());

        int rows = ps.executeUpdate();

        c.commit();
        c.setAutoCommit(true);

        if (rows > 0) {
            return a.getBalance();
        } else {
            return 0;
        }

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    return 0;
    }


}


