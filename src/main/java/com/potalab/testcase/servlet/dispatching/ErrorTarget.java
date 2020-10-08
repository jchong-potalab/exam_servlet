package com.potalab.testcase.servlet.dispatching;

import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/dispatching/error")
public class ErrorTarget extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        RequestDispatcher rd = request.getRequestDispatcher("/dispatching/forward");
//        rd.forward(request, response);

        PrintWriter pw = response.getWriter();
        pw.print(get404Html(request.getDispatcherType()));
    }

    private String get404Html(DispatcherType dispatcherType) {

        return "<!DOCTYPE html>\r\n"
                + "<html lang=\"en\">\r\n"
                + "<head>\r\n"
                + "	<meta charset=\"utf-8\">\r\n"
                + "	<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n"
                + "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
                + "	<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->\r\n"
                + "	<title>404 HTML Template by Colorlib</title>\r\n"
                + "	<!-- Google font -->\r\n"
                + "	<link href=\"https://fonts.googleapis.com/css?family=Cabin:400,700\" rel=\"stylesheet\">\r\n"
                + "	<link href=\"https://fonts.googleapis.com/css?family=Montserrat:900\" rel=\"stylesheet\">\r\n"
                + "	<!-- Custom stlylesheet -->\r\n"
                + "	<link type=\"text/css\" rel=\"stylesheet\" href=\"../css/style.css\" />\r\n"
                + "	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->\r\n"
                + "	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->\r\n"
                + "	<!--[if lt IE 9]>\r\n"
                + "		  <script src=\"https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js\"></script>\r\n"
                + "		  <script src=\"https://oss.maxcdn.com/respond/1.4.2/respond.min.js\"></script>\r\n"
                + "		<![endif]-->\r\n"
                + "</head>\r\n"
                + "<body>\r\n"
                + "	<div id=\"notfound\">\r\n"
                + "		<div class=\"notfound\">\r\n"
                + "			<div class=\"notfound-404\">\r\n"
                + "				<h3>Oops! Page not found</h3>\r\n"
                + "				<h1><span>4</span><span>0</span><span>4</span></h1>\r\n"
                + "			</div>\r\n"
                + "			<h2>we are sorry, but the page you requested was not found</h2>\r\n"
                + "			<h3>Dispatcher type is "+ dispatcherType +"</h3>\r\n"
                + "		</div>\r\n"
                + "	</div>\\\r\n"
                + "</body><!-- This templates was made by Colorlib (https://colorlib.com) -->\r\n"
                + "</html>\r\n";
    }

}
