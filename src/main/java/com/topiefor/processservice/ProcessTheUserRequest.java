package com.topiefor.processservice;

import com.topiefor.controller.ProcessRequest;
import com.topiefor.dao.UserDao;
import com.topiefor.models.Address;
import com.topiefor.models.Role;
import com.topiefor.models.User;
import com.topiefor.service.impl.UserServiceImpl;
import com.topiefor.utils.EmailSender;
import com.topiefor.utils.PasswordEncryptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessTheUserRequest implements ProcessRequest {

    private String page;
    private UserServiceImpl userServiceImpl;

    public ProcessTheUserRequest(UserDao userDao) {
        userServiceImpl = new UserServiceImpl(userDao);
    }

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse response) {

        List<User> allUsers = null;
        String userAction = request.getParameter("action");
        int userID = 0;
        String userName;
        String status;
        String SurName;
        String title;
        String telephone;
        String email;
        String street;
        String suburb;
        String code;
        String role;
        int vCode = 0;
        String password;
        PasswordEncryptor passwordEncryptor = new PasswordEncryptor();
        HttpSession session = request.getSession();

        boolean flagStatus = false;

        if (userAction != null) {

            switch (userAction.trim()) {
                case "add":

                    userName = request.getParameter("userName");
                    // Status = request.getParameter("Status");
                    SurName = request.getParameter("surName");
                    title = request.getParameter("title");
                    telephone = request.getParameter("telephone");
                    email = request.getParameter("email");
                    street = request.getParameter("street");
                    suburb = request.getParameter("suburb");
                    code = request.getParameter("code");
                    role = request.getParameter("role");
                    password = request.getParameter("password");

                    if (userName != null && !userName.isEmpty()
                            && role != null && !role.isEmpty()
                            && SurName != null && !SurName.isEmpty()
                            && title != null && !title.isEmpty()
                            && telephone != null && !telephone.isEmpty()
                            && email != null && !email.isEmpty()
                            && street != null && !street.isEmpty()
                            && suburb != null && !suburb.isEmpty()
                            && code != null && !code.isEmpty()) {

                        userName = userName.trim();
                        SurName = SurName.trim();
                        role = role.trim();
                        title = title.trim();
                        telephone = telephone.trim();
                        email = email.trim();
                        street = street.trim();
                        suburb = suburb.trim();
                        code = code.trim();

                        if (userServiceImpl.addUser(new User(0, userName, SurName, title, telephone, email, new Address(0, street, suburb, code), role.equals("PUBLIC") ? Role.PUBLIC : Role.ADMIN, true, passwordEncryptor.encryptPassword(password)))) {
                            request.setAttribute("allUsers", new ArrayList<>(userServiceImpl.getAllUsers()));
                            request.setAttribute("message", "user added!");

                        } else {
                            request.setAttribute("message", "user not Added");

                        }

                        page = "Admin/AddUserPage.jsp";

                    }
                    break;

                //---------------------------------------------------------------------------------------------------------
                case "addU":

                    userName = request.getParameter("userName");
                    // Status = request.getParameter("Status");
                    telephone = request.getParameter("telephone");
                    email = request.getParameter("email");
                    street = request.getParameter("street");
                    suburb = request.getParameter("suburb");
                    code = request.getParameter("code");
                    role = request.getParameter("role");
                    password = request.getParameter("password");

                    if (userName != null && !userName.isEmpty()
                            && role != null && !role.isEmpty()
                            && telephone != null && !telephone.isEmpty()
                            && email != null && !email.isEmpty()
                            && street != null && !street.isEmpty()
                            && suburb != null && !suburb.isEmpty()
                            && code != null && !code.isEmpty()) {
                        userName = userName.trim();
                        telephone = telephone.trim();
                        email = email.trim();
                        street = street.trim();
                        suburb = suburb.trim();
                        code = code.trim();
                        User user = new User(0, userName, telephone, email, new Address(0, street, suburb, code), Role.PUBLIC, true, passwordEncryptor.encryptPassword(password));
                        if (userServiceImpl.checkUser(email) != null) {
                            request.setAttribute("message", "user alredy Registered");
                            page = "User/LoginPage.jsp";
                            break;
                        } else {
                            session.setAttribute("user", user);
                            vCode = userServiceImpl.generateCode();
                            session.setAttribute("vCode", vCode);
                            EmailSender emailVcode = new EmailSender();
                            emailVcode.VerificationEmail(email, userName, vCode);
                            request.setAttribute("code", vCode);
                            page = "User/CodeVerificationPage.jsp";
                        }
                    }

                    break;
                case "verifyCode":
                    User user = (User) session.getAttribute("user");
                    vCode = (Integer) request.getSession().getAttribute("vCode");
                    int codes = Integer.parseInt(request.getParameter("vCode"));
                    if (user != null && codes > 0) {
                        if (codes == vCode) {
                            if (userServiceImpl.addUser(user)) {
                                request.setAttribute("allUsers", new ArrayList<>(userServiceImpl.getAllUsers()));
                                request.setAttribute("message", "<p style=\"color:green\">successfully Registered!");
                                EmailSender welcomeEmail = new EmailSender();
                                welcomeEmail.welcomeEmail(user.getEmail(), user.getUserName(), user.getTelephoneNumber(), user.getAddress());
                                request.getSession().removeAttribute("user");
                                request.getSession().removeAttribute("vCode");
                                page = "User/LoginPage.jsp";

                            } else {
                                request.setAttribute("message", "user not Added");
                                page = "User/LoginPage.jsp";

                            }
                        } else {
                            request.setAttribute("message", "Invalid Code");
                            page = "User/CodeVerificationPage.jsp";
                        }
                    }
                    break;

                case "edit":
                    page = "Admin/UserPage.jsp";

                    break;
                //---------------------------------------------------------------------------------------------------------
                case "get":
                    allUsers = userServiceImpl.getAllUsers();
                    if (allUsers != null && !allUsers.isEmpty()) {

                        request.setAttribute("allUsers", allUsers);
                    } else {
                        request.setAttribute("response", "no data");
                    }
                    page = "Admin/UserPage.jsp";
                    break;

                case "getActive":
                    allUsers = userServiceImpl.getAllUsers();
                    if (allUsers != null && !allUsers.isEmpty()) {

                        request.setAttribute("allUsers", allUsers);
                    } else {
                        request.setAttribute("response", "no data");
                    }
                    page = "Admin/ActiveUsersPage.jsp";
                    break;

                //------------------------------------------------------------------------------------
                case "delete":

                    page = "Admin/UsersPage.jsp";
                    break;
                case "addForm":

                    page = "Admin/AddUserPage.jsp";
                    break;
                case "editForm":

                    page = "User/UserProfile.jsp";
                    break;
                default:
                    request.setAttribute("response", "something went wrong");
                    break;
            }

        }
    }

    @Override
    public void processTheResponse(HttpServletRequest request, HttpServletResponse response) {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
        try {
            requestDispatcher.forward(request, response);

        } catch (ServletException | IOException ex) {
            System.out.println("Error Dispatching View: " + ex.getMessage());
        }
    }

}
