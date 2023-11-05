package com.topiefor.processservice;

import com.topiefor.controller.ProcessRequest;
import com.topiefor.dao.IngredientDao;
import com.topiefor.dao.impl.UnitDaoImpl;
import com.topiefor.database.manager.DatabaseManager;
import com.topiefor.models.Ingredient;
import com.topiefor.models.Unit;
import com.topiefor.service.UnitService;
import com.topiefor.service.impl.IngredientServiceImpl;
import com.topiefor.service.impl.UnitServiceImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProcessTheIngredientRequest implements ProcessRequest {

    private String page;
    private IngredientServiceImpl ingredientServiceImpl;

    public ProcessTheIngredientRequest(IngredientDao IngredientDao) {
        ingredientServiceImpl = new IngredientServiceImpl(IngredientDao);
    }

    @Override
    public void processTheRequest(HttpServletRequest request, HttpServletResponse response) {
        allUnits(request);
        List<Ingredient> allIngredients = null;
        String ingredientAction = request.getParameter("action");

        int ingredientID = 0;
        String ingredientName;
        String ingredientStatus;
        int ingredientQuantity = 0;
        int ingredientUnitID = 0;

        boolean flagStatus = false;
        if (ingredientAction != null) {
            switch (ingredientAction.trim()) {
                case "add":

                    ingredientName = request.getParameter("ingredientName");
                    if (ingredientName != null && !ingredientName.isEmpty()) {
                        ingredientName = ingredientName.trim();
                        if (request.getParameter("ingredientQuantity") != null && !request.getParameter("ingredientQuantity").isEmpty()
                                && request.getParameter("ingredientUnitID") != null && !request.getParameter("ingredientUnitID").isEmpty()) {
                            try {

                                ingredientUnitID = Integer.parseInt(request.getParameter("ingredientUnitID").trim());
                                ingredientQuantity = Integer.parseInt(request.getParameter("ingredientQuantity").trim());
                            } catch (NumberFormatException ex) {
                                System.out.println("error" + ex.getMessage());
                            }
                        }
                        if (ingredientServiceImpl.addIngredient(new Ingredient(0, ingredientName, ingredientQuantity, new Unit(ingredientUnitID), true))) {
                            request.setAttribute("allIngredients", new ArrayList<>(ingredientServiceImpl.getAllIngredient()));
                            request.setAttribute("message", "Ingredient added!");
                        } else {
                            request.setAttribute("message", "Ingredient not Added");
                        }

                        page = "Admin/IngredientsPage.jsp";
                    }

                    break;

                //---------------------------------------------------------------------------------------------------------
                case "edit":

                    ingredientName = request.getParameter("IngredientName");
                    if (ingredientName != null) {
                        if (request.getParameter("IngredientUnit") != null && !request.getParameter("IngredientUnit").isEmpty()
                                && request.getParameter("IngredientID") != null && !request.getParameter("IngredientID").isEmpty()
                                && request.getParameter("IngredientQuantity") != null && !request.getParameter("IngredientQuantity").isEmpty()) {
                            try {
                                ingredientUnitID = Integer.parseInt(request.getParameter("IngredientUnit").trim());
                                ingredientID = Integer.parseInt(request.getParameter("IngredientID").trim());
                                ingredientQuantity = Integer.parseInt(request.getParameter("IngredientQuantity").trim());
                            } catch (NumberFormatException ex) {
                                System.out.println("error " + ex.getMessage());
                            }
                        }
                        if (!ingredientName.isEmpty() && ingredientID > 0) {
                            if (ingredientServiceImpl.editIngredient(new Ingredient(ingredientID, ingredientName, ingredientQuantity, (new Unit(ingredientUnitID))))) {
                                request.setAttribute("allIngredients", new ArrayList<>(ingredientServiceImpl.getAllIngredient()));
                                request.setAttribute("message", "Ingredient Updated!");
                            } else {
                                request.setAttribute("message", "Ingredient not Updated!");
                            }
                        }
                    }
                    page = "Admin/IngredientsPage.jsp";
                    break;
                //---------------------------------------------------------------------------------------------------------
                case "get":
                    allIngredients = ingredientServiceImpl.getAllIngredient();
                    if (allIngredients != null && !allIngredients.isEmpty()) {

                        request.setAttribute("allIngredients", allIngredients);
                    } else {
                        request.setAttribute("response", "no data");
                    }
                    page = "Admin/IngredientsPage.jsp";
                    break;

                case "getStock":
                    allIngredients = ingredientServiceImpl.getAllIngredient();
                    if (allIngredients != null && !allIngredients.isEmpty()) {

                        request.setAttribute("allIngredients", allIngredients);
                    } else {
                        request.setAttribute("response", "no data");
                    }
                    page = "Admin/IngredientStockPage.jsp";
                    break;

                //------------------------------------------------------------------------------------
                case "delete":

                    ingredientStatus = request.getParameter("ingredientStatus");
                    if (ingredientStatus != null && !ingredientStatus.isEmpty()) {

                        ingredientStatus = ingredientStatus.trim();
                        flagStatus = checkFlag(ingredientStatus, request);
                        if (request.getParameter("ingredientID") != null && !request.getParameter("ingredientID").isEmpty()) {
                            try {
                                ingredientID = Integer.parseInt(request.getParameter("ingredientID").trim());
                            } catch (NumberFormatException ex) {
                                System.out.println("error " + ex.getMessage());
                            }
                        }

                        if (ingredientServiceImpl.deleteIngredient(new Ingredient(ingredientID, flagStatus))) {
                            request.setAttribute("allIngredients", new ArrayList<>(ingredientServiceImpl.getAllIngredient()));
                            request.setAttribute("message", "Ingredient status changed");

                        } else {
                            request.setAttribute("reponse", "status not changed");
                        }
                    }
                    page = "Admin/IngredientsPage.jsp";
                    break;

                default:
                    request.setAttribute("response", "something went wrong");
                    break;
            }

        }
    }
//    

    private void allUnits(HttpServletRequest request) {
        ServletContext sc = request.getServletContext();
        DatabaseManager databaseManager = (DatabaseManager) sc.getAttribute("dbman");
        UnitService unitService = new UnitServiceImpl(UnitDaoImpl.getInstance(databaseManager.getConnection()));
        request.setAttribute("allUnits", new ArrayList<>(unitService.getAllUnits()));
    }

    private boolean checkFlag(String ingredientString, HttpServletRequest request) {
        boolean flagStatus = false;
        switch (ingredientString) {

            case "Activate":

                flagStatus = true;
                break;
            case "Deactivate":

                flagStatus = false;
                break;
            default:

                request.setAttribute("error", "error");
                break;
        }
        return flagStatus;
    }

    @Override
    public void processTheResponse(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(page);
        try {
            requestDispatcher.forward(request, response);

        } catch (ServletException | IOException ex) {
            System.err.println("Error Dispatching View: " + ex.getMessage());
        }
    }
}
