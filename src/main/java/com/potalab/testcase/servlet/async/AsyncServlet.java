package com.potalab.testcase.servlet.async;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Servlet Spec. 3.0 에서 정의된 내용으로만 Async Servlet 을 구현한다. 즉 NIO 관련된 리스너를 사용
 * 하지 않는다는 뜻....
 *
 * 비동기를 시작하고 새로운 스레드를 만들어서 그 스레드 내에서 complete 메소드를 호출하여 페이지가
 * 정상으로 출력되는 것을 확인한다.
 */
@WebServlet(value = "/AsyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(AsyncServlet.class);

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
        throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(3000); //3초 이 내에 타 스레드작업을 기다린다.
        asyncContext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {

            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                asyncContext.complete(); // Tomcat은 이 코드가 없어도 문제가 안 되나,
                                         // Jetty 그렇지 않다.
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {

            }
        });

        ServletOutputStream sos = response.getOutputStream();
        AtomicReference<IOException> exceptionStock = new AtomicReference<>(null);
        asyncContext.start(()->{

            logger.debug("Current thread name >>> {}", Thread.currentThread().getName() );

            try {
                sos.write(getHtml().getBytes());
            } catch (IOException e) {
                exceptionStock.set(e);
            }
            asyncContext.complete();
        });

        IOException e;
        if ((e = exceptionStock.get()) != null ) {
            throw e;
        }
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
        html.append("First Async Test !!!" );
        html.append("</h1>");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }


}
