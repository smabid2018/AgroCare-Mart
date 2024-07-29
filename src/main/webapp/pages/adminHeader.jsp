<%@page import="util.StringUtil"%>
<%@page import="model.AdminModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheets/adminHeader.css" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body>
	<%
	String contextPath = request.getContextPath();
	String userSession = (String) session.getAttribute(StringUtil.PHONE_NUMBER);
	AdminModel admin = (AdminModel) session.getAttribute(StringUtil.ADMIN_INFO);
	%>
	<div class="side-menu">
		<ul>
			<li><a href="<%=contextPath%>/pages/admin.jsp"> <span
					class="material-symbols-outlined"> dashboard </span>&nbsp;
					<button class="side-item-text">Dashboard</button>
			</a></li>
			<li><a href="<%=contextPath%>/pages/addNewProduct.jsp"> <span
					class="material-symbols-outlined"> add_ad </span>&nbsp;
					<button class="side-item-text">Add New</button>
			</a></li>
			<li><a href="<%=contextPath%>/pages/adminProducts.jsp"> <span
					class="material-symbols-outlined"> inventory_2 </span>&nbsp;
					<button class="side-item-text">All Products</button>
			</a></li>
			<li><a href="#"> <span class="material-symbols-outlined">
						manage_accounts </span>&nbsp;
					<button class="side-item-text">Account Setting</button>
			</a></li>
		</ul>
	</div>

	<div class="header">
		<div class="nav">
			<div class="brand-name">
				<h3 style="font-family: cursive; color: whitesmoke">
					AgroCare <span>Mart</span>
				</h3>
			</div>
			<div class="search">
				<form action="<%=contextPath%>/ProductSearch">
					<input type="hidden" name="adminPhone"
						value="<%=admin.getPhone()%>" /> 
					<input type="text" name = "searchKey"
						placeholder="Search.." />
					<button type="submit">
						<i class="material-icons search-icon"> search </i>
					</button>
				</form>
			</div>
			<div class="user">
				<div class="group">
					<form action="<%=contextPath%>/LogOutServlet" method = "post">
						<button type="submit"
							style="display: flex; align-items: center; background-color: #3761af; color: white; border: none">

							<img alt=""
								src="<%=contextPath%>/resources/images/user/<%=admin.getImageUrlFromPart()%>"
								style="max-width: 50px; border-radius: 90%">
							<div class="detail">
								<%=admin.getName()%>
								<div class="sub">Sign out</div>
							</div>
						</button>
					</form>

				</div>

			</div>
		</div>
	</div>
</body>
</html>