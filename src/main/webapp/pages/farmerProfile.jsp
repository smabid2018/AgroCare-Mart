<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/farmerProfile.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/navbar.css" />
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
<script src="${pageContext.request.contextPath}/scripts/editProduct.js" defer></script>
</head>
<body>
<%
String imageSuccess = (String) request.getAttribute("successMessage");
String imageError = (String) request.getAttribute("errorMessage");
String passError = (String) request.getAttribute("passError");
String passSuccess = (String) request.getAttribute("passSuccess");
String profileSuccessMsg = (String)request.getAttribute("profileSuccess");
String profileErrorMsg = (String)request.getAttribute("profileError");
%>
<%@ include file="header.jsp" %>
	<div class="user-container">
      <div class="right-control">
        <div class="profile-card">
          <div class="header">Profile Picture</div>
          <div class="body">
            <form action="<%=contextPath%>/UpdateFarmerImage" method="post" enctype="multipart/form-data">
              <div class="profile-image">
                <img 
	               src="<%=contextPath%>/resources/images/user/<%=user.getImageUrlFromPart() %>" 
	               alt=""  
	               id= "image"
                />
                <br />
                <%if(imageError != null){ %>
                <span class="picture-desc" Style= "color: red">
                <%
					out.println(imageError);
				%>
				</span>
                <%}else if (imageSuccess != null){ %>
                <span class="picture-desc" Style= "color: green">
                <%
					out.println(imageSuccess);
				%>
				</span>
                <%}else{ %>
                <span class="picture-desc">
                	JPG, PNG, or JPEG no larger than 10MB
                </span>
                <%} %>
              </div>
              <div class="choose-file">
                <label for="image-upload">
                  <input
                    type="file"
                    id="image-upload"
                    name="uploadImage"
                    required="required"
                    accept=".jpg, .jpeg, .png "
                  />
                </label>
              </div>
              <div class="update-btn"><button type = "submit">Upload Image</button></div>
            </form>
            <form action="<%=contextPath%>/UpdateFarmerImage" method = "post" enctype="multipart/form-data">
            <input type = "hidden" name="deleteImage" value="<%= user.getPhone()%>">
            <button type = "submit" >Delete Image</button>
            </form>
          </div>
        </div>
        <div class="profile-card change-pass">
          <div class="header">Change Password</div>
          <div class="body">
            <form action="<%=contextPath%>/UpdateFarmerDetails" method="post" id="change-pass">
              <input type="hidden" name="updateFarmerPass" value="<%=user.getFullname()%>" />
              <div>
                <label for="old-pass">
                  <input
                    type="password"
                    name = "oldPass"
                    placeholder="Old Password"
                    required="required"
                    id="old-pass"
                  />
                </label>
              </div>
              <div>
                <label for="new-pass">
                  <input
                    type="password"
                    name = "newPass"
                    placeholder="New Password"
                    required="required"
                    id="new-pass"
                  />
                </label>
              </div>
              <div>
                <label for="retype-pass">
                  <input
                    type="password"
                    name = "retypedPass"
                    placeholder="Re-type Password"
                    required="required"
                    id="retype-pass"
                  />
                </label>
              </div>
              <%if(passError != null){ %>
              <p style = "color: red">
	            <%out.println(passError); %>
	          </p>
	          <%}else if(passSuccess != null){ %>
	          <p style = "color: green">
	            <%out.println(passSuccess); %>
	          </p>
	          <%} %>
	          
              <div class="update-btn">
                <button name="submit" type="submit" id="submit">UPDATE</button>
              </div>
              
              <br />
            </form>
          </div>
        </div>
      </div>

      <div class="profile-card account-details-div">
        <div class="header">Account Details</div>
        <div class="body">
          <form action="<%=contextPath%>/UpdateFarmerDetails" method="post">
          <input type="hidden" name="updateFarmerDetails" value="<%=user.getFullname()%>" />
            <div>
              <label for="name">
                <span class="required">Your Name: *</span>
                <input
                  type="text"
                  id="name"
                  name="name"
                  value="<%=user.getFullname()%>"
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
                  name="farmer-email"
                  value="<%=user.getEmail()%>"
                  required="required"
                  tabindex="1"
                  readonly
                />
              </label>
            </div>

            <div>
              <label for="phone">
                <span class="required">Contact Number: *</span>
                <input
                  type="tel"
                  id="phone"
                  name="phone-number"
                  value="<%=user.getPhone()%>"
                  required="required"
                  readonly
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
                  value="<%=user.getAddress() %>"
                  required="required"
                  tabindex="1"
                />
              </label>
            </div>

            <div class="update-btn">
              <button name="submit" type="submit" id="submit">UPDATE</button>
            </div>
            <%if(profileErrorMsg != null){ %>
              <p style = "color: red">
	            <%out.println(profileErrorMsg); %>
	          </p>
	          <%}else if(profileSuccessMsg != null){ %>
	          <p style = "color: green">
	            <%out.println(profileSuccessMsg); %>
	          </p>
	          <%} %>
           
          </form>
        </div>
        <form action="<%=contextPath%>/UpdateFarmerDetails" method = "post">
            <input type = "hidden" name="deleteUser" value="<%= user.getPhone()%>">
            <button type = "submit" >Delete Profile</button>
            </form>
      </div>
    </div>
<%@ include file="footer.jsp" %>
</body>
</html>