package com.potalab.testcase.servlet.dispatching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
            default:
                doSendError(req, resp);

        }
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
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for async dispatch</title>");
        pw.println("</head>");

        asyncContext.dispatch("/dispatching/async2");
    }

}
