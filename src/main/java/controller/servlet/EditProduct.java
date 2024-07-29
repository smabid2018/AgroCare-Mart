package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DBController;
import model.ProductModel;
import util.StringUtil;

/**
 * Servlet implementation class EditProduct
 */
@WebServlet(asyncSupported = true, urlPatterns ="/EditProduct")
public class EditProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProduct() {
    	this.dbController = new DBController();
    }

    /**
     * Handles HTTP GET requests to retrieve information about a specific product for editing.
     * Retrieves product information based on the provided product code using the DBController.
     * Sets the product information as an attribute in the request.
     * Forwards the request to the JSP page responsible for editing product details.
     * 
     * @param request  The HttpServletRequest object containing the request information.
     * @param response The HttpServletResponse object for sending the response.
     * @throws ServletException If an error occurs while processing the servlet request.
     * @throws IOException      If an I/O error occurs while processing the servlet request.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Retrieve the product code from the request parameter
		String pCode = request.getParameter(StringUtil.PRODUCT_CODE);
		// Retrieve product information based on the product code from the database
		ProductModel productInfo = dbController.getProductInfo(pCode);
		// Set products as an attribute in the request
        request.setAttribute(StringUtil.PRODUCTS_INFO, productInfo);
        // Forward to JSP to display products
        request.getRequestDispatcher(StringUtil.URL_EDIT_PRODUCT).forward(request, response);
	}
}
