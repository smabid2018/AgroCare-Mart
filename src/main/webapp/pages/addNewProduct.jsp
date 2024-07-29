<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Add new Product</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/adminHeader.css" />
<link
  href="https://fonts.googleapis.com/icon?family=Material+Icons"
  rel="stylesheet"
/>
<link
  rel="stylesheet"
  href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/addNewProduct.css" />
</head>
<body>
<%@ include file="adminHeader.jsp" %>
	<div class="container">
      <div id="add-product-form">
        <div>
          <h1>Fill the form to add new product</h1>
        </div>
        <p id="failure">Oopsie...product not added.</p>
        <p id="success">Your product added successfully. Thank you!</p>

        <form method="post" action="<%=contextPath%>/AddProduct"  enctype="multipart/form-data">
        <div>
            <label for="code">
              <span class="required">Product Code: *</span>
              <input
                type="text"
                id="code"
                name="code"
                value=""
                required="required"
                autofocus="autofocus"
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
                value=""
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
              <select id="category" name="category" tabindex="4">
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
                value=""
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
                value=""
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
                placeholder="write your product description here."
                required="required"
              ></textarea>
            </label>
          </div>

          <div>
            <label for="image">
              <span class="required">Product Image: *</span>
              <input 
              type="file" 
              id="image" 
              name="image"
              required="required" 
              accept=".jpg, .jpeg, .png, "
              />
            </label>
          </div>
          <div>
            <button name="submit" type="submit" id="submit">ADD</button>
          </div>
        </form>
      </div>
    </div>
</body>
</html>