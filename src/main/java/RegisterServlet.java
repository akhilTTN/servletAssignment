
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by akhil on 27/3/17.
 */
public class RegisterServlet extends HttpServlet {
    PrintWriter out;
    RequestDispatcher rd;
    private String conString = "jdbc:mysql://localhost:3306/servletdb";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        out = resp.getWriter();
        int ctr = 0;
        String first_name, last_name, unames, pass1, pass2, email;
        first_name = req.getParameter("fname");
        last_name = req.getParameter("lname");
        unames = req.getParameter("uname");
        pass1 = req.getParameter("pass1");
        pass2 = req.getParameter("pass2");
        if (!last_name.equals("") && !first_name.equals("") && !unames.equals("") && !pass1.equals("") && !pass2.equals("") && first_name != null && unames != null && pass1 != null && last_name != null && pass2 != null ) {
            System.out.println("inside null check");
            if (pass1.equals(pass2)) {
                System.out.println("inside password check");
                try {
                    System.out.println("inside uname check");
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(conString, "root", "root");
                    Statement stmt = con.createStatement();
                    String q1 = "select * from registration";
                    ResultSet rs = stmt.executeQuery(q1);
                    while (rs.next()) {
                        if (unames.equals(rs.getString("uname"))) {
                            rd = req.getRequestDispatcher("register.html");
                            /*out.print("<script>alert('this username is already registered. choose another')</script>");
                            out.print("<script>alert('this username is already registered. choose another')</script>");*/
                            out.print("<p> Username "+ unames+" is already taken. If this is you then go to login else choose another Username");
                            ctr = 1;
                            rd.include(req, resp);
                            break;
                        }
                    }
                    if (ctr == 0) {
                        System.out.println("inside insert query");
                        stmt.executeUpdate("insert into registration(fname,lname,username,password) values('" + first_name + "','"+ last_name + "','" + unames + "','"+ pass1 + "');");
                        rd = req.getRequestDispatcher("welcome.html");
                        rd.forward(req, resp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("inside password mismatch");
                rd = req.getRequestDispatcher("register.html");
                String errorMsg = "<p style='color: red;'> Passwords do not match </p>";
                out.print(errorMsg);
                rd.include(req, resp);
            }
        }
        else {
            System.out.println("inside invalid entries");
            rd = req.getRequestDispatcher("register.html");
            String errorMsg = "<p style='color: red;'> Invalid Entries </p>";
            out.print(errorMsg);
            rd.include(req, resp);
        }
    }
}
