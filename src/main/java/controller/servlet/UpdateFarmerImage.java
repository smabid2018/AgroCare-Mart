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
import model.FarmerModel;
import util.StringUtil;

/**
 * Servlet implementation for updating farmer image. This servlet handles
 * requests to update farmer profile image. Upon receiving a request, it
 * validates the image file, updates the database accordingly, and forwards the
 * user to the appropriate page with appropriate messages.
 *
 * @param request  The HttpServletRequest object containing the update request
 *                 data.
 * @param response The HttpServletResponse object for sending responses.
 * @throws ServletException if a servlet-specific error occurs.
 * @throws IOException      if an I/O error occurs.
 */
@WebServlet(urlPatterns = "/UpdateFarmerImage", asyncSupported = true)
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)

public class UpdateFarmerImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	/**
	 * Constructor initializes the DBController.
	 */
	public UpdateFarmerImage() {
		this.dbController = new DBController();
	}

	/**
	 * Handles HTTP POST requests. Calls doPut method to update farmer image.
	 *
	 * @param request  The HttpServletRequest object containing the update request
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part updateId = request.getPart(StringUtil.UPDATE_ID);
		String deleteId = request.getParameter(StringUtil.DELETE_ID);
		if (updateId != null) {
			doPut(request, response);
		}
		if (deleteId != null && !deleteId.isEmpty()) {
			doDelete(request, response);
		}
	}

	/**
	 * Handles HTTP PUT requests. Updates farmer image and database entry.
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
		System.out.println("put triggered");
		Part newImage = request.getPart(StringUtil.UPDATE_ID);
		HttpSession session = request.getSession(false);
		FarmerModel user = (FarmerModel) session.getAttribute(StringUtil.FARMER_INFO);

		String submittedFileName = Paths.get(newImage.getSubmittedFileName()).getFileName().toString(); // MS Edge and
																										// IE fix
		if (!(submittedFileName.toLowerCase().endsWith(".jpg") || submittedFileName.toLowerCase().endsWith(".jpeg")
				|| submittedFileName.toLowerCase().endsWith(".png") || submittedFileName == ""
				|| submittedFileName.isEmpty())) {
			// File has an invalid extension, set error message
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_IMAGE_FORMAT);
			request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
			return;
		} else {
			user.setImageUrlFromPart(newImage);
			if (dbController.updateFarmerImage(user) == 1) {
				// Upload image in tomcat server
				String savePath = StringUtil.IMAGE_DIR_USER;
				String fileName = user.getImageUrlFromPart();
				if (newImage.getSize() != 0) {
					newImage.write(savePath + fileName);
				}

				request.setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_DELETE);
				response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);
			} else {
				request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_DELETE);
				response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);
			}
		}
	}

	/**
	 * Handles HTTP DELETE requests. Deletes farmer image and updates database
	 * entry.
	 *
	 * @param request  The HttpServletRequest object containing the delete request
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		FarmerModel user = (FarmerModel) session.getAttribute(StringUtil.FARMER_INFO);
		user.setImageUrlFromPart(null);

		if (dbController.updateFarmerImage(user) == 1) {
			request.setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_DELETE);
			response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);
		} else {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_DELETE);
			response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);
		}
	}

}
