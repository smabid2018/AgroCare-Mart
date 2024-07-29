package controller.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DBController;
import model.AdminModel;
import model.ProductModel;
import util.StringUtil;

/**
 * Servlet implementation class AdminHome
 */
@WebServlet("/AdminHome")
public class AdminHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBController controller = new DBController();

	/**
	 * Handles HTTP GET requests to fetch admin home page information. Retrieves
	 * admin session data, seller information, product details, and statistical
	 * data. Sets session attributes with retrieved data. Redirects to the admin
	 * home page.
	 * 
	 * @param request  The HttpServletRequest object containing the request
	 *                 information.
	 * @param response The HttpServletResponse object for sending the response.
	 * @throws ServletException If an error occurs while processing the servlet
	 *                          request.
	 * @throws IOException      If an I/O error occurs while processing the servlet
	 *                          request.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve session and admin phone number
		HttpSession session = request.getSession(false);
		String adminPhone = (String) session.getAttribute(StringUtil.ADMIN_PHONE);

		// Retrieve seller information using admin phone number
		AdminModel seller = controller.getSellerInfo(adminPhone);
		int sellerId = controller.getSellerId(adminPhone);

		// Retrieve all products associated with the seller
		ArrayList<ProductModel> products = controller.getAllSellerProduct(sellerId);

		// Retrieve count of products, farmers, stock valuation, and stock count
		int productCount = products.size();
		int farmerCount = controller.getFarmerCount();
		double stockValuation = controller.getStockValue(sellerId);
		int stockCount = controller.getStock(sellerId);

		// Set session attributes with retrieved data
		session.setAttribute(StringUtil.STOCK_COUNT, stockCount);
		session.setAttribute(StringUtil.STOCK_VALUE, stockValuation);
		session.setAttribute(StringUtil.PRODUCT_COUNT, productCount);
		session.setAttribute(StringUtil.FARMER_COUNT, farmerCount);
		session.setAttribute(StringUtil.ADMIN_INFO, seller);
		session.setAttribute(StringUtil.ADMIN_ID, sellerId);

		// Redirect to the admin home page
		response.sendRedirect(request.getContextPath() + StringUtil.URL_ADMIN_HOME);
	}
}
