<%--
  Created by IntelliJ IDEA.
  User: akhil
  Date: 3/4/17
  Time: 12:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>
<head>
    <title>Blogging</title>
</head>
<body>

</body>
</html>--%>

<%@page contentType="text/html;charset=UTF-8" language="java" import="java.util.*" %>
<html>
<head>
    <title>Data Page</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <style>
        .custab{
            border: 1px solid #ccc;
            padding: 5px;
            margin: 5% 0;
            box-shadow: 3px 3px 2px #ccc;
            transition: 0.5s;
        }
        .custab:hover{
            box-shadow: 3px 3px 0px transparent;
            transition: 0.5s;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row col-md-6 col-md-offset-2 custyle">
<table class="table table-striped custab">
    <tr>
        <td><b>ID</b></td>
        <td><b>Name</b></td>
        <td><b>Content</b></td>
    </tr>
    <%Iterator itr;%>
    <%if(session.getAttribute("uname")!=null){
        List data= (List)session.getAttribute("data");
        for (itr=data.iterator(); itr.hasNext(); )
        {
    %>
    <tr>
        <td><%=itr.next()%></td>
        <td><%=itr.next()%></td>
        <td><%=itr.next()%></td>
    </tr>
    <%}}else {
        RequestDispatcher rd= request.getRequestDispatcher("login.html");
        rd.forward(request,response);
    }%>
</table>
    </div>
</div>
</body>
</html>
