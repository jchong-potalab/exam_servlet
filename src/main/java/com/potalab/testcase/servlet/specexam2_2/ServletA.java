package com.potalab.testcase.servlet.specexam2_2;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/exam2_2/a", asyncSupported = true)
public class ServletA extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


    if (req.getDispatcherType() == DispatcherType.REQUEST) {
      RequestDispatcher requestDispatcher = req.getRequestDispatcher("/exam2_2/b");
      requestDispatcher.forward(req, resp);
    } else if (req.getDispatcherType() == DispatcherType.ASYNC) {
      ServletOutputStream os = resp.getOutputStream();
      os.println("<h1>append by dispatched servlet ...(DispatcherType.ASYNC) </h1>");
    }

  }

}
