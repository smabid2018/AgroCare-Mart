<%@ page import="java.util.ArrayList" %>
<%@page import = "util.StringUtil"%>
<%@page import = "model.FarmerModel" %>
<%@page import = "model.ProductModel" %>
<%@ page import="java.util.*" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/homeCategory.css" />
</head>
<body>
	<%
	    // Retrieve the ArrayList from the request (product servlet)
	    ArrayList<ProductModel> productsList = (ArrayList<ProductModel>)session.getAttribute(StringUtil.PRODUCTS_LIST);
	
	    // Initialize maps to categorize products
	    Map<String, ArrayList<ProductModel>> categoryMap = new HashMap<>(); // LinkedHashMap to maintain insertion order
	
	    // Categorize products based on some criteria (e.g., product category)
	     for (ProductModel product : productsList) {
	        String category = product.getCategory();
	        if (!categoryMap.containsKey(category)) {
	            categoryMap.put(category, new ArrayList<ProductModel>());
	        }
	        categoryMap.get(category).add(product);
	   	 }
	%>
	<div class="category-wrapper">
      <main>
      	
        <div class="category-list">
			
			<c:if test="${empty productsList}">
				<div class="category-card">
            		<h3>No Product Found</h3>
            	</div>
			</c:if>
			
			
			<c:if test="${not empty productsList}">
				<c:forEach var="entry" items="<%=categoryMap%>">
				    <div class="category-card">
				        <h3>${entry.key}</h3> <!-- Category name -->
				        <div class="category-products">
				            <!-- Iterate over products in the category -->
				            <c:forEach var="product" items="${entry.value}">
				                <div class="product">
				                    <img src="${pageContext.request.contextPath}/resources/images/product/${product.imageUrlFromPart}" alt="${product.name}" />
				                    <span>${product.name}</span>
				                </div>
				            </c:forEach>
				        </div>
				        <div class="view-more">
				            <a class="btn" href="#">View More</a>
				        </div>
				    </div>
				</c:forEach>
			</c:if>
          </div>
      </main>
    </div>
</body>
</html>