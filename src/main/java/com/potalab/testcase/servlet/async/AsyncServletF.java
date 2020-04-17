package com.potalab.testcase.servlet.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns="/AsyncServletF", asyncSupported = true)
public class AsyncServletF extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServletF.class);

    public AsyncServletF() {

    }

    private static final int REPEAT = 10000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.debug("Debug >>> 4시 6분");

        AsyncContext context = request.startAsync();
        ServletOutputStream output = response.getOutputStream();
        context.setTimeout(1000);
        logger.debug("Debug >>> A"); // 🅐
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                logger.debug("Debug >>> B"); // 🅑
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                logger.debug("Debug >>> C"); // 🅒
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });

        AtomicInteger atomicInteger = new AtomicInteger(0);

        output.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {

                logger.debug("Debug >>> D"); // 🅓
                output.write(getHtml(atomicInteger.get()).getBytes());
                logger.debug("Debug >>> E"); // 🅔

//                waitFor(3000);
//
//                while (true) {
//                    if ( output.isReady()) {
//                        atomicInteger.set(REPEAT); // next start index ...
//                        output.write(getHtml(atomicInteger.get()).getBytes());
//                        break;
//                    }
//                }
//
                waitFor(3000);
//                context.complete();
            }

            @Override
            public void onError(Throwable throwable) {
                logger.debug("Debug >>> onError(...)");
            }
        });

        logger.debug("Debug >>> F"); // 🅕
    }

    public void waitFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch(Exception e) {

        }
    }

    public String getHtml(int start) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <title>Hello World!</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<pre>");
        html.append("</pre>");
        for (int i = start + 1; i <= start + REPEAT; i++) {
            html.append("hello world : " + i + "<br>");
        }
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }
}