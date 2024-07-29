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
 * Servlet implementation class ProductServlet
 */
@WebServlet(urlPatterns = "/ProductServlet", asyncSupported = true)
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBController controller = new DBController();

	/**
	 * Handles HTTP GET requests for retrieving all products. Retrieves all products
	 * from the database using a `DBController`, sets them as an attribute in the
	 * request, and forwards to a JSP page for display.
	 * 
	 * @param request  The HttpServletRequest object for handling HTTP requests.
	 * @param response The HttpServletResponse object for handling HTTP responses.
	 * @throws ServletException If a servlet-specific error occurs.
	 * @throws IOException      If an I/O error occurs.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve all products from the database
		ArrayList<ProductModel> productList = controller.getAllProduct();
		
		HttpSession userSession = request.getSession();
		// Set products as an attribute in the request
//		request.setAttribute(StringUtil.PRODUCTS_LIST, productList);
		userSession.setAttribute(StringUtil.PRODUCTS_LIST, productList);
		// Forward to JSP to display products
		request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
//		response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);

	}
}
