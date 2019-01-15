package base.data;

import base.pojos.User;
import base.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements IUserDao {

    private ConnectionUtil connectionUtil;

    public UserDao() {

    }

    public UserDao(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }


    @Override
    public User getUserByUsername(String username) {
        Connection c = null;
        User u = null;

        try {
            c = this.connectionUtil.newConnection();
            c.setAutoCommit(false);

            String sql = "Select userid, username, pw from bank.users where username = ?";
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                u = new User();
                u.setUserId(rs.getInt("userid"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("pw"));
            }

            c.commit();
            c.setAutoCommit(true);
            return u;
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

        return u;
    }

//    public User getUserById (int id) {
//        Connection c = null;
//        User u = null;
//
//        try {
//            c = this.connectionUtil.newConnection();
//            c.setAutoCommit(false);
//            String sql = "Select * from bank.users where userid = ?";
//            PreparedStatement ps = c.prepareStatement(sql);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                u = new User();
//                u.setUserId(rs.getInt("userid"));
//                u.setUsername(rs.getString("username"));
//                u.setPassword(rs.getString("pw"));
//            }
//
//            ps.close();
//            c.commit();
//            c.setAutoCommit(true);
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(u.getUsername());
//        return u;
//
//    }
}
