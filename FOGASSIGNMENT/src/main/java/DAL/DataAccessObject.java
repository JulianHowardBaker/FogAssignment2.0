package DAL;

import java.sql.Connection;
import java.sql.DriverManager;


public final class DataAccessObject {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/fog";
    private static final String id = "root";
    private static final String pw = "root";

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(URL, id, pw);  // The connection will be released upon program 
            System.out.println("You have successfully connected to " + URL + " database");

        } catch (Exception e) {
            System.out.println("\n*** Remember to insert your  ID and PW in the DBConnector class! ***\n");
            System.out.println("error in DBConnector.getConnection()");
            System.out.println(e);
        }

        return con;
    }

    public static void releaseConnection(Connection con) {
        try {
            con.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}

