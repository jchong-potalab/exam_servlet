package com.potalab.testcase.servlet.exceptioncase;

import com.potalab.testcase.servlet.ThreadUtil;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 비동기고 뭐고 없다. 서블릿에서 예외가 throw 되면 일어나는 현상을 본다.
 */
@WebServlet(value = "/exception/ServletThrowException")
public class ServletThrowException extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ServletThrowException.class);

    @Override
    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

        String opCode = request.getParameter("opCode");

        if ("commit".equals(opCode)) {

            response.setContentType("text/html;charset=utf-8");
            ServletOutputStream sos = response.getOutputStream();
            sos.write(getHtml().getBytes());
            sos.flush();
        }

        ThreadUtil.pause(1000); // 이렇게 기다림이 없으면 클라이언트로 컨텐츠가 내려가지
                                        // 못하고 fail 이 내려간다.
        AsyncContext asyncContext = request.startAsync(); // 배포정보에 supportAsync = true 없으면 이 대목에서 예외가 throw 됨
    }


    public String getHtml() {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <title>Hello servlet World!</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<pre>");
        html.append("</pre>");
        html.append("<h1> Servlet's errorPage test!!! </h1>");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

}
