package com.topiefor.dao.impl;

import com.topiefor.dao.AddressDao;
import com.topiefor.dao.UserDao;
import com.topiefor.models.Address;
import com.topiefor.models.Role;
import com.topiefor.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl userDaoImpl;
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private UserDaoImpl(Connection con) {
        this.con = con;
        fetchAllUsersFromDB();
    }

    //--------------------------------------------------------
    public static UserDaoImpl getInstance(Connection dbCon) {
        if (userDaoImpl == null) {
            userDaoImpl = new UserDaoImpl(dbCon);
        }
        return userDaoImpl;
    }

    private List<User> fetchAllUsersFromDB() {
        List<User> userList = new ArrayList<>();
        if (con != null) {
            try {
                ps = con.prepareStatement("SELECT user.UserID, user.UserName, user.Surname, user.Title,user.TelephoneNumber, user.Email, user.RoleID, role.Role, user.LastDateUpdated, user.Flag, user.Password, address.AddressID, address.street , address.suburb,address.code\n"
                        + "FROM user\n"
                        + "INNER JOIN  user_address ON user.UserID = user_address.UserID\n"
                        + "INNER JOIN address ON user_address.AddressID = address.AddressID\n"
                        + "INNER JOIN role ON user.RoleID = role.RoleID");
                rs = ps.executeQuery();
                while (rs.next()) {

                    userList.add(new User(rs.getInt("UserID"),
                            rs.getString("UserName"),
                            rs.getString("Surname"),
                            rs.getString("Title"),
                            rs.getString("TelephoneNumber"),
                            rs.getString("Email"), new Address(rs.getInt("AddressID"), rs.getString("Street"),
                            rs.getString("suburb"), rs.getString("code")), Role.valueOf(rs.getString("Role")),
                            rs.getBoolean("Flag"),
                            rs.getString("Password")));
                }
            } catch (SQLException ex) {
                System.out.println("Error!!: " + ex.getMessage());
                System.out.println("hello");
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return userList;
    }

    @Override
    public boolean addUser(User user) {

        boolean retVal = false;
        int userId = 0;

        if (con != null) {
            try {
                con.setAutoCommit(false);
                ps = con.prepareStatement("INSERT INTO user(UserName, Surname, Title, TelephoneNumber, Email,RoleID, LastDateUpdated, Password) values(?,?,?,?,?,?,?,?)");
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getSurName());
                ps.setString(3, user.getTitle());
                ps.setString(4, user.getTelephoneNumber());
                ps.setString(5, user.getEmail());
                ps.setInt(6, user.getRole().name().equals("PUBLIC") ? 1 : 2);
                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                ps.setTimestamp(7, currentTimestamp);
                ps.setString(8, user.getPassword());
                if (ps.executeUpdate() > 0) {
                    ps = con.prepareStatement("SELECT DISTINCT(LAST_INSERT_ID()) AS UserID FROM user");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        userId = rs.getInt("UserID");
                    }

                    AddressDao addressDaoImpl = AddressDaoImpl.getInstance(con);
                    //  AddressServiceImpl addressServiceImpl = new AddressServiceImpl(addressDaoImpl);
                    boolean isAdded = addressDaoImpl.addAddress(new Address(0, user.getAddress().getStreet(), user.getAddress().getSuburb(), user.getAddress().getCode()));

                    if (!isAdded) {
                        con.rollback();

                    } else {
                        con.commit();
                        con.setAutoCommit(true);
                        if (LinkConnection(userId)) {
                            retVal = true;
                        }

                    }

                }

            } catch (SQLException ex) {
                try {
                    con.rollback();
                } catch (SQLException rollBackError) {
                    System.out.println("Error: " + rollBackError.getMessage());
                }
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    public boolean LinkConnection(int userId) {

        boolean retVal = false;
        try {

            ps = con.prepareStatement("SELECT DISTINCT(LAST_INSERT_ID()) AS AddressID FROM address");

            rs = ps.executeQuery();

            while (rs.next()) {

                ps = con.prepareStatement("INSERT INTO user_address(AddressID, UserID) VALUES (?,?)");
                ps.setInt(1, rs.getInt("AddressID"));
                ps.setInt(2, userId);

                if (ps.executeUpdate() > 0) {
                    System.out.println("");
                    retVal = true;
                }
            }

        } catch (SQLException ex) {

            System.out.println("Error: " + ex.getMessage());
        }

        return retVal;
    }

    @Override
    public boolean editUser(User user) {

        boolean retVal = false;
        if (con != null) {
            try {
                ps = con.prepareStatement("UPDATE user SET UserName =?, Surname =?, Title =?, TelephoneNumber=?, LastDateUpdated=? WHERE UserID =? ");
                ps.setString(1, user.getUserName());
                ps.setString(2, user.getSurName());
                ps.setString(3, user.getTitle());
                ps.setString(4, user.getTelephoneNumber());
                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                ps.setTimestamp(5, currentTimestamp);
                ps.setInt(6, user.getUserID());

                if (ps.executeUpdate() > 0) {
                    retVal = true;
                }

            } catch (SQLException ex) {
                try {
                    con.rollback();
                } catch (SQLException rollBackError) {
                    System.out.println("Error: " + rollBackError.getMessage());
                }
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    @Override
    public List<User> getAllUsers() {
        return fetchAllUsersFromDB();
    }

    @Override
    public boolean userAvailability(User user) {

        boolean retVal = false;

        if (con != null) {
            try {

                con.setAutoCommit(false);
                ps = con.prepareStatement("UPDATE user SET Flag =?, LastDateUpdated=? WHERE UserID =? ");

                ps.setBoolean(1, user.isStatus());
                Timestamp currentTimestamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
                ps.setTimestamp(2, currentTimestamp);
                ps.setInt(3, user.getUserID());
                if (ps.executeUpdate() > 0) {
                    try {
                        ps = con.prepareStatement("SELECT UserID FROM user_address WHERE UserID =?");
                        ps.setInt(1, user.getUserID());

                        rs = ps.executeQuery();

                        while (rs.next()) {

                            ps = con.prepareStatement("UPDATE user_address SET Flag = ? WHERE  UserID =?");
                            ps.setBoolean(1, user.isStatus());
                            ps.setInt(2, rs.getInt("UserID"));

                            if (ps.executeUpdate() > 0) {
                                System.out.println("");
                                con.commit();

                                retVal = true;
                            }

                        }

                        con.setAutoCommit(true);

                    } catch (SQLException ex) {

                        System.out.println("Error: " + ex.getMessage());
                    }

                }

            } catch (SQLException ex) {
                try {
                    con.rollback();
                } catch (SQLException rollBackError) {
                    System.out.println("Error: " + rollBackError.getMessage());
                }
                System.out.println("Error!!: " + ex.getMessage());
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                        System.out.println("Could not close: " + ex.getMessage());
                    }
                }
            }
        }
        return retVal;
    }

    public static void main(String[] args) {

        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/to_pie_for_db", "root", "root"
            );
        } catch (ClassNotFoundException | SQLException cle) {
        }
        if (con != null) {
            System.out.println("Got connection");
        }
        // System.out.println("Add user: " + new UserDaoImpl(con).addUser(new User(0, "girl", "ygvhb", "Mrs", "0727634999", "123@gmail", new Address(0, "312 trws road", "Ka-Mhinga", "0980"), Role.PUBLIC, true, "hgdsfbv")));
        // System.out.println("Results edit: " + new UserDaoImpl(con).editUser(new User(44, "fumi", "Ngove", "Mrs", "0847562922", "OkayMai@gmail", new Address(38, "92 venda hayani", "Mvenda", "4867"), new Role(2), true)));
        System.out.println("Results All users: " + new UserDaoImpl(con).getAllUsers());
        // System.out.println("Results deleting: " + new UserDaoImpl(con).userAvailability(new User(44, new Address(45), false)));
    }

}