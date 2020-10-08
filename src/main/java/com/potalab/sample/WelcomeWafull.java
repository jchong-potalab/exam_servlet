package com.potalab.sample;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeWafull extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        String opCode = req.getParameter("opcode");

        if ( opCode != null && opCode.equals("INCLUDE") ) {
            RequestDispatcher rd = req.getRequestDispatcher("/servlet/example/IncludeTarget");
            rd.include(req, resp);
        }

        if ( opCode != null && opCode.equals("FORWARD") ) {
            RequestDispatcher rd = req.getRequestDispatcher("/servlet/example/ForwardTarget");
            rd.forward(req, resp);
        }

        if ( opCode != null && opCode.equals("ERROR") ) {
            resp.sendError(404, "test error message - page not found.");
        }

        if ( opCode != null && opCode.equals("ERROR2") ) {
            resp.sendError(503, "test error message - page not found.");
        }

        if ( opCode != null && opCode.equals("EXCEPT") ) {
            throw new RuntimeException("exception for test.....");
        }


        resp.setContentType("text/html;charset=UTF-8");

        ServletOutputStream os = resp.getOutputStream();
        os.write("<html>".getBytes());
        os.write("<head>".getBytes());
        os.write("<title>Wafull sample page by PotaLab Inc.</title>".getBytes());
        os.write("</head>".getBytes());
        os.write("<body>".getBytes());
        os.write("<h1>Hello WAFULL world !!!</h1>".getBytes());
        os.write("</body>".getBytes());
        os.write("</html>".getBytes());
    }
}
