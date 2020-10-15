package com.potalab.wafull.sample;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AsyncTarget extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.setHeader("keep-alive", "close");
        resp.setContentType("text/html;charset=UTF-8");
        ServletOutputStream os = resp.getOutputStream();
        os.println(String.format("<h1>ASYNC TARGET CONTENTS - %s <h1>", req.getDispatcherType()));

        String requestUri = (String)req.getAttribute(AsyncContext.ASYNC_REQUEST_URI);
        String contextPath = (String)req.getAttribute(AsyncContext.ASYNC_CONTEXT_PATH);
        String servletPath = (String)req.getAttribute(AsyncContext.ASYNC_SERVLET_PATH);
        String pathInfo = (String)req.getAttribute(AsyncContext.ASYNC_PATH_INFO);
        String queryString = (String)req.getAttribute(AsyncContext.ASYNC_QUERY_STRING);
        HttpServletMapping mapping = (HttpServletMapping)req.getAttribute(AsyncContext.ASYNC_MAPPING);

        os.println("<h3>AsyncContext.ASYNC_XXX attribute values <h3>");

        os.println("<table border=\"1\">");
        os.println("<tbody>");

        os.println(String.format("<tr><td>request uri</td><td>%s</td></tr>", requestUri));
        os.println(String.format("<tr><td>context path</td><td>%s</td></tr>", contextPath));
        os.println(String.format("<tr><td>servlet path</td><td>%s</td></tr>", servletPath));
        os.println(String.format("<tr><td>path info</td><td>%s</td></tr>", pathInfo));
        os.println(String.format("<tr><td>query string</td><td>%s</td></tr>", queryString));

        if ( mapping != null ) {
            os.println(String.format("<tr><td>servlet mapping-servlet name</td><td>%s</td></tr>", mapping.getServletName()));
            os.println(String.format("<tr><td>servlet mapping-pattern</td><td>%s</td></tr>", mapping.getPattern()));
            os.println(String.format("<tr><td>servlet mapping-match value</td><td>%s</td></tr>", mapping.getMatchValue()));
            os.println(String.format("<tr><td>servlet mapping-mapping match</td><td>%s</td></tr>", mapping.getMappingMatch().toString()));
        }

        os.println("</tbody>");
        os.println("</table>");
        os.println("<h3>Path element values <h3>");

        os.println("<table border=\"1\">");
        os.println("<tbody>");

        os.println(String.format("<tr><td>request uri</td><td>%s</td></tr>", req.getRequestURI()));
        os.println(String.format("<tr><td>context path</td><td>%s</td></tr>", req.getContextPath()));
        os.println(String.format("<tr><td>servlet path</td><td>%s</td></tr>", req.getServletPath()));
        os.println(String.format("<tr><td>path info</td><td>%s</td></tr>", req.getPathInfo()));
        os.println(String.format("<tr><td>query string</td><td>%s</td></tr>", req.getQueryString()));

        os.println("</tbody>");
        os.println("</table>");

        os.println("</body>");
        os.println("</html>");

    }
}
