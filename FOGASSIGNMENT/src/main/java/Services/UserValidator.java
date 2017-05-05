/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Web.DTO.ValidationResult;

/**
 * Validation methods for user
 *
 * @author azurwular
 */
public class UserValidator
{
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
}