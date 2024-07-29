
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta name="viewport" content="initial-scale=1, maximum-scale=1" />
    <title>Home</title>
    <link
      href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,400,0,0"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/navbar.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/homeCategory.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/login-form.css" />
    <style type="text/css">
    .msg{
    right: 0;
    background:white;
    position: absolute;
    z-index:5;
    }
    
    .error-msg{
    color:red
    }
    
    .success-msg{
    color:green}
    </style>
    <script src="${pageContext.request.contextPath}/scripts/loginRegisterMenu.js" defer></script>
  </head>
  <body>
  <%@ include file="pages/header.jsp" %>
  <%
		
		String successMessage = (String) request.getAttribute("successMessage");
		String successMessageRegister = (String) session.getAttribute("successMessage");
		String errorMessage = (String) request.getAttribute("errorMessage");

		if (successMessage != null) {
			// print
		%>
		<p class="msg success-msg">
			<%
			out.println(successMessage);
			%>
		</p>
		<% 
		} 
		else if(errorMessage != null){
		%>
		<p class="msg error-msg">
			<%
			out.println(errorMessage);
			%>
		</p>
		<%}else if(successMessageRegister != null){ %>
		<p class="msg success-msg">
			<%
			out.println(successMessageRegister);
			session.removeAttribute("successMessage"); 
			//session.invalidate();
			%>
		</p>
		<%} %>
		
			
  <%@ include file="pages/promoslider.jsp" %>
  <%@ include file="pages/homeCategory.jsp" %>
  <%@ include file="pages/footer.jsp" %>
  
  
  </body>
</html>