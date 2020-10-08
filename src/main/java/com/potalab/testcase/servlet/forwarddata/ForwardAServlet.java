package com.potalab.testcase.servlet.forwarddata;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns="/data/ForwardAServlet")
public class ForwardAServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(ForwardAServlet.class);

  public ForwardAServlet() {
  }

  protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
    request.setAttribute("glamorous.cat.forwardA", "glamorous.cat.forwardA");

    try {
      RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/data/ForwardBServlet?forwardA=forwardA");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }

    response.setContentType("text/html");
    PrintWriter output = response.getWriter();
    logger.info("<br>");
    logger.info("-------------------------------<br>");
    logger.info("2. forward A attributes</br>");
    logger.info("-------------------------------<br>");
    Util.getAttributes(request).forEach((name, value) -> {
      logger.info(name + " : " + value + "<br>");
    });

    logger.info("<br>");
    logger.info("-------------------------------<br>");
    logger.info("2. forward A http parameters</br>");
    logger.info("-------------------------------<br>");
    Util.getParameters(request).forEach((name, value) -> {
      logger.info(name + " : " + value + "<br>");
    });
  }
}