/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Web.DTO;

import Domain.User;
import Domain.UserRole;

/**
 *
 * @author azurwular
 */
public class UserSessionDto
{
    private int id;
    private String email;
    private UserRole role;
    
    public UserSessionDto(User user)
    {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public UserRole getRole()
    {
        return role;
    }

    public void setRole(UserRole role)
    {
        this.role = role;
    }
    
    
}
