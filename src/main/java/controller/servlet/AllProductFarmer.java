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
 * Servlet implementation class AllProductFarmer
 */
@WebServlet(urlPatterns = "/AllProductFarmer", asyncSupported = true)
public class AllProductFarmer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBController controller = new DBController();

	/**
	 * Handles HTTP GET requests to fetch all products available from farmers.
	 * Retrieves a list of all products from the database using the DBController.
	 * Sets the list of products as an attribute in the request. Forwards the
	 * request to the JSP page responsible for displaying farmer search results.
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
		// Retrieve a list of all products from the database
		ArrayList<ProductModel> productList = controller.getAllProduct();
		// Set products as an attribute in the request
		request.setAttribute(StringUtil.PRODUCTS_LIST, productList);
		// Forward to JSP to display products
		request.getRequestDispatcher(StringUtil.URL_FARMER_SEARCH_PAGE).forward(request, response);
	}

}
