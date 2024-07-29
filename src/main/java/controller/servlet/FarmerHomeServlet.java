package controller.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.FarmerModel;
import model.ProductModel;
import util.StringUtil;
import controller.database.DBController;

/**
 * Servlet implementation class FarmerHomeServlet
 */
@WebServlet(urlPatterns = "/FarmerHomeServlet", asyncSupported = true)
public class FarmerHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DBController controller = new DBController();

	/**
	 * Handles HTTP GET requests to retrieve information about the farmer's home
	 * page. Retrieves farmer information based on the user's phone number from the
	 * session using the DBController. Retrieves a list of all products from the
	 * database using the DBController. Sets the list of products as an attribute in
	 * the request. Sets the farmer information as an attribute in the session.
	 * Forwards the request to the index page.
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
		HttpSession session = request.getSession(false); // Don't create a new session if one doesn't exist
		// Retrieve the user's phone number from the session
		String userphone = (String) session.getAttribute(StringUtil.PHONE_NUMBER);
		// Retrieve farmer information based on the user's phone number from the
		// database
		FarmerModel farmer = controller.getFarmerInfo(userphone);

		// Retrieve a list of all products from the database
		ArrayList<ProductModel> productList = controller.getAllProduct();
		// Set products as an attribute in the request
		request.setAttribute(StringUtil.PRODUCTS_LIST, productList);

		// Set the farmer information as an attribute in the session
		session.setAttribute(StringUtil.FARMER_INFO, farmer);
		// Forward the request to the index page
		request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
	}

}
