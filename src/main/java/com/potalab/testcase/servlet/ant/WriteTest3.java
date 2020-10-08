package com.potalab.testcase.servlet.ant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

@WebServlet(urlPatterns = "/ant/write3", asyncSupported = true)
public class WriteTest3 extends HttpServlet {
    public WriteTest3() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        Writer w = response.getWriter();

        w.write("<html>");
        // <meta charset="UTF-8">
        w.write("<head><meta charset=\"UTF-8\"></head>");
        w.write("<body>");
        w.write("<h1>Test3</h1>");
        w.write("<table border=1>");

        for (int i = 0x10000; i < 0x11000; i++) {
            w.write("<tr>\n");
            w.write("<td>0x" + Integer.toHexString(i) + "</td>\n");
            char[] chars = Character.toChars(i);
            w.write("<td>");
            for(char c : chars) {
                w.write(c);
            }
            w.write("</td>");
            w.write("<td>length : " + chars.length + "</td>\n");
            w.write("</tr>\n");
        }
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");
    }
}

