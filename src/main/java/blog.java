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
public class blog extends HttpServlet {
    private String conString = "jdbc:mysql://localhost:3306/servletdb";
    RequestDispatcher rd;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sessions = req.getSession(false);
        String name = (String) sessions.getAttribute("uname");
        PrintWriter out = resp.getWriter();
//        out.println("<script>alert("+name+")</script>");
        resp.setContentType("text/html");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(conString, "root", "root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select max(id) from blog where name ='" + name + "';");
            while (rs.next()) {
                if (rs.getString("max(id)") == null) {
                    rd = req.getRequestDispatcher("temp.html");
                    out.println("<strong> Welocme " + name + "</strong>");
                    out.println("Your last blog id was 0. This is your first Blog. keep your blog id as one. <br><br> Happby Blogging!!");
                    out.println("<input type='button' onclick=\"location.href='blog.html'\" value='click'/> here to go to the BLOG page");
                    rd.include(req, resp);
                } else {
                    rd = req.getRequestDispatcher("temp.html");
                    out.println("<strong> Welocme2 " + name + "</strong>");
                    out.println("Your last blog id was " + rs.getInt("max(id)") + ". Keep your blog id as " + (rs.getInt("max(id)")+1) + ". <br><br> Happby Blogging!!");
                    out.println("<input type='button' onclick=\"location.href='blog.html'\" value='click'/> here to go to the BLOG page");
                    rd.include(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}