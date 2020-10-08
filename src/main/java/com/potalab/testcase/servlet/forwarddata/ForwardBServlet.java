package com.potalab.testcase.servlet.forwarddata;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns="/data/ForwardBServlet")
public class ForwardBServlet extends HttpServlet {
  public ForwardBServlet() {
  }

  protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    request.setAttribute("glamorous.cat.forwardB", "glamorous.cat.forwardB");

    response.setContentType("text/html");
    PrintWriter output = response.getWriter();
    output.println("<br>");
    output.println("-------------------------------<br>");
    output.println("3. forward B attributes</br>");
    output.println("-------------------------------<br>");
    Util.getAttributes(request).forEach((name, value) -> {
      output.println(name + " : " + value + "<br>");
    });

    output.println("<br>");
    output.println("-------------------------------<br>");
    output.println("3. forward B http parameters</br>");
    output.println("-------------------------------<br>");
    Util.getParameters(request).forEach((name, value) -> {
      output.println(name + " : " + value + "<br>");
    });
  }
}