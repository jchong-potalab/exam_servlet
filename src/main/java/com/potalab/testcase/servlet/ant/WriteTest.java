package com.potalab.testcase.servlet.ant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet(urlPatterns = "/ant/write", asyncSupported = true)
public class WriteTest extends HttpServlet {
    public WriteTest() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        //sb.append("a");

        for (int i = 0x10000; i < 0x11000; i++) {
            char[] chars = Character.toChars(i);
            sb.append(chars);
        }
        String data = sb.toString();

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        Writer w = response.getWriter();

        char[] chars = data.toCharArray();

        w.write("<html>");
        // <meta charset="UTF-8">
        w.write("<head><meta charset=\"UTF-8\"></head>");
        w.write("<body>");
        w.write("<table border=1>");

        for (int i = 0; i < chars.length; i++) {
            w.write("<tr>\n");
            w.write("<td>" + i + "</td>\n");
            w.write("<td>0x" + Integer.toHexString((i + 0x10000)) + "</td>\n");
            w.write("<td>");
            w.write(chars[i]);
            w.write("</td>");
            w.write("</tr>\n");
        }
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");
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

