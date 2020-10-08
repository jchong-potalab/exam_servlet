package com.potalab.testcase.servlet.dispatching;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardTargetByDD extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ForwardTargetByDD.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String queryString = req.getQueryString();
        processRequest(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getDispatcherType() == DispatcherType.FORWARD) {
            String mode = request.getParameter("mode");

            if (mode != null && mode.equals("dispatch")) {
                AsyncContext ctx = request.startAsync(); // 인자가 없는 경우
                logger.debug("getRequestURI() >>> {}" , request.getRequestURI());
                logger.debug("getRequestURL() >>> {}" , request.getRequestURL());
                ctx.dispatch(); // REQUEST 한 URL로 dispatch 된다. 결과론 적으로 dispatch to request

            } else if (mode != null && mode.equals("dispatch2")) {

                AsyncContext ctx = request.startAsync(request, response); // 인자가 있는 경우
                logger.debug("getRequestURI() >>> {}" , request.getRequestURI());
                logger.debug("getRequestURL() >>> {}" , request.getRequestURL());
                ctx.dispatch(); // getReqeustURI()로 dispatch 한다. 결과론적으로 dispatch to self

            }
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for forward</title>");
        pw.println("</head>");
        pw.println("<body>");
        pw.printf("<h1>Dispatcher Type is %s call by entry</h1>" + System.lineSeparator()
                , request.getDispatcherType() );
        pw.println("</body>");
        pw.println("</html>");
        pw.flush();
    }
}
