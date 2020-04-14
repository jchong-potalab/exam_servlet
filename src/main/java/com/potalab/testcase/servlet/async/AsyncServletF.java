package com.potalab.testcase.servlet.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns="/AsyncServletF", asyncSupported = true)
public class AsyncServletF extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(AsyncServletF.class);

    public AsyncServletF() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AsyncContext context = request.startAsync();
        ServletOutputStream output = response.getOutputStream();
        context.setTimeout(30000);
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

        output.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {
                logger.debug("Debug >>> D"); // 🅓
                output.write(getHtml().getBytes());
                logger.debug("Debug >>> E"); // 🅔
            }

            @Override
            public void onError(Throwable throwable) {

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

    public String getHtml() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <title>Hello World!</title>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<pre>");
        html.append("</pre>");
        for (int i = 1; i <= 10000; i++) {
            html.append("hello world : " + i + "<br>");
        }
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }
}