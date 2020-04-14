package com.potalab.testcase.servlet.dispatching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/dispatching/async2", asyncSupported = true)
public class AsyncTargetAsync  extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTargetAsync.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter pw = response.getWriter();
        pw.println("<body>");
        pw.printf("<h1>%s result call by entry</h1>" + System.lineSeparator()
                , request.getDispatcherType());
        pw.println("</body>");
        pw.println("</html>");
        pw.flush();

        AsyncContext ac;

        if ( request.isAsyncStarted() ) {
            logger.debug("already exists async context!");
            ac = request.getAsyncContext();
        } else {
            // 비동기 호출이 시작된 적이 없다. 새로운 context 가 생긴다.
            // 아마도 request가 wrapping 되서 그런듯 하다.
            logger.debug("will be create new async context!");
            ac = request.startAsync();
        }

        ac.start(()->{
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            pw.println("In Async servlet's sub thread !!!!");
            ac.complete();
        });
    }

}
