package com.potalab.testcase.servlet.async;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 비동기 모드 열어놓고 Forward 를 하면 일어나는 현상을 검증한다.
 *
 */
@WebServlet(value = "/AsyncServletAsyncStartAndForward", asyncSupported = true)
public class AsyncServletAsyncStartAndForward extends HttpServlet {

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
        throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(3000);
//        asyncContext.addListener(new AsyncListener() {
//            @Override
//            public void onComplete(AsyncEvent event) throws IOException {
//
//            }
//
//            @Override
//            public void onTimeout(AsyncEvent event) throws IOException {
//                asyncContext.complete(); // Tomcat은 이 코드가 없어도 문제가 안 되나,
//                                         // Jetty 그렇지 않다.
//            }
//
//            @Override
//            public void onError(AsyncEvent event) throws IOException {
//
//            }
//
//            @Override
//            public void onStartAsync(AsyncEvent event) throws IOException {
//
//            }
//        });

        RequestDispatcher requestDispatcher
            = request.getRequestDispatcher("/SyncServletTarget");
        requestDispatcher.forward(request, response);
        ServletOutputStream sos = response.getOutputStream();
//        AtomicReference<IOException> exceptionStock = new AtomicReference<>(null);
//        asyncContext.start(()->{
//            try {
//                sos.write(getHtml().getBytes());
//            } catch (IOException e) {
//                exceptionStock.set(e);
//            }
//            asyncContext.complete();
//        });
//
//        IOException e;
//        if ((e = exceptionStock.get()) != null ) {
//            throw e;
//        }

        sos.write(getHtml().getBytes());

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
        html.append("First Async Start and Forward Test!!!" );
        html.append("</h1>");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }


}
