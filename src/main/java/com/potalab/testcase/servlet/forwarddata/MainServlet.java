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

@WebServlet(urlPatterns="/data/MainServlet")
public class MainServlet extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(MainServlet.class);

  public MainServlet() {
  }

  protected void service(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    request.setAttribute("glamorous.cat.main", "glamorous.cat.main");

    try {
      RequestDispatcher dispatcher
          = request.getServletContext().getRequestDispatcher("/data/ForwardAServlet?main=main");
      dispatcher.forward(request, response);
    } catch (Exception e) {
      e.printStackTrace();
    }

    response.setContentType("text/html");
    PrintWriter output = response.getWriter();
    logger.info("<br>");
    logger.info("-------------------------------<br>");
    logger.info("1. main attributes</br>");
    logger.info("-------------------------------<br>");
    Util.getAttributes(request).forEach((name, value) -> {
      logger.info(name + " : " + value + "<br>");
    });

    logger.info("<br>");
    logger.info("-------------------------------<br>");
    logger.info("1. main http parameters</br>");
    logger.info("-------------------------------<br>");
    Util.getParameters(request).forEach((name, value) -> {
      logger.info(name + " : " + value + "<br>");
    });

  }
}