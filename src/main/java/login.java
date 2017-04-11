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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessions=req.getSession(true);
        out=resp.getWriter();
        String uname,pass;
        int ctr=0;
        uname=req.getParameter("name");
        pass=req.getParameter("pass");
        try{
            Connection con= ConnectionPool.getConnection();
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select count(*) from registration where username='"+uname+"' and password='"+pass+"';");
            while(rs.next()){
                if(rs.getString(1).equals("1")){
                    sessions.setAttribute("uname",uname);
                    rd=req.getRequestDispatcher("/blog");
                    rd.forward(req,resp);
                    ctr=1;
                    break;
                }
            }
            if(ctr==0){
                rd=req.getRequestDispatcher("login.html");
                out.print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script><script> $(document).ready(function(){$('#error').css('display', 'block') });</script>");
                rd.include(req,resp);
            }
        }
        catch(Exception e){
        e.printStackTrace();
        }
        finally {
            ConnectionPool.closeConnection();
        }

    }
}
