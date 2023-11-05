import com.topiefor.dao.OrderDao;
import com.topiefor.dao.impl.OrderDaoImpl;
import com.topiefor.models.Address;
import com.topiefor.models.Order;
import com.topiefor.models.Payment;
import com.topiefor.models.Product;
import com.topiefor.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderDaoTest {

    private Connection con = null;

    public OrderDaoTest() {
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
//    public void addOrderTest() {
//
//        OrderDao order = OrderDaoImpl.getInstance(con);
//        List<Product> lp = new ArrayList();
//        lp.add(new Product(11, 1));
//        assertTrue(order.addOrder(new Order(new Address(30), new User(11), 40.00, lp, new Payment(0, "Approved", true))));
//
//    }

//    @Test
//    public void cancelOrderTest() {
//
//        OrderDao order = OrderDaoImpl.getInstance(con);
//        assertTrue(order.cancelOrder(new Order(2, "Cancelled")));
//
//    }
//
//    @Test
//    public void getOrderDetailsByOrderIDTest() {
//        OrderDao orderDao = OrderDaoImpl.getInstance(con);
//        List<Order> detailsOfOrder = orderDao.getOrderDetailsByOrderID(5);
//        for (Order order : detailsOfOrder) {
//            System.out.println("Product Name: " + order.getProd().getName() + "\nPrice: " + order.getProd().getPrice() + "\nNumber ordered: " + order.getProd().getQuantity() + "\nImg: " + order.getProd().getImage() + "\nRecipe: " + order.getProd().getRecipe().getRecipeName());
//
//        }
//
//    }
//@Test
//    public void changeOrderStatusTest() {
//
//        OrderDao order = OrderDaoImpl.getInstance(con);
//        assertTrue(order.cancelOrder(new Order(2, "baked")));
//
//    }
//    @Test
//    public void getAllOrdersTest() {
//
//        OrderDao order = OrderDaoImpl.getInstance(con);
//        assertTrue(order.getAllOrders().size()>0);
//
//    }
}