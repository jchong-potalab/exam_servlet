package com.potalab.testcase.servlet.illegaldispatching;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/illegal/forward")
public class IllegalForward extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(IllegalForward.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp);
  }

  @Override
  protected void doHead(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    processRequest(req, resp);
  }

  private void processRequest(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {


    resp.setContentType("text/html;charset=UTF-8");

    ServletOutputStream sos = resp.getOutputStream();
    sos.write(getHtml().getBytes());
    sos.flush();

    //RequestDispatcher dispatcher = req.getRequestDispatcher("/illegal/forwardTarget");
    //dispatcher.forward(req, resp);

  }

  public String getHtml() {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>\n");
    html.append("<html>\n");
    html.append("<head>\n");
    html.append("    <title>Hello World!</title>\n");
    html.append("</head>\n");
    html.append("<body>\n");
    html.append("<h1>");
    html.append("Illegal Dispatch Test !!!");
    html.append("</h1>");
    html.append("</body>\n");
    html.append("</html>\n");
    return html.toString();
  }

}
