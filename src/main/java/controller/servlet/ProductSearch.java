package controller.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DBController;
import model.ProductModel;
import util.StringUtil;

/**
 * Servlet implementation class ProductSearch
 */
@WebServlet(urlPatterns = "/ProductSearch", asyncSupported = true)
public class ProductSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController controller;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductSearch() {
		this.controller = new DBController();
	}

	/**
	 * Handles HTTP GET requests for searching products. Retrieves search key and
	 * optional admin phone number from request parameters, searches for products
	 * based on the search key, and forwards the results to the appropriate JSP
	 * page.
	 * 
	 * @param request  The HttpServletRequest object containing search request data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException If a servlet-specific error occurs.
	 * @throws IOException      If an I/O error occurs.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchKey = request.getParameter(StringUtil.SEARCH_KEY);
		String adminPhone = request.getParameter(StringUtil.ADMIN_PHONE);

		int sellerId = controller.getSellerId(adminPhone);
		System.out.println(sellerId + " : this is seller id at product search servlet");
		ArrayList<ProductModel> productList;

		// Determine whether the search is for all products or products of a specific
		// seller
		if (adminPhone == null) {
			productList = controller.getAllSearchedProduct(searchKey);
		} else {
			productList = controller.getAllSellerSearchedProduct(searchKey, sellerId);
		}
		System.out.println(productList.size() + " this is size of searched list");
		request.setAttribute(StringUtil.PRODUCTS_LIST, productList);

		// Forward the results to the appropriate JSP page
		if (adminPhone == null) {
			request.getRequestDispatcher(StringUtil.URL_FARMER_SEARCH_PAGE).forward(request, response);
		} else {
			request.getRequestDispatcher(StringUtil.URL_SEARCHED_PRODUCT_SELLER).forward(request, response);
		}
	}

}
