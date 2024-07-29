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
import model.FarmerModel;
import util.StringUtil;
import util.ValidationUtil;

/**
 * Servlet implementation class RegisterFarmers This Servlet class handles
 * farmer registration requests. It extracts farmer information from the
 * registration form submission, performs basic data validation (to be
 * implemented), and attempts to register the farmer in the database using a
 * `DBController`. The user is redirected to the home page upon successful
 * registration.
 */
@WebServlet(urlPatterns = "/RegisterFarmers", asyncSupported = true)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)

public class RegisterFarmers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterFarmers() {
		this.dbController = new DBController();
	}

	/**
	 * Servlet implementation for handling farmer registration requests. This
	 * servlet extracts farmer information from the registration form submission,
	 * performs basic data validation, and attempts to register the farmer in the
	 * database using a `DBController`. Upon successful registration, the user is
	 * redirected to the home page.
	 *
	 * @param request  The HttpServletRequest object containing registration form
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
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
		String submittedFileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString(); // MS Edge and
																											// IE fix

		FarmerModel farmer;

		int result = 0;

		// validation starts
		if (!ValidationUtil.isRegex(password1)) {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_PASSWORD_FORMAT);
			request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
			return;
		} else if (!ValidationUtil.isMatched(password1, password2)) {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_PASSWORD_UNMATCHED);
			request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
			return;
		} else {
			try {
				if (dbController.checkNumberIfExist(phone)) {
					System.out.println("The PHONE exist already");
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_PHONE_NUMBER);
					request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
					return;

				} else if (dbController.checkEmailIfExist(email)) {
					System.out.println("The email exist already");
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_EMAIL);
					request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
					return;

				} else if (!(submittedFileName.toLowerCase().endsWith(".jpg")
						|| submittedFileName.toLowerCase().endsWith(".jpeg")
						|| submittedFileName.toLowerCase().endsWith(".png") || submittedFileName == ""
						|| submittedFileName.isEmpty())) {
					// File has an invalid extension, set error message
					request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_IMAGE_FORMAT);
					request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
					return;
				} else {
					// Create a StudentModel object to hold student information
					farmer = new FarmerModel(fullName, phone, email, address, password1, imagePart);
					// Call DBController to register the student
					result = dbController.registerFarmer(farmer);
				}
			} catch (ClassNotFoundException | ServletException | IOException e) {

				e.printStackTrace();
				request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_SERVER);
				request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
				return;
			}
		}
		// validation ends

		if (result == 1) {
			// Upload image in tomcat server
			String savePath = StringUtil.IMAGE_DIR_USER;
			String fileName = farmer.getImageUrlFromPart();
			if (!fileName.isEmpty() && fileName != null)
				imagePart.write(savePath + fileName);

			request.getSession().setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_REGISTER);

			response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX + "?success=true");
			return;

		} else {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_REGISTER);
			request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
			return;
		}

	}

}
