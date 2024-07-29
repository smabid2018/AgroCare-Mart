<%@ page import="java.util.ArrayList" %>
<%@page import = "model.ProductModel" %>
<%@page import = "util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Admin Product Page</title>
<link rel="stylesheet" href="../stylesheets/adminProduct.css" />
</head>
<body>
	<%@ include file="adminHeader.jsp" %>
	<div class="container">
      <div class="content">
        <div class="cards">  
        	<c:if test="${empty productsList}">
				<p>No Products found.</p>
			</c:if>
			
			<c:if test="${not empty productsList}">
				<c:forEach var="product" items="${productsList}">
					<div class="card">
			            <div class="box">
			              <img src="<%=contextPath%>/resources/images/product/${product.imageUrlFromPart}" alt="${product.name}" />
			              <div class="product-name">
			                <span>${product.name}</span>
			              </div>
			
			              <div class="product-price">
			                <span>Rs <span class="price-amount">${product.price}</span></span>
			              </div>
			            </div>
			            <div class="control-btn">
			              <div class="edit-btn"> 
			              	<form action="<%=contextPath%>/EditProduct" method = "get">
			              		<input type="hidden"  name = "code" required value = "${product.productCode}" />
			              		<button type= "submit"> EDIT</button> 
			              	</form> 
			              </div>
			              <div class="delete-btn">
			              <form action="<%=contextPath%>/UpdateProduct" method = "post">
			              	<input type="hidden"  name = "<%=StringUtil.PRODUCT_CODE_DELETE%>"  value = "${product.productCode}" />
			              	<button type = "submit">DELETE</button>
			              </form>
			              	
			              </div>
			            </div>
			        </div>
				</c:forEach>
			</c:if>

        </div>
      </div>
    </div>
</body>
</html>