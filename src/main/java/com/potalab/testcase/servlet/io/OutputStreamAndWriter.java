package com.potalab.testcase.servlet.io;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns="/OutputStreamAndWriter", asyncSupported = true)
public class OutputStreamAndWriter extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(OutputStreamAndWriter.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    resp.setContentType("text/html;charset=UTF-8");
    PrintWriter pw = resp.getWriter();
    AsyncContext ctx = req.startAsync();

//    try {
//      ServletOutputStream sos = resp.getOutputStream();
//    } catch (IOException e) {
//      logger.info("깍웅!!");
//    }

    pw.write(getHtml());
    ctx.complete();

  }

  public String getHtml() {
    StringBuilder html = new StringBuilder();

    html.append("<!DOCTYPE html>\n");
    html.append("<html>\n");
    html.append("<head>\n");
    html.append("    <title>Hello World!</title>\n");
    html.append("</head>\n");
    html.append("<body>\n");
    html.append("<pre>");
    html.append("</pre>");
    html.append("<h1> ServletOutputStream And Writer 테스트 </h1>");
    html.append("</body>\n");
    html.append("</html>\n");
    return html.toString();
  }
}
