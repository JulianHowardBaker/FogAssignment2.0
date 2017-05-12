/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import DAL.Repositories.UserRepository;
import Web.DTO.ValidationResult;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validation methods for user
 *
 * @author azurwular
 */
public class UserValidator
{
    private final UserRepository userRepository;
    
    public UserValidator()
    {
        this.userRepository = new UserRepository();
    }
    /**
     * Validates a user signup
     * @param email
     * @param password
     * @param passwordConfirmation
     * @return 
     * @throws java.sql.SQLException 
     */
    public ValidationResult ValidateSignup(String email, String password, String passwordConfirmation) throws SQLException
    {
        ValidationResult result = new ValidationResult();
        
        if (email == null || email.isEmpty())
        {
            result.errors.put("email", "Email can't be empty");
        }
        else
        {
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches())
            {
                result.errors.put("email", "Email is not valid");
            }
            else
            {
                // Check if user already exists
                if (this.userRepository.Exists(email))
                {
                    result.errors.put("email", "The user already exists");
                }
            }
        }
        
        if (password == null || password.isEmpty())
        {
            result.errors.put("password", "Password can't be empty");
        }
        
        if (passwordConfirmation == null || passwordConfirmation.isEmpty())
        {
            result.errors.put("passwordConfirmation", "Password confirmation can't be empty");
        }
        
        if (password != null && passwordConfirmation !=null && !password.equals(passwordConfirmation))
        {
            result.errors.put("password", "Password doesn't match password confirmation");
        }
        
        if (result.errors.isEmpty())
        {
            result.isValid = true;
        }
        
        return result;
    }
    
    /**
     * Validates a user login
     * @param email
     * @param password
     * @return 
     */
    public ValidationResult ValidateLogin(String email, String password)
    {
        ValidationResult result = new ValidationResult();
        
        if (email == null || email.isEmpty())
        {
            result.errors.put("email", "Email can't be empty");
        }
        
        if (password == null || password.isEmpty())
        {
            result.errors.put("password", "Password can't be empty");
        }
        
        if (result.errors.isEmpty())
        {
            result.isValid = true;
        }
        
        return result;
    }
    
    
    public ValidationResult ValidateEdit(String email)
    {
        ValidationResult result = new ValidationResult();
        
        if (email == null || email.isEmpty())
        {
            result.errors.put("email", "Email can't be empty");
        }
        
        else
        {
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches())
            {
                result.errors.put("email", "Email is not valid");
            }
            else
            {
                try
                {
                    // Check if user already exists
                    if (this.userRepository.Exists(email))
                    {
                        result.errors.put("email", "The user already exists");
                    }
                } catch (SQLException ex)
                {
                    Logger.getLogger(UserValidator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return result;
    }
}