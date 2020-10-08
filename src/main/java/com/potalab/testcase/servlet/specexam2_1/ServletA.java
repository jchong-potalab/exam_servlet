package com.potalab.testcase.servlet.specexam2_1;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/exam2_1/a", asyncSupported = true)
public class ServletA extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    AsyncContext asyncContext = req.startAsync();

    if (req.getDispatcherType() == DispatcherType.ASYNC) { // 이 조건이 없으면 무한루프에 빠진다.
      asyncContext.complete(); // 여기서 완료 하라 했다고 바로 커밋 되는 것이 아님, pending 실행 됨
    } else {
      asyncContext.dispatch();
    }

    ServletOutputStream os = resp.getOutputStream();

    StringBuffer sb = new StringBuffer();

    sb.append("<html>");
    sb.append("<body>");
    sb.append("<h1> code example 2-1 of Servlet Spec 4.0 </h1>");
    sb.append(String.format("<h2> This dispatch's Dispatch Type is %s </h2>", req.getDispatcherType()));
    sb.append("</body>");
    sb.append("</html>");

    os.write(sb.toString().getBytes());

  }

}
