<%-- 
    Document   : signup
    Created on : Apr 6, 2017, 11:39:03 AM
    Author     : petermihok
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="Web.DTO.SessionKeys"%>
<%@page import="Web.DTO.UserSessionDto"%>


<html>
<head>
  <title>Edit Profile</title>
  
  <%@include file="/views/partial/header.jsp" %>
  

  
</head>

<body>

  <%@include file="/views/partial/nav.jsp" %>

  <div class="form">
      
      <div class="tab-content">
        <div id="signup">   
          <h1>Edit Profile</h1>
          
          <form action="/user/edit" method="post">
          
          <div class="top-row">
            <div class="field-wrap">
              <label>
                First Name<span class="req"></span>
              </label>
              <input type="text" name="firstName" value="<%= sessionUser.getFirstName() %>" autocomplete="off" />
            </div>
        
            <div class="field-wrap">
              <label>
                Last Name<span class="req"></span>
              </label>
              <input type="text" name="lastName" value="<%= sessionUser.getLastName() %>" autocomplete="off"/>
            </div>
          </div>

          <div class="field-wrap">
            <label>
              Email Address<span class="req">*</span>
            </label>
            <input type="email" name="email" value="<%= sessionUser.getLastName() %>" autocomplete="off"/>
          </div>
          
          <div class="field-wrap">
            <label>
              Country<span class="req">*</span>
            </label>
            <input type="text" name="country" value="<%= sessionUser.getCountry()%>" autocomplete="off"/>
          </div>

          <div class="field-wrap">
            <label>
               City<span class="req">*</span>
            </label>
            <input type="text" name="City" value="<%= sessionUser.getCity() %>" autocomplete="off"/>
          </div>
          
          <div class="field-wrap">
            <label>
               Zipcode<span class="req">*</span>
            </label>
            <input type="text" name="Zipcode" value="<%= sessionUser.getZipcode() %>" autocomplete="off"/>
          </div>
          
          <div class="field-wrap">
            <label>
               Address<span class="req">*</span>
            </label>
            <input type="text" name="Address" value="<%= sessionUser.getAddress() %>" autocomplete="off"/>
          </div>
          
          <div class="field-wrap">
            <label>
               Phone<span class="req">*</span>
            </label>
            <input type="text" name="Phone" value="<%= sessionUser.getPhone() %>" autocomplete="off"/>
          </div>
          
          <button type="submit" class="button button-block">Update</button>
          
          </form>

        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script src="js/index.js"></script>

</body>
</html>

