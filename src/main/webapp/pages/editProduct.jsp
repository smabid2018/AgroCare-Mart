<%@page import = "model.ProductModel" %>
<%@page import = "util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/adminHeader.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/addNewProduct.css" />
<script src="${pageContext.request.contextPath}/scripts/editProduct.js" defer></script>
</head>
<body>
<%
	ProductModel product = (ProductModel)request.getAttribute(StringUtil.PRODUCTS_INFO);
%>
<%@ include file="adminHeader.jsp" %>
<div class="container">
      <div id="add-product-form">
        <div>
          <h1>Edit the form to Update product</h1>
        </div>
        <p id="failure">Oopsie...product not added.</p>
        <p id="success">Your product added successfully. Thank you!</p>

        <form action="<%=contextPath%>/UpdateProduct" enctype="multipart/form-data" method="post">
        <input type = "hidden" name = "currentImage" value = "<%=product.getImageUrlFromPart()%>"/>
          <div class="retrieved-image">
            <img
              src="<%=contextPath%>/resources/images/product/<%=product.getImageUrlFromPart()%>"
              alt=""
              id="image"
              style="max-width: 300px; max-height: 400px;"/>
          </div>
          <div>
            <label for="image">
              <span class="required">Change Image: *</span>
              <input
                type="file"
                id="image-upload"
                name="image"
                accept=".jpg, .jpeg, .png, "
              />
            </label>
          </div>
          <div>
          <div>
              <label for="code">
                <span class="required">Product Code: *</span>
                <input
                  type="text"
                  id="code"
                  name="code"
                  value="<%=product.getProductCode()%>"
                  required="required"
                  autofocus="autofocus"
                  readonly
                />
              </label>
            </div>
            <div>
              <label for="name">
                <span class="required">Product Name: *</span>
                <input
                  type="text"
                  id="name"
                  name="name"
                  value="<%=product.getName()%>"
                  required="required"
                  tabindex="1"
                  autofocus="autofocus"
                />
              </label>
            </div>
            <div></div>
            <div>
              <label for="category">
                <span>Category: </span>
                <select id="category" name="category" >
                <option value="<%=product.getCategory()%>" selected><%=product.getCategory()%></option>
                  <option value="Herbicide">Herbicide</option>
                  <option value="Pesticide">Pesticide</option>
                  <option value="Livestocks">Livestocks</option>
                  <option value="Plant Suppliments">Plant Suppliments</option>
                  <option value="Animal Suppliments">Animal Suppliments</option>
                  <option value="Vet Pharma">Vet Pharma</option>
                  <option value="Fishery Suppliments">Fishery Suppliments</option>
                  <option value="Fishery Hatchling">Fishery Hatchling</option>
                </select>
              </label>
            </div>

            <div>
              <label for="price">
                <span class="required">Product Price: *</span>
                <input
                  type="number"
                  id="price"
                  name="price"
                  value="<%=product.getPrice()%>"
                  required="required"
                  tabindex="4"
                  autofocus="autofocus"
                />
              </label>
            </div>

            <div>
              <label for="stock">
                <span class="required">Product Stock: *</span>
                <input
                  type="number"
                  id="stock"
                  name="stock"
                  value="<%=product.getStock()%>"
                  required="required"
                  tabindex="2"
                  autofocus="autofocus"
                />
              </label>
            </div>

            <div>
              <label for="description">
                <span class="required">Description: *</span>
                <textarea
                  id="description"
                  name="description"
                  
                  required="required"
                >${productInfo.description}</textarea>
              </label>
            </div>

            <button name="submit" type="submit" id="submit">UPDATE</button>
          </div>
        </form>
      </div>
    </div>
</body>
</html>