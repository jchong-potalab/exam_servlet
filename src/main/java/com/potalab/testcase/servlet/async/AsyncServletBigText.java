package com.potalab.testcase.servlet.async;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/AsyncServletBigText", asyncSupported = true)
public class AsyncServletBigText extends HttpServlet {

  public AsyncServletBigText() {
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    AsyncContext context = request.startAsync();
    context.addListener(new AsyncListener() {
      @Override
      public void onComplete(AsyncEvent asyncEvent) throws IOException {
        System.out.println("complete!!!");
      }

      @Override
      public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        System.out.println("timeout");
      }

      @Override
      public void onError(AsyncEvent asyncEvent) throws IOException {

      }

      @Override
      public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

      }
    });

    response.setContentType("application/octet-stream");
    response.addHeader("Content-Disposition", "filename=\"hello.txt\"");
    ServletOutputStream output = response.getOutputStream();
    output.setWriteListener(new WriteListener() {
      @Override
      public void onWritePossible() throws IOException {
        output.write(getHtml().getBytes());
        context.complete();
      }

      @Override
      public void onError(Throwable t) {

      }
    });

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
    for (int i = 1; i <= 900000 * 10; i++) {
      html.append(i);
      html.append(" : ");
      html.append("hello world : " + i + "<br>");
    }
    html.append("</body>\n");
    html.append("</html>\n");

    return html.toString();
  }


  public void waitFor(long ms) {
    try {
      Thread.sleep(ms);
    } catch (Exception e) {
    }
  }

}

