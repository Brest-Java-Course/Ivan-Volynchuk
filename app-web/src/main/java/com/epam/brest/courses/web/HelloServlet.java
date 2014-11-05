package com.epam.brest.courses.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by fieldistor on 05.11.14.
 */
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("name");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.print("Hello ");
        out.print("<input type='button' />");
        out.print(parameter);
        out.close();
    }

}
