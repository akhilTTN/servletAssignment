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
import java.sql.Statement;

/**
 * Created by akhil on 27/3/17.
 */
public class save extends HttpServlet {
    PrintWriter out;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher re;
        out = resp.getWriter();
        resp.setContentType("text/html");
        HttpSession session = req.getSession(false);
        String content, name;
        String id;
        content = req.getParameter("tarea");
        id = req.getParameter("id");
        if (session!=null) {
            name = (String) session.getAttribute("uname");
            if (content != null && !content.equals("") && id != null && !id.equals("")) {
                int ids = Integer.parseInt(id);
                try {
                    Connection con = ConnectionPool.getConnection();
                    Statement stmt = con.createStatement();
                    stmt.executeUpdate("insert into blog values('" + ids + "','" + name + "','" + content + "');");
                    re = req.getRequestDispatcher("/blog.html");
                    out.println("<script>alert('Blog data inserted successfully')</script>    ");
                    re.include(req, resp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    ConnectionPool.closeConnection();
                }
            } else {
                re = req.getRequestDispatcher("blog.html");
                out.print("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js\"></script><script> $(document).ready(function(){$('#error').css('display', 'block') });</script>");
                re.include(req, resp);
            }
        } else {
            re = req.getRequestDispatcher("login.html");
            re.forward(req, resp);
        }
    }
}
