/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Web.Filters;

import Domain.UserRole;
import Web.DTO.SessionKeys;
import Web.DTO.UserSessionDto;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A filter to be used by servlets that only allow sales people in
 */
@WebFilter("/SalesUserOnlyFilter")
public class SalesUserOnlyFilter implements Filter
{
    private ServletContext context;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        // Get session from request
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        
        // Get the current user from the session
        UserSessionDto currentUser = (UserSessionDto) session.getAttribute(SessionKeys.user);
        
        // If the user is not a warehouse person, redirect to home page
        if (!currentUser.getRole().equals(UserRole.SalesPerson))
        {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("/");
        }
    }

    @Override
    public void destroy()
    {
    }
}