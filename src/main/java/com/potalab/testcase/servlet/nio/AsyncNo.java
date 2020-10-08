package com.potalab.testcase.servlet.nio;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/nio/AsyncNo", asyncSupported = true)
public class AsyncNo extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(AsyncNo.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    processRequest(req, resp);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    processRequest(req, resp);

  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    AsyncContext asyncContext = request.startAsync();

    asyncContext.addListener(new AsyncListener() {
      @Override
      public void onComplete(AsyncEvent event) throws IOException {

      }

      @Override
      public void onTimeout(AsyncEvent event) throws IOException {
        asyncContext.complete();
      }

      @Override
      public void onError(AsyncEvent event) throws IOException {

      }

      @Override
      public void onStartAsync(AsyncEvent event) throws IOException {

      }
    });

    ServletOutputStream os = response.getOutputStream();

    response.setContentType("text/html;charset=utf-8");
    final byte[] bytes = getBigHtml().getBytes();
    os.setWriteListener(new WriteListener() {
      int offSet = 0;
      int writeLength = response.getBufferSize();
      int dataLength = bytes.length;
      @Override
      public void onWritePossible() throws IOException {
        while(os.isReady()){
          logger.debug("offSet value>>>{}", offSet);
          if(offSet + writeLength == dataLength){
            asyncContext.complete();
            break;
          }
          else if(offSet + writeLength > dataLength){
            os.write(bytes, offSet, dataLength - offSet);
            asyncContext.complete();
            break;
          } else {
            os.write(bytes, offSet, writeLength);
            offSet += writeLength;
          }
        }
      }

      @Override
      public void onError(Throwable t) {
      }
    });

  }

  public String getBigHtml() {
    StringBuilder html = new StringBuilder();

    html.append("<!DOCTYPE html>\n");
    html.append("<html>\n");
    html.append("<head>\n");
    html.append("    <title>Hello World!</title>\n");
    html.append("</head>\n");
    html.append("<body>\n");
    html.append("<pre>");
    html.append("</pre>");
    for (int i = 1; i <= 10000 * 5; i++) {
      html.append(i);
      html.append(" : ");
      html.append("hello world : " + i + "<br>");
    }
    html.append("</body>\n");
    html.append("</html>\n");

    return html.toString();
  }

}
