<%@page import="com.topiefor.models.Order"%>
<%@page import="com.topiefor.models.Product"%>
<%@page import="com.topiefor.models.User"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");

    User user = (User) request.getSession().getAttribute("user");

    if (user != null) {
        request.setAttribute("user", user);
    } else {
        response.sendRedirect("login.jsp");
    }
    List<Product> cart_list = (ArrayList<Product>) session.getAttribute("cart-list");
    if (cart_list != null) {
        request.setAttribute("cart_list", cart_list);
    }
    List<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp"%>
        <link href="UserJS/OrdersCSS.css" type="text/css" rel="stylesheet" />
        <script src="UserJS/OrdersJS.js"></script>
        
        <title>To pie for</title>
 
    <style>
       
    </style>
       </head>
    <body>
        <div class="nav" >
            <!--top left welcome-->
            <h3 class="logo" style="margin-left: 100px; margin-top: 2%; bold">Orders</h3>
            <!--header list top right-->
            <ul>
                <li><a  href="IndexController" ><button class="btn">Home</button></a></li>
                <li><a class="btn" href="User/UserProfile" >Profile</a></li>

                <%if (user == null) {
                        request.setAttribute("user", user);
                %>
                <li><a  href="User/LoginPage.jsp#signup" ><buttonnn  onclick="document.getElementById('signupModal').style.display = 'block'" ">Sign Up</buttonnn></a></li>
                <li><a  href="User/LoginPage.jsp#login" ><buttonnn onclick="document.getElementById('loginModal').style.display = 'block'" ">Login</buttonnn></a></li>
                            <%} else {%>
                <li><a href="OrderController?action=getOrder&userID=<%= user.getUserID()%>" class="btn">Orders</a></li>
                <li><a href="#" class="btn">Logout</a></li>
                    <%}%>
            </ul>
        </div>

        <div class="container">
            <div class="card-header my-3">All Orders</div>
            <table class="table table-light">
                <thead>
                    <tr>
                        <th scope="col">OrderID</th>
                        <th scope="col">Date</th>
                        <th scope="col">Date To Be Delivered</th>
                        <th scope="col">Delivery Address</th>
                        <th scope="col">Amount</th>
                    </tr>
                </thead>
                <tbody>

                    <%
                        if (orders != null) {
                            for (Order o : orders) {%>
                    <tr>
                        <td><%=o.getOrderID()%></td>
                        <td><%=o.getDate()%></td>
                        <td><%=o.getDateToBeDelivered()%></td>
                        <td><%=o.getAddress().getStreet() + ", " + o.getAddress().getSuburb() + ", " + o.getAddress().getCode()%></td>
                        <td>R <%=dcf.format(o.getTotalPrice())%></td>
                        <td><a class="btn btn-sm btn-success" href="OrderController?action=viewOrder&orderID=<%=o.getOrderID()%>">View Order</a></td>
                    </tr>
                    <%}
                        }
                    %>

                </tbody>
            </table>
        </div>

    </body>
</html>