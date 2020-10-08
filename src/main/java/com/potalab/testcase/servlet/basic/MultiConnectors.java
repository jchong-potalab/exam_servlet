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
@WebServlet(urlPatterns = "/basic/multi-conn")
public class MultiConnectors extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    ServletOutputStream sos = resp.getOutputStream();

    sos.write(getHtml(req).getBytes());
    sos.flush(); // commit 한 것이다.
    ThreadUtil.pause(1000); // 1초 기다린다. 요청/응답 처리는 아직 진행중이다.

  }

  public String getHtml(HttpServletRequest req) {
    StringBuilder html = new StringBuilder();
    html.append("<!DOCTYPE html>\n");
    html.append("<html>\n");
    html.append("<head>\n");
    html.append("    <title>Hello World!!! - Multi-Connector Case</title>\n");
    html.append("</head>\n");
    html.append("<body>\n");
    html.append("<h1>");

    html.append(String.format("getLocalPort() >>> %d<br/>", req.getLocalPort()));
    html.append(String.format("getLocalAddr() >>> %s<br/>", req.getLocalAddr()));
    html.append(String.format("getLocalName() >>> %s<br/>", req.getLocalName()));
    html.append("----------------------------------------------------------<br/>");
    html.append(String.format("getRemotePort() >>> %d<br/>", req.getRemotePort()));
    html.append(String.format("getRemoteAddr() >>> %s<br/>", req.getRemoteAddr()));
    html.append(String.format("getRemoteHost() >>> %s<br/>", req.getRemoteHost()));
    html.append(String.format("getRemoteUser() >>> %s<br/>", req.getRemoteUser()));


    html.append("</h1>");
    html.append("</body>\n");
    html.append("</html>\n");
    return html.toString();
  }
}
