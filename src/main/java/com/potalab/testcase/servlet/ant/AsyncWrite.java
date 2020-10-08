package com.potalab.testcase.servlet.ant;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/async/write11", asyncSupported = true)
public class AsyncWrite extends HttpServlet {
    static StringBuilder html = new StringBuilder();
    static {
        html.append("<html>\n" +
                "  <head>\n" +
                "    <title></title>\n" +
                "  </head>\n" +
                "  <body>\n");
        for (int i = 0; i < 1000000; i++) {
            html.append(i +":    Hello~~~~~~~~~~~~~~~~\n");
        }
        html.append("  </body>\n" + "</html>");
    }

    final static byte[] byteHtml = html.toString().getBytes();

    public AsyncWrite() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AsyncContext context = request.startAsync();
        response.setContentType("aa/bb");
        ServletOutputStream output = response.getOutputStream();
        output.setWriteListener(new WriteListener() {
            @Override
            public void onWritePossible() throws IOException {
                if (output.isReady()) {
                    output.write(byteHtml);
                    context.complete();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                context.complete();
            }
        });
    }
}

