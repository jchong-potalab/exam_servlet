package com.potalab.testcase.servlet.dispatching;

import javax.servlet.ServletOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/dispatching/async")
public class AsyncTarget extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ForwardTarget.class);

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

        try {
            ServletOutputStream os = response.getOutputStream();
            os.write(0);
        } catch (Exception e) {
            throw e;
        }
    }
}
