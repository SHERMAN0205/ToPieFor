
import com.topiefor.dao.UserDao;
import com.topiefor.dao.impl.UserDaoImpl;
import com.topiefor.models.Address;
import com.topiefor.models.Role;
import com.topiefor.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    private Connection con = null;

    public UserDaoTest() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                   "jdbc:mysql://localhost:3306/to_pie_for_db_test", "root", "root"
            );
        } catch (ClassNotFoundException | SQLException cle) {
        }
        if (con != null) {
            System.out.println("Got connection");
        }
    }

//    @Test
//    public void getAllUserTest() {
//
//        UserDao user = UserDaoImpl.getInstance(con);
//        assertTrue(user.getAllUsers().size() > 0);
//    }
//
////    @Test
////    public void addUserTest() {
////
////        UserDao user = UserDaoImpl.getInstance(con);
////        User u = new User(0, "Nyiko", "Herbert", "Mrs", "0887726524", "boy@gmail", new Address(0, "22 Tembisa Road", "Ka-Mhinga", "1111"), Role.PUBLIC, true);
////        assertTrue(user.addUser(u));
////
////    }
////
////    @Test
////    public void editUserTest() {
////
////       
////        UserDao user = UserDaoImpl.getInstance(con);
////        User u = new User(1, "fumiPrecios", "Ngove", "Mrs", "0847562922", "OkayMai@gmail", new Address(7, "92 venda hayani", "Mvenda", "4867"), Role.PUBLIC, true);
////        assertTrue(user.editUser(u));
////
////    }
//
//    @Test
//    public void userAvailabilityTest() {
//        
//        UserDao user = UserDaoImpl.getInstance(con);
//        User u = new User(1);
//        assertTrue(user.userAvailability(u));
//    }
}