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
import model.ProductModel;
import util.StringUtil;

/**
 * Servlet implementation class AdminProducts
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/AdminProducts" })
public class AdminProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBController controller = new DBController();

	/**
	 * Handles HTTP GET requests to retrieve the list of products associated with
	 * the admin (seller). Retrieves the session object to obtain the seller ID.
	 * Retrieves the list of products using the seller ID from the database
	 * controller. Sets the list of products as a request attribute. Forwards the
	 * request to the admin product page for display.
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
		// Retrieve session object without creating a new session if one doesn't exist
		HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist

		// Retrieve the seller ID from the session
		int sellerId = (int) session.getAttribute(StringUtil.ADMIN_ID);

		// Retrieve the list of products associated with the seller from the database
		// controller
		ArrayList<ProductModel> productList = controller.getAllSellerProduct(sellerId);

		// Set the list of products as a request attribute
		request.setAttribute(StringUtil.PRODUCTS_LIST, productList);

		// Forward the request to the admin product page for display
		request.getRequestDispatcher(StringUtil.URL_ADMIN_PRODUCT).forward(request, response);
	}

}
