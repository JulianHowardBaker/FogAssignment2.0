/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL.Repositories;


import DAL.DataAccessObject;
import Domain.User;
import Domain.UserRole;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Repository for users
 * @author azurwular
 */
public class UserRepository
{
    /**
     * Gets user from database
     * @param email
     * @param password
     * @return
     * @throws SQLException
     * @throws NullPointerException 
     */
    public User Get(String email , String password) throws SQLException, NullPointerException, Exception 
    {
        // Hash the password
        String hashedSaltedPassword = PasswordUtils.getSaltedHash(password);
        
        // Query for the user
        PreparedStatement insertStatement;
        ResultSet rs;
        User user = null;
        String insertSQL = "SELECT * from user where email = ? AND password = ?";
        
        Connection connection = DataAccessObject.getConnection();
        
        insertStatement = connection.prepareStatement(insertSQL);
        insertStatement.setString(1, email);
        insertStatement.setString(2, hashedSaltedPassword);

        rs = insertStatement.executeQuery();

        if (rs.next())
        {
            user = new User(
                    rs.getInt("id"),
                    UserRole.valueOf(rs.getString("role")),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address"),
                    rs.getString("zipcode"),
                    rs.getString("city"),
                    rs.getString("country"));
        }
        
        DataAccessObject.releaseConnection(connection);
        
        return user;
    }

    public void Create(User newUser) throws SQLException
    {
        PreparedStatement insertStatement;
        ResultSet rs;
        
        String sql = "SELECT * from user where email = "+email;
        if (sql == null)
        {
            try {
                String insertSQL = "insert into user(firstName,lastName,email,phone,password,) values(?,?,?,?,?)";
                insertStatement = con.prepareStatement(insertSQL);
                insertStatement.setString(1, firstName);
                insertStatement.setString(2, password);
                insertStatement.setString(3, email);
                insertStatement.setString(4 , phone);
                insertStatement.setString(5 , password);


                insertStatement.executeUpdate();//result set stores the results of the query
                System.out.println("You have created a new user");
            } catch (SQLException e) {
                System.out.println("Fail in create new user. Check code");
                System.out.println(e.getMessage());

            }

        }
        else
        {
            System.out.println("User exists");
        }
    }
    /*
    public Bottom getBottom (String bottom)
    {
        try {
            String query = "select name, price from bottoms where name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, bottom);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            Bottom dbBottom;
            
            while(resultSet.next())
            {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                
                dbBottom = new Bottom(name, price);
                
                return dbBottom;
            }
        } catch (SQLException e) {
            System.out.println("Fail to query topping.");
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public Topping getTopping (String topping)
    {
        try {
            String query = "select name, price from topping where name = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, topping);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            Topping dbTopping;
            
            while(resultSet.next())
            {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                
                dbTopping = new Topping(name, price);
                
                return dbTopping;
            }
        } catch (SQLException e) {
            System.out.println("Fail to query topping.");
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    public int createInvoice (Invoice invoice)
    {
        PreparedStatement insertStatement;
        try {
            String insertSQL = "insert into invoices(username) values(?)";
            insertStatement = con.prepareStatement(insertSQL);
            insertStatement.setString(1, invoice.getUsername());

            insertStatement.executeUpdate(insertSQL, 1);
            return insertStatement.getGeneratedKeys().getInt("id");
        } catch (SQLException e) {
            System.out.println("Fail in create new invoice. Check code");
            System.out.println(e.getMessage());
        }
        
        return -1;
    }
    
    public void addLineItems (ArrayList<LineItem> lineItems)
    {
        try {
            String insertSQL = "insert into lineitems(topping, bottom, quantity, username, invoiceId) values(?,?,?,?,?)";
            PreparedStatement insertStatement = con.prepareStatement(insertSQL);
            
            for(LineItem lineItem : lineItems)
            {
                insertStatement.setString(1, lineItem.getTopping().getName());
                insertStatement.setString(2, lineItem.getBottom().getName());
                insertStatement.setInt(3, lineItem.getQuantity());
                insertStatement.setString(4, lineItem.getUsername());
                insertStatement.setInt(5, lineItem.getInvoiceId());
                insertStatement.addBatch();
            }

            insertStatement.executeBatch();
            System.out.println("Line items created");
        } catch (SQLException e) {
            System.out.println("Fail in creating line items. Check code");
            System.out.println(e.getMessage());
        }
    }
*/
}
