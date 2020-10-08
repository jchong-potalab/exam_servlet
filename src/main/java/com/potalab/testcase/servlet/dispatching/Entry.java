package com.potalab.testcase.servlet.dispatching;

import com.potalab.testcase.servlet.ThreadUtil;
import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/dispatching/entry/*", asyncSupported = true)
public class Entry extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(Entry.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        doProcess(req, resp);
    }

    private void doProcess(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        String operation = req.getParameter("opCode");

        logger.info("jchong DEBUG >>> " + req.getParameter("role"));

        switch(operation.toUpperCase()) {
            case "REDIRECT":
                doRedirect(req, resp);
                break;
            case "FORWARD":
                doForward(req, resp);
                break;
            case "FORWARD_NOT_EX":
                doForwardNotEx(req, resp);
                break;
            case "FORWARD_DD":
                doForwardDD(req, resp);
                break;
            case "FORWARD_DISP":
                doForwardAndDisp(req, resp);
                break;
            case "FORWARD_DISP2":
                doForwardAndDisp2(req, resp);
                break;
            case "INCLUDE":
                doInclude(req, resp);
                break;
            case "INCLUDE_DD":
                doIncludeDD(req, resp);
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

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a POST
     * request.
     * <p>
     * The HTTP POST method allows the client to send data of unlimited length to the Web server a
     * single time and is useful when posting information such as credit card numbers.
     *
     * <p>When overriding this method, read the request data,
     * write the response headers, get the response's writer or output stream object, and finally,
     * write the response data. It's best to include content type and encoding. When using a
     * <code>PrintWriter</code> object to return the response, set the
     * content type before accessing the <code>PrintWriter</code> object.
     *
     * <p>The servlet container must write the headers before committing the
     * response, because in HTTP the headers must be sent before the response body.
     *
     * <p>Where possible, set the Content-Length header (with the
     * {@link ServletResponse#setContentLength} method), to allow the servlet container to use a
     * persistent connection to return its response to the client, improving performance. The content
     * length is automatically set if the entire response fits inside the response buffer.
     *
     * <p>When using HTTP 1.1 chunked encoding (which means that the response
     * has a Transfer-Encoding header), do not set the Content-Length header.
     *
     * <p>This method does not need to be either safe or idempotent.
     * Operations requested through POST can have side effects for which the user can be held
     * accountable, for example, updating stored data or buying items online.
     *
     * <p>If the HTTP POST request is incorrectly formatted,
     * <code>doPost</code> returns an HTTP "Bad Request" message.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made
     *             of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends
     *             to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the
     *                          request
     * @throws ServletException if the request for the POST could not be handled
     * @see ServletOutputStream
     * @see ServletResponse#setContentType
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        doProcess(req, resp);
    }

    private void doForwardAndDisp2(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        //
        // forward 하기전에 쓴 내용은 모두 무효화 된다.
        //

        if ( req.getDispatcherType() == DispatcherType.REQUEST) {
            RequestDispatcher requestDisp
                = req.getRequestDispatcher("/dispatching/forward?mode=dispatch2");
            requestDisp.forward(req, resp);
        } else {
            logger.debug("[doForwardAndDisp2] Dispatch type is >>> {}", req.getDispatcherType());
        }
    }

    private void doForwardAndDisp(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        //
        // forward 하기 전에 쓴 내용은 모두 무효화 된다.
        //

        if ( req.getDispatcherType() == DispatcherType.REQUEST) {
            RequestDispatcher requestDisp
                = req.getRequestDispatcher("/dispatching/forward?mode=dispatch");
            requestDisp.forward(req, resp);
        } else {
            logger.debug("[doForwardAndDisp] Dispatch type is >>> {}", req.getDispatcherType());
        }


    }

    private void doSyncToAsync(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = req.getRequestDispatcher("/dispatching/async2");
        dispatcher.forward(req, resp);
    }

    private void doSendError(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(500, "error message for test!!!!");
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

//        resp.sendError(500, "test error");

        RequestDispatcher requestDisp
            = req.getRequestDispatcher("forward?a=a&b=b&role=domain_user&key1=CC");


        // ② 현재 컨텍스트의 루트 하위 절대 경로로 target 리소스를 지정하는 방법
//      RequestDispatcher requestDisp
//              = req.getRequestDispatcher("/dispatching/forward");

        // ③ ServletContext를 먼저 구함, 현재 컨텍스트의 루트 하위 절대 경로로 target 리소스를
        // 지정하는 방법
//      RequestDispatcher requestDisp
//               = req.getServletContext().getRequestDispatcher("/dispatching/forward");

        logger.info("This response's commit state is {}, check at before forward.", resp.isCommitted());

        // resp.sendError(500, "test error at Entry servlet in-bound");

        requestDisp.forward(req, resp);

        // resp.sendError(500, "test error at Entry servlet out-bound");

        logger.info("This response's commit state is {}, check at after forward.", resp.isCommitted());

        PrintWriter pw = resp.getWriter();

        pw.println("<p>김수한무 거북이와 두루미!!!!</p>");

    }

    private void doForwardNotEx(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();

        RequestDispatcher requestDisp
            = servletContext.getRequestDispatcher("/dispatching/forward/css/style.css?param1=a&param2=b");

        requestDisp.forward(req, resp);
    }

    private void doForwardDD(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        RequestDispatcher requestDisp
            = req.getServletContext().getNamedDispatcher("forward-target-by-dd");

        requestDisp.forward(req, resp);
    }

    private void doInclude(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher requestDisp
                = req.getRequestDispatcher("/dispatching/include?a=a&b=b&role=domain_user&key1=CC");

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

    private void doIncludeDD(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        RequestDispatcher requestDisp
            = req.getServletContext().getNamedDispatcher("include-target-by-dd");

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

        AsyncContext asyncContext = null;
        try {
            //asyncContext = req.startAsync(req, resp);
            asyncContext = req.startAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        asyncContext.setTimeout(0); // NO timeout
        PrintWriter pw = resp.getWriter();
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Test case for async dispatch</title>");
        pw.println("</head>");

//        asyncContext.start(()->{
//            try {
//                boolean b = asyncContext.hasOriginalRequestAndResponse();
//                pw.println(String.format("<p>%b</p>", b));
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        });

        asyncContext.dispatch("/dispatching/async?role=sysop");
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

    private class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {

        /**
         * Constructs a request object wrapping the given request.
         *
         * @param request the {@link HttpServletRequest} to be wrapped.
         * @throws IllegalArgumentException if the request is null
         */
        public MyHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
        }
    }

    private class MyHttpServletResponseWrapper extends HttpServletResponseWrapper {

        public MyHttpServletResponseWrapper(HttpServletResponse response) {
            super(response);
        }
    }
}
