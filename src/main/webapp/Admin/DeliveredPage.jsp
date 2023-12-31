<%@page import="com.topiefor.models.User"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.topiefor.models.Order"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    User user = (User) request.getSession().getAttribute("user");

    if (user != null) {
        request.setAttribute("user", user);
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <link href="Admin/Admin.css" type="text/css" rel="stylesheet" />
        <link href="AdminCSS/DeliveredPageCSS.css" type="text/css" rel="stylesheet" />
        <script src="AdminJS/DeliveredPageJS.js"></script>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delivered Page</title>
        <style>
            
        </style>
    </head>
    <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
    <body>
        <!-----------------Scalable Vector Graphics----------------->
        <svg style="display:none;">

    <symbol id="pages" viewBox="0 0 16 16">
        <rect x="4" width="12" height="12" transform="translate(20 12) rotate(-180)" />
        <polygon points="2 14 2 2 0 2 0 14 0 16 2 16 14 16 14 14 2 14" />
    </symbol>/*Home*/

    <symbol id="users" viewBox="0 0 16 16">
        <path d="M8,0a8,8,0,1,0,8,8A8,8,0,0,0,8,0ZM8,15a7,7,0,0,1-5.19-2.32,2.71,2.71,0,0,1,1.7-1,13.11,13.11,0,0,0,1.29-.28,2.32,2.32,0,0,0,.94-.34,1.17,1.17,0,0,0-.27-.7h0A3.61,3.61,0,0,1,5.15,7.49,3.18,3.18,0,0,1,8,4.07a3.18,3.18,0,0,1,2.86,3.42,3.6,3.6,0,0,1-1.32,2.88h0a1.13,1.13,0,0,0-.27.69,2.68,2.68,0,0,0,.93.31,10.81,10.81,0,0,0,1.28.23,2.63,2.63,0,0,1,1.78,1A7,7,0,0,1,8,15Z" />
    </symbol>/*Users*/

    <symbol id="trends" viewBox="0 0 16 16">
        <polygon points="0.64 11.85 -0.02 11.1 2.55 8.85 4.57 10.4 9.25 5.25 12.97 8.84 15.37 6.79 16.02 7.54 12.94 10.2 9.29 6.68 4.69 11.76 2.59 10.14 0.64 11.85" />
    </symbol>/*Categories*/

    <symbol id="collection" viewBox="0 0 16 16">
        <rect width="7" height="7" />
        <rect y="9" width="7" height="7" />
        <rect x="9" width="7" height="7" />
        <rect x="9" y="9" width="7" height="7" />
    </symbol>/*Ingredients*/

    <symbol id="comments" viewBox="0 0 16 16">
        <path d="M0,16.13V2H15V13H5.24ZM1,3V14.37L5,12h9V3Z" />
        <rect x="3" y="5" width="9" height="1" />
        <rect x="3" y="7" width="7" height="1" />
        <rect x="3" y="9" width="5" height="1" />
    </symbol>/*Recipes*/



    <symbol id="settings" viewBox="0 0 16 16">
        <rect x="9.78" y="5.34" width="1" height="7.97" />
        <polygon points="7.79 6.07 10.28 1.75 12.77 6.07 7.79 6.07" />
        <rect x="4.16" y="1.75" width="1" height="7.97" />
        <polygon points="7.15 8.99 4.66 13.31 2.16 8.99 7.15 8.99" />
        <rect x="1.28" width="1" height="4.97" />
        <polygon points="3.28 4.53 1.78 7.13 0.28 4.53 3.28 4.53" />
        <rect x="12.84" y="11.03" width="1" height="4.97" />
        <polygon points="11.85 11.47 13.34 8.88 14.84 11.47 11.85 11.47" />
    </symbol>/*Products*/

    <symbol id="charts" viewBox="0 0 16 16">
        <polygon points="0.64 7.38 -0.02 6.63 2.55 4.38 4.57 5.93 9.25 0.78 12.97 4.37 15.37 2.31 16.02 3.07 12.94 5.72 9.29 2.21 4.69 7.29 2.59 5.67 0.64 7.38" />
        <rect y="9" width="2" height="7" />
        <rect x="12" y="8" width="2" height="8" />
        <rect x="8" y="6" width="2" height="10" />
        <rect x="4" y="11" width="2" height="5" />
    </symbol>/*Address to deliver*/

    <symbol id="appearance" viewBox="0 0 16 16">
        <path d="M3,0V7A2,2,0,0,0,5,9H6v5a2,2,0,0,0,4,0V9h1a2,2,0,0,0,2-2V0Zm9,7a1,1,0,0,1-1,1H9v6a1,1,0,0,1-2,0V8H5A1,1,0,0,1,4,7V6h8ZM4,5V1H6V4H7V1H9V4h1V1h2V5Z" />
    </symbol>/* */



    </svg>
    <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
    <header class="page-header">
        <nav>
            <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
            <ul class="admin-menu">
                <li>
                    <a href="IndexController">
                        <svg>
                        <use xlink:href="#pages"></use>
                        </svg>
                        <span>welcome Page</span>
                    </a>
                </li>
                <!-------------------------------Admin------------------------->
                <li class="menu-heading">
                    <h3>Admin</h3>
                </li>
                <!-------------------------------Home button------------------------->
                <li>
                    <a href="OrderReportController?action=get">
                        <svg>
                        <use xlink:href="#pages"></use>
                        </svg>
                        <span>Home</span>
                    </a>
                </li>
                <!-------------------------------Users button------------------------->
                <li>
                    <a href="UserController?action=get">
                        <svg>
                        <use xlink:href="#users"></use>
                        </svg>
                        <span>Users</span>
                    </a>
                </li>

                <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
                <!-------------------------------Assets------------------------->
                <li class="menu-heading">
                    <h3>Assets</h3>
                </li>
                <!-------------------------------ingredient button------------------------->
                <li>
                    <a href="CategoryController?action=get">

                        <svg>
                        <use xlink:href="#trends"></use>
                        </svg>
                        <span >Categories</span>
                    </a>
                </li>
                <!-------------------------------Ingredients button------------------------->
                <li>
                    <a href="IngredientController?action=get"  onclick="submitForm()">
                        <form id ="myForm" method="POST" action="UnitController">
                            <input type="hidden" name="action" value="get">
                        </form>
                        <svg>
                        <use xlink:href="#collection"></use>
                        </svg>

                        <span>Ingredients</span>
                    </a>

                </li>
                <!-------------------------------Recipe button------------------------->
                <li>
                    <a href="RecipesController?action=get">
                        <form id ="myForm" method="POST" action="IngredientController">
                            <input type="hidden" name="action" value="get">
                        </form>
                        <svg>
                        <use xlink:href="#comments"></use>
                        </svg>
                        <span>Recipes</span>
                    </a>
                </li>
                <li>
                    <a href="ProductController?action=get">
                        <svg>
                        <use xlink:href="#settings"></use>
                        </svg>
                        <span>Products</span>
                    </a>
                </li>
                <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
                <!-------------------------------Reports------------------------->
                <li class="menu-heading">
                    <h3>Reports</h3>
                </li>
                <!-------------------------------Report Pages------------------------->
                <li>
                    <a href="OrderController?action=get">
                        <svg>
                        <use xlink:href="#appearance"></use>
                        </svg>
                        <span>Orders</span>
                    </a>
                </li>
                <li>
                    <a href="OrderController?action=getBaked">
                        <svg>
                        <use xlink:href="#appearance"></use>
                        </svg>
                        <span>Delivery Orders</span>
                    </a>
                </li>
                <li>
                    <a href="OrderController?action=getDelivered">
                        <svg>
                        <use xlink:href="#appearance"></use>
                        </svg>
                        <span>Completed Orders</span>
                    </a>
                </li>
                <li>
                    <a href="IngredientController?action=getStock">
                        <svg>
                        <use xlink:href="#appearance"></use>
                        </svg>
                        <span>Stock Shortage</span>
                    </a>
                </li>
                <li>
                    <a href="UserController?action=getActive">
                        <svg>
                        <use xlink:href="#appearance"></use>
                        </svg>
                        <span>Active Users</span>
                    </a>
                </li>
                <li>
                    <a href="#0">
                    </a>
                </li>
            </ul>
        </nav>
        <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
    </header>
    <section class="page-content">
        <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
        <!--<section class="grid">-->
        <div class="topnav" style="">
            <label style=" text-align: center;"><h2>Completed Orders</h2></label>
            <br>
            <h4 style="display: inline-block; margin-right: 20px;">Start Date:</h4>
            <input type="date" id="start-date" placeholder="Start Date" style="display: inline-block; width: 10%; height: 40px; margin-right: 30px;">
            <h4 style="display: inline-block; margin-right: 20px;">End Date:</h4>
            <input type="date" id="end-date" placeholder="End Date" style="display: inline-block; width: 10%; height: 40px;">
            <button onclick="searchParameterOrders()" class="buttonTable" style="display: inline-block; width: 10%;">Search</button>
        </div>
        <br>

        <div class="container">
            <!-- Grid View -->
            <div class="grid-container">
                <%
                    List<Order> orderList = (List<Order>) request.getAttribute("allOrders");
                    if (orderList != null && !orderList.isEmpty()) {
                        for (Order c : orderList) {
                            int orderID = c.getOrderID();

                            Date date = c.getDate();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                            String formattedDate = dateFormat.format(date);

                            Date Ddate = c.getDateToBeDelivered();
                            SimpleDateFormat DdateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                            String DformattedDate = DdateFormat.format(Ddate);

                            String userInfo1 = c.getAddress().getSuburb();
                            String userInfo2 = c.getAddress().getStreet();

                            String userTele = c.getUser().getTelephoneNumber();

                            String originalStatus = c.getStatus(); // Store the original status value
                            String status = originalStatus.equals("Delivered") ? "Delivered" : "Baked";
                            if (status.equals("Delivered")) {
                %>
                <div class="grid-item">
                    <div class="card" style="">
                        <form name="pdfForm" action="PdfDeliveredController" method="POST">

                            <div class="card-header">
                                <h1 class="order-heading">ORDER</h1>
                                <h1 class="position-number"><%= orderID%></h1>
                                <h1>:</h1>
                            </div>
                            <br>

                            <div class="card-body">

                                <div class="order-details">
                                    <div class="order-detail">
                                        <h4><u>Date Ordered:</u></h4>
                                        <h5 class="card-title"><%= formattedDate%></h5>
                                    </div>
                                    <div class="order-detail date-to-be-delivered">
                                        <h4><u>Date to be Delivered:</u></h4>
                                        <h5 class="card-title"><%= DformattedDate%></h5>
                                    </div>
                                </div>
                                <br>

                                <div class="order-details">
                                    <div class="order-detail">
                                        <h4><u>Telephone Number:</u></h4>
                                        <h5 class="card-title"><%= userTele%></h5>
                                    </div>
                                    <div class="order-detail">
                                        <h4><u>Status:</u></h4>
                                        <h5 class="card-title"><%= originalStatus%></h5>
                                    </div>
                                </div>
                                <br>

                                <h4><u>Street:</u></h4>
                                <h5 class="card-title"><%= userInfo2%></h5><br>
                                <h4><u>Suburb:</u></h4>
                                <h5 class="card-title"><%= userInfo1%></h5>
                                <br><br>

                            </div>
                            <input type="hidden" name="orderInfo" value="<%= orderID + "," + formattedDate + "," + DformattedDate + "," + userTele + "," + originalStatus + "," + userInfo2 + "," + userInfo1%>">
                            <button onclick="generatePdf()" class="buttonTable" style="display: inline-block; width: 100px; height:40px;">Generate PDF</button>
                        </form>
                    </div>
                </div>
                <%

                                status = "Delivered";
                            } else {
                                status = "Baked";
                            }

                        }
                    } else {
                        out.print("<h4>no data found</h4>");
                    }
                %>
            </div>
        </div>


        <!--</section>-->
        <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
        <footer class="page-footer">
        </footer>
        <%-- -------------------------------------------------------------------------------------------------------------------------------------------- --%>
    </section>
</body>
</html>
<script>

</script>