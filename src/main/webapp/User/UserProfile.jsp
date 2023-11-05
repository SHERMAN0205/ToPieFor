<%@page import="com.topiefor.models.Order"%>
<%@page import="com.topiefor.models.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="com.topiefor.models.User"%>
<%@page import="java.text.DecimalFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DecimalFormat dcf = new DecimalFormat("#.##");

    User user = (User) request.getSession().getAttribute("user");

    if (user != null) {
        request.setAttribute("user", user);
    } else {
        response.sendRedirect("User/login.jsp");
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="UserCSS/UserProfileCSS.css" type="text/css" rel="stylesheet" />
        <script src="UserJS/UserProfileJS.js"></script>

        <%@include file="head.jsp"%>
        <title>User Profile Page</title>
        <style>

        </style>
    </head>
    <body>
        <div class="nav" >
            <!--top left welcome-->
            <h3 class="logo" style="margin-left: 100px; margin-top: 2%; bold">Profile</h3>
            <!--header list top right-->
            <ul>
                <li><a  href="IndexController" ><button class="btn">Home</button></a></li>
                <li><a href="#" class="btn">Logout</a></li>
            </ul>
        </div>
        <div class="container rounded bg-white mt-5 mb-5">
            <div class="row">
                <div class="col-md-3 border-right">
                    <div class="d-flex flex-column align-items-center text-center p-3 py-5"><img class="rounded-circle mt-5" width="150" src="https://th.bing.com/th/id/R.89f59066dd3183cb465068d3e4269649?rik=z3SMKTw9FKtyPw&riu=http%3a%2f%2feshitadesai.com%2fwp-content%2fuploads%2f2017%2f06%2fProfile-ICon.png&ehk=xSGkDRzX5FK0lvKswGuTSoHwTzArHO3vw3iNXHSom%2b8%3d&risl=&pid=ImgRaw&r=0"><span class="font-weight-bold"><%=user.getUserName()%></span><span class="text-black-50"><%=user.getEmail()%></span><span> </span></div>
                </div>
                <div class="col-md-5 border-right">
                    <div class="p-3 py-5">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4 class="text-right">Profile Settings</h4>
                        </div>
                        <div class="row mt-2">
                           
                            <div class="col-md-6"><label class="labels">Name</label><input type="text" class="form-control" placeholder="first name" value="<%=user.getUserName()%>"></div>
                                <%
                                    String surname = user.getSurName();
                                    if (user.getSurName() == null) {
                                        surname = "";
                                                                       }
                                %>
                            <div class="col-md-6"><label class="labels">Surname</label><input type="text" class="form-control"  value="<%=surname%>"></div>
                            <div class="col-md-6"><label class="labels">Title</label>
                                <select  class="form-control">
                                    <option>MR</option>
                                    <option>Miss</option>
                                </select>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="col-md-12"><label class="labels">Mobile Number</label><input type="text" class="form-control" placeholder="enter phone number" value="<%=user.getTelephoneNumber()%>"></div>
                            <div class="col-md-12"><label class="labels">Street</label><input type="text" class="form-control" placeholder="enter address line 1" value="<%=user.getAddress().getStreet()%>"></div>
                            <div class="col-md-12"><label class="labels">Suburb</label><input type="text" class="form-control" placeholder="enter address line 2" value="<%=user.getAddress().getSuburb()%>"></div>
                            <div class="col-md-12"><label class="labels">code</label><input type="text" class="form-control" placeholder="enter address line 3" value="<%=user.getAddress().getCode()%>"></div>
                        </div>
                        <form action="UserController">
                            <input type="hidden" name="action" value="editUser">
                            <div class="mt-5 text-center"><button class="btn btn-primary profile-button" type="submit">Save Profile</button></div>
                        </form>
                    </div>
                </div> 

            </div>
        </div>

    </body>
    <script>
//            sort js for products with category list
        function sortProducts(category) {
            const categoryCards = document.getElementsByClassName('category-card');
            for (let i = 0; i < categoryCards.length; i++) {
                const card = categoryCards[i];
                const cardCategory = card.getAttribute('data-category');
                if (cardCategory === category) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            }
        }

        // Get the sign up modal
        var signupModal = document.getElementById('signupModal');
        // When the user clicks anywhere outside of the sign up modal, close it
        window.onclick = function (event) {
            if (event.target == signupModal) {
                signupModal.style.display = "none";
            }
        };

        // Get the login modal
        var loginModal = document.getElementById('loginModal');
        // When the user clicks anywhere outside of the login modal, close it
        window.onclick = function (event) {
            if (event.target == loginModal) {
                loginModal.style.display = "none";
            }
        };

        // Get the cart modal
        var cartModal = document.getElementById('cartModal');
        // When the user clicks anywhere outside of the login modal, close it
        window.onclick = function (event) {
            if (event.target == cartModal) {
                cartModal.style.display = "none";
            }
        };

//            update price on quantity click
        function updatePrice(input, productId, initialPrice) {
            var quantity = parseInt(input.value);
            var newPrice = quantity * initialPrice;
            document.getElementById("price_" + productId).textContent = newPrice.toFixed(2);

            // Recalculate the total price
            var total = 0;
            var prices = document.querySelectorAll("[id^=price_]");
            prices.forEach(function (price) {
                total += parseFloat(price.textContent);
            });
            document.getElementById("totalPrice").textContent = total.toFixed(2);
            document.getElementById("payNowPrice").textContent = total.toFixed(2);
        }


        var coll = document.getElementsByClassName("collapsible");
        var i;

        for (i = 0; i < coll.length; i++) {
            coll[i].addEventListener("click", function () {
                this.classList.toggle("active");
                var content = this.nextElementSibling;
                if (content.style.display === "block") {
                    content.style.display = "none";
                } else {
                    content.style.display = "block";
                }
            });
        }
        var open = <%=request.getAttribute("openCart")%>;
        console.log(open);
        if (open !== null && open !== '') {
            document.getElementById('cartModal').style.display = 'block';
        }
    </script>
</html>