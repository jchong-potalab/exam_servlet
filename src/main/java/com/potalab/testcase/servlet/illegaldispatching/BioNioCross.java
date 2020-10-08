package com.potalab.testcase.servlet.illegaldispatching;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/illegal/bionio", asyncSupported = true)
public class BioNioCross extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    AsyncContext asyncContext = req.startAsync();
    asyncContext.setTimeout(300);
    ServletOutputStream sos = resp.getOutputStream();
    sos.setWriteListener(new WriteListener() {
      @Override
      public void onWritePossible() throws IOException {

      }

      @Override
      public void onError(Throwable t) {

      }
    });

    sos.write(getHtml().getBytes());
    sos.flush();

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
    html.append("BIO write on NIO mode Test !!!");
    html.append("</h1>");
    html.append("</body>\n");
    html.append("</html>\n");
    return html.toString();
  }

}
