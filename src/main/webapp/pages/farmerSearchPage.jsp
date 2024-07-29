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
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/navbar.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/allProductsFarmer.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/login-form.css" />
</head>
<body>
<%@ include file="../pages/header.jsp" %>
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
								<img
									src="<%=contextPath%>/resources/images/product/${product.imageUrlFromPart}"
									alt="${product.name}" />
								<div class="product-name">
									<span>${product.name}</span>
								</div>

								<div class="product-price">
									<span>Rs <span class="price-amount">${product.price}</span></span>
								</div>
								<div class="product-price">
									<span><span class="price-amount">${product.category}</span></span>
								</div>
								<div class="product-price">
									<span><span class="price-amount" style="font-size: small; color: blue;">${product.description}</span></span>
								</div>
							</div>

							<div class="control-btn">
								<div class="edit-btn">
									<form action="#" method="get">
										<input type="hidden" name="buyNow" required
											value="${product.productCode}" />
										<button type="submit">Buy Now</button>
									</form>
								</div>
								<div class="delete-btn">
									<form action="#" method="post">
										<input type="hidden"
											name="addToCart"
											value="${product.productCode}" />
										<button type="submit">Add to Cart</button>
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