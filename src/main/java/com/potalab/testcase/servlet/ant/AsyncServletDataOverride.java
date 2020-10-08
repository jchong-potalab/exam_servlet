package com.potalab.testcase.servlet.ant;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(urlPatterns = "/ant/async/over", asyncSupported = true)
public class AsyncServletDataOverride extends HttpServlet {
    public AsyncServletDataOverride() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AsyncContext context = request.startAsync();
        ServletOutputStream output = response.getOutputStream();
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("complete!!!");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {

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
                byte[] data = getHtml().getBytes();
                output.write(data);
                Arrays.fill(data, (byte) 'A');
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
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
        for (int i = 1; i <= 1000000; i++) {
            html.append(i);
            html.append(" : ");
            html.append("hello world : " + i + "<br>");
        }
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }


    public void waitFor(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
        }
    }
}

