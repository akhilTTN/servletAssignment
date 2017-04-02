import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by akhil on 27/3/17.
 */
public class login extends HttpServlet {
    RequestDispatcher rd;
    PrintWriter out;
    private String conString = "jdbc:mysql://localhost:3306/servletdb";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessions=req.getSession(true);
        out=resp.getWriter();
        String uname,pass;
        int ctr=0;
        uname=req.getParameter("name");
        pass=req.getParameter("pass");
        String db=getServletContext().getInitParameter("dbString");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(conString,"root","root");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select username,password from registration");
            while(rs.next()){
                if(uname.equals(rs.getString("username")) && pass.equals(rs.getString("password"))){
                    sessions.setAttribute("uname",uname);
                    rd=req.getRequestDispatcher("/blog");
                    rd.forward(req,resp);
                    ctr=1;
                    break;
                }
            }
            if(ctr==0){
                rd=req.getRequestDispatcher("login.html");
//                out.print("<p style='color:red'; margin: 500px> please register first </p>");
                out.print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script><script> $(document).ready(function(){$('#error').css('display', 'block') });</script>");
                rd.include(req,resp);
            }
        }
        catch(Exception e){
        e.printStackTrace();
        }

    }
}
