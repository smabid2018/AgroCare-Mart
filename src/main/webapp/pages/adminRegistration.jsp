<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Seller's Registration</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/adminRegistration.css" />
<script src="${pageContext.request.contextPath}/scripts/editProduct.js" defer></script>
</head>
<body>
	<%	String contextPath = request.getContextPath();
		String errorMessage = (String) request.getAttribute("errorMessage");
	%>
	<div class="container">
      <div class="profile-image">
        <img
          src="<%=contextPath %>/resources/images/mart-front.jpeg"
          alt=""
          id="image"
          style="max-width: 300px; max-height: 400px; padding-left: 3vh"
        />
        <%if(errorMessage != null){ %>
	        <p id="failure">
	        	<%
					out.println(errorMessage);
				%>
			</p>
        <%}%>
      </div>
      <div id="seller-reg-form">
        <div>
          <h1>Seller Registration</h1>
        </div>

        <form action="<%=contextPath %>/AdminRegister"  method="post"  enctype="multipart/form-data">
          <div>
            <label for="name">
              <span class="required">Full Name: *</span>
              <input
                type="text"
                id="name"
                name="fullName"
                value=""
                required="required"
                tabindex="1"
              />
            </label>
          </div>
          <div></div>
          <div>
            <label for="phone">
              <span class="required">Mobile Number: *</span>
              <input
                type="tel"
                id="phone"
                name="phoneNumber"
                value=""
                placeholder="Please Enter nepali cellular no."
                pattern="[9]{1}[7-8]{1}[0-9]{8}"
                required="required"
                tabindex="1"
              />
            </label>
          </div>

          <div>
            <label for="email">
              <span class="required">Email: *</span>
              <input
                type="email"
                id="email"
                name="email"
                value=""
                placeholder="Bussiness Email"
                required="required"
                tabindex="4"
              />
            </label>
          </div>
          <div>
            <label for="address">
              <span class="required">Address: *</span>
              <input
                type="text"
                id="address"
                name="address"
                value=""
                required="required"
                tabindex="1"
              />
            </label>
          </div>
          <div>
            <div>
            <label for="pass1">
              <span class="required">Password: *</span>
              <input
                type="text"
                id="pass"
                name="password"
                value="Abid@123"
                required="required"
              />
            </label>
          </div>
          <div>
            <label for="pass2">
              <span class="required">Retype Password: *</span>
              <input
                type="text"
                id="pass"
                name="retypePassword"
                value="Abid@123"
                required="required"
              />
            </label>
          </div>
          </div>
          <div>
            <label for="image">
              <span class="required">Profile Image: *</span>
              <input 
              type="file" 
              id="image-upload" 
              name="image" 
              accept=".jpg, .jpeg, .png, "
              />
            </label>
          </div>
          <div>
            <button name="submit" type="submit" id="submit">REGISTER</button>
          </div>
        </form>
      </div>
    </div>
</body>
</html>