package com.potalab.wafull.sample;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcConnPoolExam extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        resp.setHeader("keep-alive", "close");
        resp.setContentType("text/html;charset=UTF-8");
        ServletOutputStream os = resp.getOutputStream();

        os.println("<html>");
        os.println("<head>");
        os.println("<title>Wafull sample page by PotaLab Inc.</title>");
        os.println("</head>");
        os.println("<body>");


        DataSource ds = null;

        try{
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env");
            ds = (DataSource)envCtx.lookup("testDB");
        } catch(Exception e){
            e.printStackTrace();
        }

        Connection connection = null;
        try {

            connection = ds.getConnection();

            os.println(String.format("<h1>JDBC Test Page catalog: %s <h1>",
                connection.getCatalog()));

            String sql = "SELECT * FROM tb_dept";

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            os.println("<table border=\"1\">");
            os.println("<tbody>");

            while(rs.next()) {

                String data = String.format("<tr><td>id</td><td>%s</td><td>dept_cd</td><td>%s</td" +
                    "><td>dept_nm</td><td>%s</td><td>description</td><td>%s</td></tr>",
                    rs.getInt("id"), rs.getString("dept_cd"),
                    rs.getString("dept_nm"), rs.getString("description"));

                os.write(data.getBytes("UTF-8"));

            }

            os.println("</tbody>");
            os.println("</table>");

        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            if ( connection != null ) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        os.println("</body>");
        os.println("</html>");
        os.flush();


    }
}
