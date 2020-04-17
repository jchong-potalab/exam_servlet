package com.potalab.testcase.servlet.dispatching;

import com.potalab.testcase.servlet.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/dispatching/entry", asyncSupported = true)
public class Entry extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Entry.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String Operation = req.getParameter("opCode");

        switch(Operation.toUpperCase()) {
            case "REDIRECT":
                doRedirect(req, resp);
                break;
            case "FORWARD":
                doForward(req, resp);
                break;
            case "INCLUDE":
                doInclude(req, resp);
                break;
            case "ASYNC":
                doAsync(req, resp);
                break;
            case "ASYNC2":
                doAsync2(req, resp);
                break;
            case "ASYNC3":
                doAsync3(req, resp);
                break;
            case "EXCEPTION_CASE1":
                doSyncToAsync(req, resp);
                break;
            default:
                doSendError(req, resp);

        }
    }

    private void doSyncToAsync(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/dispatching/async2");
        dispatcher.forward(req, resp);
    }

    private void doSendError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(404, "error message for test!!!!");
        logger.info(req.getDispatcherType().name()); // REQUEST가 출력 된다. ERROR가 아니다.
    }

    private void doRedirect(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for sendRedirect</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<h1>OOPs this contents write before sendRedirect !!!</h1>");
        pw.println("</body>");
        pw.println("</html>");

        resp.sendRedirect("redirect");
    }

    private void doForward(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDisp
                = req.getRequestDispatcher("/dispatching/forward");

        //
        // forward 하기 전에 쓴 내용은 모두 무효화 된다.
        //

        requestDisp.forward(req, resp);
    }

    private void doInclude(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDisp
                = req.getRequestDispatcher("/dispatching/include");

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for include</title>");
        pw.println("</head>");

        requestDisp.include(req, resp);

        pw.println("</html>");
        pw.flush();


    }

    private void doAsync(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0); // NO timeout
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for async dispatch</title>");
        pw.println("</head>");

        asyncContext.start(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        asyncContext.dispatch("/dispatching/async");

    }

    private void doAsync2(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0); // NO timeout

        asyncContext.addListener(new AsyncListener() {
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

        asyncContext.dispatch("/dispatching/async2");
        logger.debug("After calling the dispatch function ...");

        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for async dispatch</title>");
        pw.println("</head>");
    }

    private void doAsync3(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0); // NO timeout
        asyncContext.addListener(new AsyncListener() {
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
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for async dispatch</title>");
        pw.println("</head>");

        req.getRequestDispatcher("/dispatching/async2").include(req, resp);
        ThreadUtil.pause(3000);
        logger.debug("Debug >>> Exit for service control block. After 3sec ...");
    }

}
