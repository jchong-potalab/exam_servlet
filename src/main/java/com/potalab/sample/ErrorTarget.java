package com.potalab.sample;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorTarget extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.setContentType("text/html;charset=UTF-8");
        ServletOutputStream os = resp.getOutputStream();

        os.println("<html>");
        os.println("<head>");
        os.println("<title>Wafull sample page by PotaLab Inc.</title>");
        os.println("</head>");
        os.println("<body>");
        os.println(String.format("<h1>Hello WAFULL world !!! Current Dispatcher Type is - %s</h1>",
            req.getDispatcherType()));

        String requestUri = (String)req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        String servletName = (String)req.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);
        Integer errorCode = (Integer)req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String)req.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Class<?> errorExceptionType = (Class<?>)req.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Throwable errorException = (Throwable)req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        os.println("<table border=\"1\">");
        os.println("<tbody>");
        os.println(String.format("<tr><td>Error Request Uri</td><td>%s</td></tr>", requestUri));
        os.println(String.format("<tr><td>Error Servlet Name</td><td>%s</td></tr>", servletName));
        os.println(String.format("<tr><td>Error Status Code</td><td>%d</td></tr>", errorCode));
        os.println(String.format("<tr><td>Error Message</td><td>%s</td></tr>", errorMessage));
        os.println(String.format("<tr><td>Exception Type</td><td>%s</td></tr>",
            errorExceptionType == null ? null : errorExceptionType.getCanonicalName()));
        os.println(String.format("<tr><td>Stack Trace</td><td>%s</td></tr>",
            getCallStackFormattedMessage(errorException)));
        os.println("</tbody>");
        os.println("</table>");

        os.println("</body>");
        os.println("</html>");
        os.flush();
    }

    private String getCallStackFormattedMessage(Throwable throwable) {

        if (throwable == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for(StackTraceElement ste :stackTraceElements) {
            sb.append(ste.toString()).append("<br>");
        }

        return sb.toString();
    }
}
