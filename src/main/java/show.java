import a.a.ap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhil on 3/4/17.
 */
public class show extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List list = new ArrayList();
        RequestDispatcher rd;
        HttpSession session = req.getSession(false);
        if (session != null) {
            String name = (String) session.getAttribute("uname");

            try {
                Connection con = ConnectionPool.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from blog where name='" + name + "';");
                while (rs.next()) {
                    list.add(rs.getString("id"));
                    list.add(rs.getString("name"));
                    list.add(rs.getString("content"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                ConnectionPool.closeConnection();
            }
            session.setAttribute("data", list);
            rd = req.getRequestDispatcher("display.jsp");
            rd.include(req, resp);
        }
        else {
                rd=req.getRequestDispatcher("login.html");
                rd.forward(req,resp);
        }
    }
}
