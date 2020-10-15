package com.potalab.wafull.sample;


import com.potalab.testcase.servlet.ThreadUtil;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WelcomeWafull extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String opCode = req.getParameter("opcode");

        if (opCode != null) {
            switch (opCode) {
                case "ASYNC1": {
                    AsyncContext asyncCtx =  req.startAsync();
                    resp.setContentType("text/html;charset=UTF-8");
                    ServletOutputStream os = resp.getOutputStream();
                    os.write("<html>".getBytes());
                    os.write("<head>".getBytes());
                    os.write("<title>Wafull sample page by PotaLab Inc.</title>".getBytes());
                    os.write("</head>".getBytes());
                    os.write("<body>".getBytes());
                    os.write(("<h1>Hello WAFULL world !!! async start and wait 2sec and " +
                        "complete.</h1>").getBytes());
                    os.write("</body>".getBytes());
                    os.write("</html>".getBytes());
                    os.flush();
                    ThreadUtil.pause(2000);
                    asyncCtx.complete();
                    break;
                }
                case "ASYNC2": {
                    AsyncContext asyncCtx = req.startAsync();
                    asyncCtx.dispatch("/servlet/example/AsyncTarget");
                    break;
                }
                case "ASYNC3": {
                    AsyncContext asyncCtx = req.startAsync();
                    asyncCtx.start(
                        () -> {
                            resp.setContentType("text/html;charset=UTF-8");
                            try {
                                ThreadUtil.pause(2000);
                                ServletOutputStream os = resp.getOutputStream();
                                os.write("<html>".getBytes());
                                os.write("<head>".getBytes());
                                os.write("<title>Wafull sample page by PotaLab Inc.</title>".getBytes());
                                os.write("</head>".getBytes());
                                os.write("<body>".getBytes());
                                os.write(("<h1>Hello WAFULL world !!! async start and call in thread " +
                                    "start</h1>").getBytes());
                                os.write("</body>".getBytes());
                                os.write("</html>".getBytes());
                                os.flush();
                                asyncCtx.complete();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    );
                    break;
                }
                case "ASYNC4": {
                    AsyncContext asyncCtx = req.startAsync();
                    asyncCtx.dispatch("/servlet/example/AsyncTarget?system=erp&user=sysop&role=admin");
                    break;
                }
                case "INCLUDE": {
                    RequestDispatcher rd
                        = req.getRequestDispatcher("/servlet/example/IncludeTarget?opcode" +
                        "=INCLUDE_TARGET&role=SYSOP");
                    rd.include(req, resp);
                    break;
                }
                case "FORWARD": {
                    RequestDispatcher rd
                        = req.getRequestDispatcher("/servlet/example/ForwardTarget?opcode" +
                        "=FORWARD_TARGET&role=SYSOP");
                    rd.forward(req, resp);
                    break;
                }
                case "ERROR":
                    resp.sendError(404, "test error message - page not found.");
                    break;
                case "ERROR2":
                    resp.sendError(503, "test error message - page not found.");
                    break;
                case "EXCEPT":
                    throw new RuntimeException("exception for test.....");
                default:
                    resp.setContentType("text/html;charset=UTF-8");
                    ServletOutputStream os = resp.getOutputStream();
                    os.write("<html>".getBytes());
                    os.write("<head>".getBytes());
                    os.write("<title>Wafull sample page by PotaLab Inc.</title>".getBytes());
                    os.write("</head>".getBytes());
                    os.write("<body>".getBytes());
                    os.write(String.format("<h1>Hello WAFULL world !!! opcode='%s' is not " +
                        "support~</h1>", opCode).getBytes());
                    os.write("</body>".getBytes());
                    os.write("</html>".getBytes());
                    break;
            }
        } else {
            resp.setContentType("text/html;charset=UTF-8");
            ServletOutputStream os = resp.getOutputStream();
            os.write("<html>".getBytes());
            os.write("<head>".getBytes());
            os.write("<title>Wafull sample page by PotaLab Inc.</title>".getBytes());
            os.write("</head>".getBytes());
            os.write("<body>".getBytes());
            os.write("<h1>Hello WAFULL world !!!</h1>".getBytes());
            os.write("<br><h2>opcode</h2>".getBytes());
            os.write("<table border=\"1\">".getBytes());
            os.write("<tbody>".getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=ASYNC1\">ASYNC1</a>" +
                "</td>" +
                "<td>비동기 시작 -> 2기다림 -> 비동기 완료</td>" +
                "</tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=ASYNC2\">ASYNC2</a>" +
                "</td>" +
                "<td>비동기 시작 -> dispatch -> async dispatch 에서 컨텐츠 쓰기를 통한 비동기 완료" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=ASYNC3\">ASYNC3</a>" +
                "</td>" +
                "<td>비동기 시작 -> asyncContext.start -> 비동기 완료" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=ASYNC4\">ASYNC4</a>" +
                "</td>" +
                "<td>비동기 dispatch의 query string 병합 테스트" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=INCLUDE\">INCLUDE</a>" +
                "</td>" +
                "<td>INCLUDE 테스트" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=FORWARD\">FORWARD</a>" +
                "</td>" +
                "<td>FORWARD 테스트" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=ERROR\">ERROR</a>" +
                "</td>" +
                "<td>404 ERROR 테스트" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=ERROR2\">ERROR2</a>" +
                "</td>" +
                "<td>500 ERROR 테스트" +
                "</td></tr>").getBytes());
            os.write(("<tr>" +
                "<td>" +
                "<a href=\"/ctx1/servlet/example/WelcomeWafull?opcode=EXCEPT\">EXCEPT</a>" +
                "</td>" +
                "<td>EXCEPTION 을 throw 하여 에러페이지로 분기가 잘 되는지 확인" +
                "</td></tr>").getBytes());
            os.write("</tbody>".getBytes());
            os.write("</table>".getBytes());
            os.write("</body>".getBytes());
            os.write("</html>".getBytes());
            os.flush();
        }
    }
}
