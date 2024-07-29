package controller.servlet;

import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.database.DBController;
import model.AdminModel;
import util.StringUtil;
import util.ValidationUtil;

/**
 * Servlet implementation class AdminRegistration
 */
@WebServlet(urlPatterns = "/AdminRegister", asyncSupported = true)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)

public class AdminRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRegistration() {
		this.dbController = new DBController();
	}

	/**
	 * Handles HTTP POST requests to register a new admin. Extracts signup
	 * information from request parameters including full name, email, phone number,
	 * address, passwords, and profile image. Performs validation on the extracted
	 * data, including password format, password matching, existence of phone
	 * number, existence of email, and image format. If validation passes, creates
	 * an AdminModel object to hold admin information and calls the DBController to
	 * register the admin. Uploads the profile image to the Tomcat server if
	 * registration is successful. Redirects to the index page with a success
	 * message if registration is successful. Forwards the request to the admin
	 * registration page with an error message if registration fails.
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
		// Extraction of signup info from request parameter
		String fullName = request.getParameter(StringUtil.FULL_NAME);
		String email = request.getParameter(StringUtil.EMAIL);
		String phone = request.getParameter(StringUtil.PHONE_NUMBER);
		String address = request.getParameter(StringUtil.ADDRESS);
		String password1 = request.getParameter(StringUtil.PASSWORD);
		String password2 = request.getParameter(StringUtil.RETYPE_PASSWORD);
		Part imagePart = request.getPart(StringUtil.IMAGE);

		// MS Edge and IE fix
		String submittedFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

		AdminModel admin;

		int result = 0;

		// validation starts
		if (!ValidationUtil.isRegex(password1)) {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_PASSWORD_FORMAT);
			request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
			return;
		} else if (!ValidationUtil.isMatched(password1, password2)) {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_PASSWORD_UNMATCHED);
			request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
			return;
		} else {
			try {
				if (dbController.checkNumberIfExist(phone)) {
					System.out.println("The PHONE exist already");
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_PHONE_NUMBER);
					request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
					return;

				} else if (dbController.checkEmailIfExist(email)) {
					System.out.println("The email exist already");
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_EMAIL);
					request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
					return;

				} else if (!(submittedFileName.toLowerCase().endsWith(".jpg")
						|| submittedFileName.toLowerCase().endsWith(".jpeg")
						|| submittedFileName.toLowerCase().endsWith(".png") || submittedFileName == ""
						|| submittedFileName.isEmpty())) {
					// File has an invalid extension, set error message
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_IMAGE_FORMAT);
					request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
					return;
				} else {
					// Create an AdminModel object to hold admin information
					admin = new AdminModel(fullName, phone, email, address, password1, imagePart);
					// Call DBController to register the admin
					result = dbController.registerAdmin(admin);
				}
			} catch (ClassNotFoundException | ServletException | IOException e) {

				e.printStackTrace();
				request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_SERVER);
				request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
				return;
			}
		}
		// validation ends

		if (result == 1) {
			// Upload image in tomcat server
			String savePath = StringUtil.IMAGE_DIR_USER;
			String fileName = admin.getImageUrlFromPart();
			if (!fileName.isEmpty() && fileName != null)
				imagePart.write(savePath + fileName);

			request.getSession().setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_REGISTER);

			response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX + "?success=true");
			return;

		} else if (result == 0) {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_REGISTER);
			request.getRequestDispatcher(StringUtil.URL_ADMIN_REGISTER).forward(request, response);
			return;
		} else {

		}
	}

}
