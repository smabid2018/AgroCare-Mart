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

/**
 * Servlet implementation for updating product details. This servlet handles
 * requests to update product details. Upon receiving a request, it validates
 * the input data, updates the database accordingly, and forwards the user to
 * the appropriate page with appropriate messages.
 *
 * @param request  The HttpServletRequest object containing the update request
 *                 data.
 * @param response The HttpServletResponse object for sending responses.
 * @throws ServletException if a servlet-specific error occurs.
 * @throws IOException      if an I/O error occurs.
 */
@WebServlet(asyncSupported = true, urlPatterns = "/UpdateProduct")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)

public class UpdateProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	/**
	 * Constructor initializes the DBController.
	 */
	public UpdateProduct() {
		this.dbController = new DBController();
	}

	/**
	 * Handles HTTP POST requests. Calls doPut method to update product details.
	 *
	 * @param request  The HttpServletRequest object containing the update request
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Extraction of signup info from request parameter
		String pCode = request.getParameter(StringUtil.PRODUCT_CODE);
		String pCodeDelete = request.getParameter(StringUtil.PRODUCT_CODE_DELETE);
		if (pCode != null && !pCode.isEmpty()) {
			doPut(request, response);
		}

		if (pCodeDelete != null && !pCodeDelete.isEmpty()) {
			doDelete(request, response);
		}

	}

	/**
	 * Handles HTTP PUT requests. Updates product details and database entry.
	 *
	 * @param request  The HttpServletRequest object containing the update request
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pCode = request.getParameter(StringUtil.PRODUCT_CODE);
		String pName = request.getParameter(StringUtil.PRODUCT_NAME);
		String pCategory = request.getParameter(StringUtil.PRODUCT_CATEGORY);
		int pPrice = Integer.parseInt(request.getParameter(StringUtil.PRODUCT_PRICE));
		int pStock = Integer.parseInt(request.getParameter(StringUtil.PRODUCT_STOCK));
		String pDescription = request.getParameter(StringUtil.PRODUCT_DESCRIPTION);
		Part pImage = request.getPart(StringUtil.PRODUCT_IMAGE);

		String currentImage = request.getParameter(StringUtil.CURRENT_IMAGE);

		HttpSession session = request.getSession(false);
		int adminId = (int) session.getAttribute(StringUtil.ADMIN_ID);
		System.out.println("this is previous Image" + currentImage);
		String submittedFileName = Paths.get(pImage.getSubmittedFileName()).getFileName().toString(); // MS Edge and IE
																										// fix
		ProductModel editedProduct;
		int result = 0;

		// validation starts
		try {
			if (!(submittedFileName.toLowerCase().endsWith(".jpg") || submittedFileName.toLowerCase().endsWith(".jpeg")
					|| submittedFileName.toLowerCase().endsWith(".png") || submittedFileName == ""
					|| submittedFileName.isEmpty())) {
				// File has an invalid extension, set error message
				request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_IMAGE_FORMAT);
				request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
				return;
			} else {
				// Create a Product Model object to hold Product information
				editedProduct = new ProductModel();
				editedProduct.setSellerId(adminId);
				editedProduct.setProductCode(pCode);
				editedProduct.setName(pName);
				editedProduct.setDescription(pDescription);
				editedProduct.setCategory(pCategory);
				editedProduct.setPrice(pPrice);
				editedProduct.setStock(pStock);
				if (pImage.getSize() == 0) {
					editedProduct.setImageUrlFromDB(currentImage);
				} else {
					editedProduct.setImageUrlFromPart(pImage);
				}
				// Call DBController to add the product
				result = dbController.updateProduct(editedProduct);
			}
		} catch (ServletException | IOException e) {

			e.printStackTrace();
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_SERVER);
			request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
			return;
		}

		// validation Ends

		if (result == 1) {
			// Upload image in tomcat server
			String savePath = StringUtil.IMAGE_DIR_PRODUCT;
			String fileName = editedProduct.getImageUrlFromPart();
			System.out.println("the image size is: " + pImage.getSize());
			if (pImage.getSize() != 0) {
				pImage.write(savePath + fileName);
			}

			request.getSession().setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_UPDATE_PRODUCT);
			response.sendRedirect(request.getContextPath() + StringUtil.URL_ADMIN_HOME);
			return;

		} else {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_UPDATE_PRODUCT);
			request.getRequestDispatcher(StringUtil.URL_ADD_PRODUCT).forward(request, response);
			return;
		}
	}

	/**
	 * Handles HTTP DELETE requests. Deletes product and database entry.
	 *
	 * @param request  The HttpServletRequest object containing the delete request
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (dbController.deleteProduct(req.getParameter(StringUtil.PRODUCT_CODE_DELETE)) == 1) {
			req.setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_DELETE);
			resp.sendRedirect(req.getContextPath() + StringUtil.URL_ADMIN_HOME);
		} else {
			req.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_DELETE_PRODUCT);
			resp.sendRedirect(req.getContextPath() + StringUtil.URL_ADMIN_HOME);
		}
	}

}
