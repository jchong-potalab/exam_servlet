package com.potalab.testcase.servlet.nio;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = "/nio/AsyncNi", asyncSupported = true)
public class AsyncNi extends HttpServlet {

  private static final Logger logger = LoggerFactory.getLogger(AsyncNi.class);

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    logger.info("① doGet");
    processRequest(req, resp);

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    logger.info("① doPost");
    processRequest(req, resp);

  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    AsyncContext asyncContext = request.startAsync();
    ServletInputStream is = request.getInputStream();
    is.setReadListener(new ReadListener() {
      @Override
      public void onDataAvailable() throws IOException {
        logger.info("②-1 onDataAvailable");

        StringBuilder sb = new StringBuilder();
        int len = -1;
        byte b[] = new byte[8];
        while (is.isReady() && (len = is.read(b)) != -1) {
          String data = new String(b, 0, len);
          sb.append(data);
        }

        logger.info("Data read: "+ sb.toString());
      }

      @Override
      public void onAllDataRead() throws IOException {
        logger.info("②-2 onAllDataRead");
      }

      @Override
      public void onError(Throwable t) {

      }
    });
  }
}
