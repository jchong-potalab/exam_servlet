package com.potalab.testcase.servlet.basic;

import com.potalab.testcase.servlet.ThreadUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 요청 및 응답의 최종 시점을 확인 하기 위한 테스트
 *
 * 일반적인 동기 서블릿의 요청/응답 처리 종료시점은 최초 커밋이 아님을 확인 하는테스트
 *
 */
@WebServlet(urlPatterns = "/basic/flush")
public class ResponseEndPoint extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ServletOutputStream sos = resp.getOutputStream();

    sos.write(getHtml().getBytes());
    sos.flush(); // commit 한 것이다.
    ThreadUtil.pause(1000); // 1초 기다린다. 요청/응답 처리는 아직 진행중이다.
    // 브라우저에서 1초 있다가 아래 문장을 출력한다.
    sos.write("<h1>Additional html tag ... </h1>".getBytes());

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
    html.append("Basic flush Test !!!");
    html.append("</h1>");
    html.append("</body>\n");
    html.append("</html>\n");
    return html.toString();
  }
}
