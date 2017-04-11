import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by akhil on 11/4/17.
 */
public class ConnectionPool {
    private static Connection connection;
    private static String url;
    private static String dbName;
    private static String driver;
    private static String userName;
    private static String password;
    static {
          url = "jdbc:mysql://localhost:3306/";
        dbName = "servletdb";
        driver = "com.mysql.jdbc.Driver";
        userName = "root";
         password = "root";
    }

    static Connection getConnection() throws Exception{
        Class.forName(driver).newInstance();
        connection= DriverManager.getConnection(url+dbName,userName,password);
        return connection;
    }

    static void closeConnection(){
        try {
            connection.close();
        }
        catch (Exception e){

        }
    }

}
