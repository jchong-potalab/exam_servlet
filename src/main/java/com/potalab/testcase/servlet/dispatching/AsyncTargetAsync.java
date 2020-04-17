package com.potalab.testcase.servlet.dispatching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
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
            logger.debug("will be create new async context!");
            ac = request.startAsync();
        }

        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                logger.debug("onComplete(...) >>> " + this.getClass().getSimpleName());
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {

            }

            @Override
            public void onError(AsyncEvent event) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {
                logger.debug("onStartAsync(...) >>> " + this.getClass().getSimpleName());
            }
        });

        ac.start(()->{

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            pw.println("In Async servlet's sub thread !!!!");
            ac.complete();
        });
    }

}
