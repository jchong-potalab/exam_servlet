package com.potalab.testcase.servlet.namedfilter;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "target_servlet", urlPatterns = "/namedfilter/target_servlet", asyncSupported = true)
public class TargetServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter pw = resp.getWriter();
    pw.println("<html>");
    pw.println("<head>");
    pw.println("<title>Test case for forward</title>");
    pw.println("</head>");
    pw.println("<body>");
    pw.printf("<h1>Dispatcher Type is %s call by entry</h1>" + System.lineSeparator()
        , req.getDispatcherType() );
    pw.println("</body>");
    pw.println("</html>");
    pw.flush();

  }
}
