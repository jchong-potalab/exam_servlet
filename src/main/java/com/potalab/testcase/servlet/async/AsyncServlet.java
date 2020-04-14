package com.potalab.testcase.servlet.async;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(value = "/AsyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

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
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter output = response.getWriter();
        output.println("<html>");
        output.println("<head>");
        output.println("<title>Reading asynchronously</title>");
        output.println("</head>");
        output.println("<body>");
        output.println("<h1>Reading asynchronously</h1>");
        output.println("<form action=\"?\" method=\"post\" name=\"testAsync\">");
        output.println("	<input type=\"text\" name=\"text1\" value=\"금강산\" size=\"30\">");
        output.println("	<input type=\"text\" name=\"text2\" value=\"백두산\" size=\"30\">");
        output.println("	<input type=\"submit\" value=\"submit\">");
        output.println("</form>");


        AsyncContext context = request.startAsync(request, response);
        System.out.println("Debug 1 >>> " + context.hasOriginalRequestAndResponse());

        ServletInputStream input = request.getInputStream();
        input.setReadListener(new ReadingListener(input, context));

        output.println("</body>");
        output.println("</html>");
        output.flush();
        context.start(()->{

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Debug 2 >>> " + context.hasOriginalRequestAndResponse());
            output.println(String.format("<h3>In thread Process !!! request is %s, response is %s</h3>"
                , context.getRequest().getClass().getName()
                , context.getResponse().getClass().getName()));

            context.complete();

        });

    }


    private static class ReadingListener implements ReadListener {

        private ServletInputStream input = null;
        private AsyncContext context = null;

        public ReadingListener(ServletInputStream in, AsyncContext ac) {
            this.input = in;
            this.context = ac;
        }

        @Override
        public void onDataAvailable() {
            try {
                int len = -1;
                byte[] b = new byte[1024];
                while (input.isReady() && (len = input.read(b)) != -1) {
                    String data = new String(b, 0, len);
                    System.out.print("--> " +data);
                }
            } catch (IOException ex) {
                //logger.log(SEVERE, null, ex);
            }
        }

        @Override
        public void onAllDataRead() {

        }

        @Override
        public void onError(Throwable t) {
            //logger.log(SEVERE, "onError executed", t);
            context.complete();
        }
    }
}
