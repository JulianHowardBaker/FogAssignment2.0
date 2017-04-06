
package DataAccessLayer;

import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Julian
 */
public class DataMapper {
    private final  Connection con;
      public DataMapper() {//instantiates a new connection whenever the DataMapper class is instatiated
       
       con = new DataAccessObject().getConnection();
    }
 public void createUser(String username , String password) throws SQLException{
 PreparedStatement insertStatement;
 ResultSet rs;
 try{
 String createUser = "insert into user(username,password) values (?,?)";
 insertStatement  = con.prepareStatement(createUser);
insertStatement.setString(1,username );
insertStatement.setString(2,password);
insertStatement.executeUpdate();//result set stores the results of the query
System.out.println("A new user has been created");
  
 
 
 }
 catch(SQLException e){System.out.println("There is an error in the createUser method  - please check it");
                                    System.out.println(e.getMessage());
                                    }
 
 }
 
                                    
 
    
           
 public User checkUser(String username, String password) throws SQLException, NullPointerException {
        PreparedStatement insertStatement;
        ResultSet rs;
        User user = null;
        String insertSQL = "SELECT * from user where username = ?  and password =?";
        insertStatement = con.prepareStatement(insertSQL);
        insertStatement.setString(1, username);
        insertStatement.setString(2,password);

        rs = insertStatement.executeQuery();//result set stores the results of the query
        System.out.println("You are checking for a new user");
        if (rs.next()) {
            System.out.println("this method is working after rs.next");
            user = new User(rs.getString("username"), rs.getString("password"));

   }
      return user;

    }

}
