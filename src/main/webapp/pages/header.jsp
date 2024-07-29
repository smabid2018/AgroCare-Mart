<%@page import="util.StringUtil"%>
<%@page import="model.FarmerModel"%>
<%@page import="model.ProductModel"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" />
<link href="https://fonts.googleapis.com/css2?family=Lato&display=swap"
	rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded:opsz,wght,FILL,GRAD@48,400,0,0" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheets/navbar.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/stylesheets/login-form.css" />
<script
	src="${pageContext.request.contextPath}/scripts/loginRegisterMenu.js?v=1"
	defer></script>

<title>Home</title>
</head>
<body>
	<%
	String contextPath = request.getContextPath();
	String userSession = (String) session.getAttribute(StringUtil.PHONE_NUMBER);
	FarmerModel user = (FarmerModel) session.getAttribute(StringUtil.FARMER_INFO);

	String cookieUsername = null;
	String cookieSessionID = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(StringUtil.USER))
		cookieUsername = cookie.getValue();
			if (cookie.getName().equals(StringUtil.JSESSIONID))
		cookieSessionID = cookie.getValue();
		}
	}
	%>
	<nav class="navbar">
		<div class="sidepanel">
			<input type="checkbox" id="check"> <label for="check">
				<i class="fas fa-bars" id="btn"></i> <i class="fas fa-times"
				id="cancel"></i>
			</label>
			<div class="sidebar">
				<header>Menu</header>
				<a href="<%=contextPath%><%=StringUtil.URL_SERVLET_PRODUCT_USER%>" class="active">
					<span>Home</span>
				</a> <a href="<%=contextPath%>/pages/farmerProfile.jsp" class="active">
					<span>Profile</span>
				</a> <a href="<%=contextPath%>/pages/farmerSearchPage.jsp"> <span>All
						Products</span>
				</a> <a href="#"> <span>Overview</span>
				</a> <a href="#"> <span>Events</span>
				</a> <a href="#"> <span>About</span>
				</a> <a href="#"> <span>Services</span>
				</a> <a href="#"> <span>Contact</span>
				</a>
			</div>
		</div>

		<div class="text" style="font-family: cursive">AgroCare</div>

		<div class="item search right" tabindex="0">
			<div class="search">
				<form action="<%=contextPath%>/ProductSearch">
					<input type="text" name= "searchKey" placeholder="Search.." />
					<button type="submit">
						<i class="material-icons search-icon"> search </i>
					</button>
				</form>
			</div>
		</div>




		<%
		if (userSession == null) {
		%>
		<button class="item login-btn" id="login-button"
			style="background-color: #3761af; color: white; border: none">
			<div class="group">
				<i class="material-icons"> account_circle </i>
				<div class="detail">
					Account
					<div class="sub">Sign In</div>
				</div>
			</div>
		</button>

		<%
		} else {
		%>
		<div>
			<form action="<%=contextPath%>/LogOutServlet" method="post">
				<button class="item " id=""
					style="background-color: #3761af; color: white; border: none">
					<div class="group">
						<img alt=""
							src="<%=contextPath%>/resources/images/user/<%=user.getImageUrlFromPart()%>"
							style="max-width: 40px; border-radius: 100%">
						<div class="detail">
							<%=user.getFullname()%>
							<div class="sub">Sign out</div>
						</div>
					</div>
				</button>
			</form>
		</div>



		<%
		}
		%>


		<button class="item"
			style="background-color: #3761af; color: white; border: none">
			<div class="group">
				<i class="material-icons"> shopping_cart </i>
				<div class="detail">
					Cart
					<div class="sub">Rs 0.0</div>
				</div>
			</div>
		</button>
	</nav>

	<section>
		<div class="blur-bg-overlay"></div>
		<div class="form-popup">
			<span class="close-btn material-symbols-rounded">close</span>
			<div class="form-box login">
				<div class="form-details"></div>
				<div class="form-content">
					<h2>LOGIN</h2>
					<form action="<%=contextPath%>/LoginUser" method="post">
						<div class="input-field">
							<!-- <input type="email" required />
              				<label>Email</label> -->
							<input type="tel" id="phone" name="phoneNumber"
								pattern="[9]{1}[7-8]{1}[0-9]{8}" required="required" /> <label
								for="phone">Phone Number</label>
						</div>
						<div class="input-field">
							<input type="password" name="password" required value="Abid@123" />
							<label>Password</label>
						</div>
						<a href="#" class="forgot-pass-link">Forgot password?</a>
						<button type="submit">Log In</button>
					</form>
					<div class="bottom-link">
						Don't have an account? <a href="#" id="signup-link">Signup</a>
					</div>
				</div>
			</div>
			<div class="form-box signup">
				<div class="form-details">
					<h2>Create Account</h2>
					<p>To become a part of our community, please sign up using your
						personal information.</p>
				</div>
				<div class="form-content">
					<h2>SIGNUP</h2>
					<form action="<%=contextPath%>/RegisterFarmers" method="post"
						enctype="multipart/form-data">
						<div class="input-field">

							<input type="text" id="fullName" name="fullName"
								required="required" /> <label for="fullName">Full Name</label>
						</div>
						<div class="input-field">
							<input type="email" required name="email" /> <label>Enter
								your email</label>
						</div>
						<div class="input-field">
							<input type="text" required name="address" /> <label>Address</label>
						</div>
						<div class="input-field">

							<input type="tel" id="phone" name="phoneNumber"
								pattern="[9]{1}[7-8]{1}[0-9]{8}" required="required" /> <label
								for="phone">Phone Number</label>
						</div>
						<div class="input-field">
							<input type="text" name="password" required /> <label>Create
								password</label>
						</div>

						<div class="input-field">
							<input type="text" name="retypePassword" required /> <label>Re-type
								password</label>
						</div>

						<div class="input-field">
							<input type="file" id="image" name="image"
								accept=".jpg, .png, .jpeg" /> <label>Upload Photo</label>
						</div>
						<button type="submit">Sign Up</button>

					</form>

					<div class="bottom-link">
						Already have an account? <a href="#" id="login-link">Login</a>
					</div>
				</div>
			</div>
		</div>
	</section>

</body>
</html>