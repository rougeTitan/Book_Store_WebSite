package com.bookstore.controller.admin.book;


import com.bookstore.service.BookServices;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/admin/create_book")
@MultipartConfig(fileSizeThreshold = 1024 * 10, maxFileSize = 1024 * 300, maxRequestSize = 1024 * 1024)
public class CreateBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateBookServlet() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookServices bookServices = new BookServices( request, response);
        bookServices.createBook();
    }

}
