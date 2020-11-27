<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Wafull sample page by PotaLab Inc.</title>
</head>
<body>
<h1>Hello WAFULL world !!! - this page is JSP Sample.</h1>
<%
    session.setAttribute("my_name", "JI-CHOEL HONG");
    out.print(String.format("<h1>%s</h1>", session.getAttribute("my_name")));
%>
<table>
    <%
        for(int i=0; i<1000;i++){%>
    <tr>
        <td>Count</td>
        <td><%= i %></td>
    </tr>
    <%}%>
</table>
</body>
</html>