package com.potalab.testcase.servlet.illegaldispatching;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/illegal/forwardTarget")
public class ForwardTarget extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(ForwardTarget.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    logger.info("ForwardTarget#doGet");

    ServletOutputStream sos = resp.getOutputStream();
    sos.write(getHtml().getBytes());
    //sos.flush();


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
    html.append("Illegal Dispatch Test of forward target !!!");
    html.append("</h1>");
    html.append("</body>\n");
    html.append("</html>\n");
    return html.toString();
  }

}
