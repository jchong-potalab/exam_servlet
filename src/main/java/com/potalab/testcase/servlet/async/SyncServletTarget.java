package com.potalab.testcase.servlet.async;

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


@WebServlet(value = "/SyncServletTarget")
public class SyncServletTarget extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(SyncServletTarget.class);

    protected void doPost(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

        processRequest(request, response);

    }

    protected void doGet(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

        processRequest(request, response);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream sos = response.getOutputStream();
        sos.write(getHtml().getBytes());

        logger.info("isAsyncStarted() == {}", request.isAsyncStarted());

        AsyncContext ctx = request.getAsyncContext();
        ctx.complete();


    } // 이 블럭(Servlet#service)을 빠져 나가야 timeout, complete, dispatch 가 작용한다.

    public String getHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <title>Hello World!</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<h1>");
        html.append("SyncServletTarget !!!" );
        html.append("</h1>");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }

}
