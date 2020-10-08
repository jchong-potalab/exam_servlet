package com.potalab.testcase.servlet.specexam2_2;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/exam2_2/c", asyncSupported = true)
public class ServletC extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ServletOutputStream os = resp.getOutputStream();

    StringBuffer sb = new StringBuffer();

    sb.append("<html>");
    sb.append("<body>");
    sb.append("<h1> code example 2-2 of Servlet Spec 4.0 </h1>");
    sb.append(String.format("<h2> >>>> This dispatch's Dispatch Type is %s </h2>", req.getDispatcherType()));
    sb.append("</body>");
    sb.append("</html>");
  }


}
