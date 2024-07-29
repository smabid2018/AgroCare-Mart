package controller.servlet;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controller.database.DBController;
import model.ProductModel;
import util.StringUtil;
import util.ValidationUtil;

/**
 * Servlet implementation class AddProduct
 */
@WebServlet(urlPatterns = "/AddProduct", asyncSupported = true)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	/**
	 * Constructs a new AddProduct servlet instance.
	 */
	public AddProduct() {
		// Initialize the DBController instance
		this.dbController = new DBController();
	}

	/**
	 * Handles HTTP POST requests for adding a new product.
	 * 
	 * @param request  The HttpServletRequest object containing the request
	 *                 information.
	 * @param response The HttpServletResponse object for sending the response.
	 * @throws ServletException If an error occurs while processing the servlet
	 *                          request.
	 * @throws IOException      If an I/O error occurs while processing the servlet
	 *                          request.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Extraction of product information from request parameters
		String pCode = request.getParameter(StringUtil.PRODUCT_CODE);
		String pName = request.getParameter(StringUtil.PRODUCT_NAME);
		String pCategory = request.getParameter(StringUtil.PRODUCT_CATEGORY);
		int pPrice = Integer.parseInt(request.getParameter(StringUtil.PRODUCT_PRICE));
		int pStock = Integer.parseInt(request.getParameter(StringUtil.PRODUCT_STOCK));
		String pDescription = request.getParameter(StringUtil.PRODUCT_DESCRIPTION);
		Part pImage = request.getPart(StringUtil.PRODUCT_IMAGE);
		// MS Edge and IE fix
		String submittedFileName = Paths.get(pImage.getSubmittedFileName()).getFileName().toString();

		// Retrieving session information
		HttpSession session = request.getSession(false);
		int adminId = (int) session.getAttribute(StringUtil.ADMIN_ID);

		ProductModel newProduct;
		int result = 0;

		// validation starts
		if (!ValidationUtil.isValidCode(pCode)) {
			// Invalid product code, set error message and forward
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_INVALID_CODE);
			request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
			return;
		} else {
			try {
				if (dbController.isCodeExists(pCode)) {
					// Product code already exists, set error message and forward
					System.out.println("The code exist already from add product servlet");
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_CODE_EXISTS);
					request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
					return;

				} else if (!(submittedFileName.toLowerCase().endsWith(".jpg")
						|| submittedFileName.toLowerCase().endsWith(".jpeg")
						|| submittedFileName.toLowerCase().endsWith(".png") || submittedFileName == ""
						|| submittedFileName.isEmpty())) {
					// Invalid file extension, set error message and forward
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_IMAGE_FORMAT);
					request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
					return;
				} else {
					// Create a Product Model object to hold Product information
					newProduct = new ProductModel(adminId, pCode, pName, pDescription, pCategory, pPrice, pStock,
							pImage);
					// Call DBController to add the product
					result = dbController.addNewProduct(newProduct);
				}
			} catch (ClassNotFoundException | ServletException | IOException e) {
				// Error occurred during database operation, set error message and forward
				e.printStackTrace();
				request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_SERVER);
				request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
				return;
			}
		}

		// validation Ends

		// Handling the validation results
		if (result == 1) {
			// Product added successfully, upload image to the server and redirect to admin
			// home page
			String savePath = StringUtil.IMAGE_DIR_PRODUCT;
			String fileName = newProduct.getImageUrlFromPart();
			if (!fileName.isEmpty() && fileName != null)
				pImage.write(savePath + fileName);

			request.getSession().setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_ADD_PRODUCT);
			response.sendRedirect(request.getContextPath() + StringUtil.URL_ADMIN_HOME);
			return;

		} else {
			// Error occurred while adding product, set error message and forward
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_ADD_PRODUCT);
			request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
			return;
		}
	}

}
