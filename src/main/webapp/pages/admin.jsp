<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../stylesheets/adminHome.css" />
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
<title>Vendor Home</title>
</head>

<body>
	<%
	int productCount = (int) session.getAttribute(StringUtil.PRODUCT_COUNT);
	int customerCount = (int) session.getAttribute(StringUtil.FARMER_COUNT);
	double stockValue = (double) session.getAttribute(StringUtil.STOCK_VALUE);
	int stockCount = (int) session.getAttribute(StringUtil.STOCK_COUNT);
	DecimalFormat decimalFormat = new DecimalFormat("#.00");
	%>
	<%@ include file="adminHeader.jsp"%>
	<div class="container">
		<div class="content">
			<div class="cards">
				<div class="card">
					<div class="box">
						<h1>
							<%
							out.println(productCount);
							%>
						</h1>
						<h3>Products Added</h3>
					</div>
					<div class="icon-case">
						<span class="material-symbols-outlined"> inventory_2 </span>
					</div>
				</div>
				<div class="card">
					<div class="box">
						<h1>
							<%
							out.println(customerCount);
							%>
						</h1>
						<h3>Customers Available</h3>
					</div>
					<div class="icon-case">
						<span class="material-symbols-outlined"> groups </span>
					</div>
				</div>
				<div class="card">
					<div class="box">
						<h1>
							<%
							out.println(stockCount);
							%>
						</h1>
						<h3>Stocks Available</h3>
					</div>
					<div class="icon-case">
						<span class="material-symbols-outlined"> shopping_bag </span>
					</div>
				</div>
				<div class="card">
					<div class="box">
						NRS

						<%
					if (stockValue >= 10000000) {
					%>
						<h1>
							<%
							out.println(decimalFormat.format(stockValue / 10000000));
							%>
						</h1>
						<span>Crores</span>
						<%
						} else if (stockValue >= 100000) {
						%>
						<h1>
							<%
							out.println(decimalFormat.format(stockValue / 100000));
							%>
						</h1>
						<span>Lakhs</span>

						<%
						} else {
						%>
						<h1>
							<%
							out.println(decimalFormat.format(stockValue));
							%>
						</h1>

						<%
						}
						%>

						<h3>Stock Valuation</h3>
					</div>
					<div class="icon-case">
						<span class="material-symbols-outlined"> assured_workload </span>
					</div>
				</div>
			</div>
			<div class="content-2">
				<div class="recent-orders">
					<div class="title">
						<h2>
							Recent Orders <span style="font-size: 20px; color: red">(Comming
								Soon)</span>
						</h2>
						<a href="#" class="btn">View All</a>
					</div>
					<table>
						<tr>
							<th>Order ID</th>
							<th>Product Name</th>
							<th>Quantity</th>
							<th>Total Amount</th>
						</tr>
						<tr>
							<td>Sample ID</td>
							<td>Sample Product</td>
							<td>1</td>
							<td>$200</td>
						</tr>
					</table>
				</div>
				<div class="ratings">
					<div class="title">
						<h2>
							Ratings <span style="font-size: 15px; color: red">(Comming
								Soon)</span>
						</h2>
					</div>
					<table>
						<tr>
							<th>Avatar</th>
							<th>Name</th>
							<th>Rating</th>
						</tr>
						<tr>
							<td><i class="material-icons"> account_circle </i></td>
							<td>John Doe</td>
							<td>
								<div class="stars">
									<span class="material-symbols-outlined"> star </span> <span
										class="material-symbols-outlined"> star </span> <span
										class="material-symbols-outlined"> star </span> <span
										class="material-symbols-outlined"> star </span>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
