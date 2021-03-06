/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Web;

import DAL.Repositories.UserRepository;
import Domain.User;
import Services.UserValidator;
import Web.DTO.SessionKeys;
import Web.DTO.UserSessionDto;
import Web.DTO.ValidationResult;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 *
 * @author azurwular
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginServlet extends BaseServlet
{
    private final UserValidator userValidator;
    private final UserRepository userRepository;
    
    public LoginServlet()
    {
        this.userValidator = new UserValidator();
        this.userRepository = new UserRepository();
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        super.forward("/user/login.jsp", request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // Get username and password from request
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Validate request
        ValidationResult validationResult = this.userValidator.ValidateLogin(email, password);
        
        if (!validationResult.isValid)
        {
            // TODO: Find a way to send errors back
            response.sendRedirect("/login");
        }
        
        // Get user from repository
        User user = null;
        
        try
        {
            // Check if the user exists in the repository
            user = this.userRepository.Get(email, password);
        } catch (Exception ex)
        {
            response.sendRedirect("/error");
        }
        
        if (user == null)
        {
            response.sendRedirect("/login");
        }
        
        // At this point the user exists so we store him to the session
        HttpSession session = request.getSession();
        UserSessionDto sessionUser = new UserSessionDto(user);
        session.setAttribute(SessionKeys.user, user);
        
        // Redirect to front page
        response.sendRedirect("/");
    }
}