package com.bookstore.controller.admin;

import com.bookstore.dao.*;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Review;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/")
public class AdminHomeServlet extends HttpServlet {

    public AdminHomeServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderDAO orderDAO = new OrderDAO();
        ReviewDAO reviewDAO = new ReviewDAO();
        UserDAO userDAO = new UserDAO();
        BookDAO bookDAO = new BookDAO();
        CustomerDAO customerDAO = new CustomerDAO();


        List<BookOrder> listMostRecentSales = orderDAO.listMostRecentSales();
        List<Review> listMostRecentReviews = reviewDAO.listAll();

        long totalUsers = userDAO.count();
        long totalBooks = bookDAO.count();
        long totalCustomer = customerDAO.count();
        long totalReviews = reviewDAO.count();
        long totalOrders = orderDAO.count();

        request.setAttribute("listMostRecentSales",listMostRecentSales);
        request.setAttribute("listMostRecentReviews",listMostRecentReviews);

        request.setAttribute("totalUsers",totalUsers);
        request.setAttribute("totalBooks",totalBooks);
        request.setAttribute("totalCustomer",totalCustomer);
        request.setAttribute("totalReviews", totalReviews);
        request.setAttribute("totalOrders", totalOrders);

        String homePage = "index.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(homePage);
        dispatcher.forward(request, response);
    }


}
