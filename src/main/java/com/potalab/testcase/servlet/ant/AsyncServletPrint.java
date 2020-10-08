package com.potalab.testcase.servlet.ant;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/ant/async/print", asyncSupported = true)
public class AsyncServletPrint extends HttpServlet {
    public AsyncServletPrint() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AsyncContext context = request.startAsync();
        context.setTimeout(3000);
        ServletOutputStream output = response.getOutputStream();
        context.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {
                System.out.println("complete!!!");
            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                System.out.println("---- async timeout ----");
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                System.out.println("---- async error ----");
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });
        output.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {
              PrintWriter writer = response.getWriter();
              writer.print(getHtml());
              context.complete();
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("---- write error ----");
                System.out.println(throwable.toString());
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
        for (int i = 1; i <= 10; i++) {
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

