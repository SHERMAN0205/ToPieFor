package com.topiefor.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/PdfDeliverController")
public class PdfDeliverController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the order information from the request parameters
        String orderInfo = request.getParameter("orderInfo");

        // Split the order information into individual data elements
        String[] orderData = orderInfo.split(",");

        // Generate the PDF document
        Document document = new Document();
        try {
            // Set the response headers for PDF download
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=NeedToDeliverOrders.pdf");

            // Create a PDF writer
            PdfWriter.getInstance(document, response.getOutputStream());

            // Open the PDF document
            document.open();

            // Add paragraphs with the order information to the document
            document.add(new Paragraph("Order ID: " + orderData[0]));
            document.add(new Paragraph("Date Ordered: " + orderData[1]));
            document.add(new Paragraph("Date to be Delivered: " + orderData[2]));
            document.add(new Paragraph("Telephone Number: " + orderData[3]));
            document.add(new Paragraph("Status: " + orderData[4] + " and needs to be Delivered"));
            document.add(new Paragraph("Street Address: " + orderData[5]));
            document.add(new Paragraph("Suburb: " + orderData[6]));
//            document.add(new Paragraph("Recipe List: " + orderData[5]));

            // Close the PDF document
            document.close();
        } catch (DocumentException e) {
        }
    }
}