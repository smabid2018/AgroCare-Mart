package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DBController;
import model.FarmerModel;
import model.PasswordEncryptionWithAes;
import util.StringUtil;
import util.ValidationUtil;

/**
 * Servlet implementation for updating farmer details. This servlet handles
 * requests to update farmer details, including password and profile
 * information. Upon receiving a request, it validates the input data, updates
 * the database accordingly, and forwards the user to the appropriate page with
 * appropriate messages.
 *
 * @param request  The HttpServletRequest object containing the update request
 *                 data.
 * @param response The HttpServletResponse object for sending responses.
 * @throws ServletException if a servlet-specific error occurs.
 * @throws IOException      if an I/O error occurs.
 */
@WebServlet("/UpdateFarmerDetails")
public class UpdateFarmerDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateFarmerDetails() {
		this.dbController = new DBController();
	}

	/**
	 * Servlet implementation for updating farmer details. This servlet handles
	 * requests to update farmer details, including password and profile
	 * information. Upon receiving a request, it validates the input data, updates
	 * the database accordingly, and forwards the user to the appropriate page with
	 * appropriate messages.
	 *
	 * @param request  The HttpServletRequest object containing the update request
	 *                 data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException if a servlet-specific error occurs.
	 * @throws IOException      if an I/O error occurs.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String updatePassword = request.getParameter(StringUtil.UPDATE_FARMER_PASS);
		String deleteUser = request.getParameter(StringUtil.DELETE_USER);
		String updateDetails = request.getParameter(StringUtil.UPDATE_FARMER_DETAILS);

		if (updatePassword != null || updateDetails != null) {
			doPut(request, response);
		}
		if (deleteUser != null && !deleteUser.isEmpty()) {
			doDelete(request, response);
		}

	}

	/**
	 * Handles HTTP PUT requests. Calls doPut method to update farmer details.
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

		String updatePassword = request.getParameter(StringUtil.UPDATE_FARMER_PASS);
		String updateDetails = request.getParameter(StringUtil.UPDATE_FARMER_DETAILS);

		String oldPass = request.getParameter(StringUtil.OLD_PASS);
		String password1 = request.getParameter(StringUtil.NEW_PASS);
		String password2 = request.getParameter(StringUtil.RETYPED_PASS);

		String userName = request.getParameter(StringUtil.DB_FULL_NAME);
		String userAddress = request.getParameter(StringUtil.ADDRESS);

		HttpSession session = request.getSession(false);
		FarmerModel user = (FarmerModel) session.getAttribute(StringUtil.FARMER_INFO);
		if (updatePassword != null) {
			String originalPass = PasswordEncryptionWithAes.decrypt(dbController.getFarmerPass(user.getPhone()),
					user.getPhone());

			if (oldPass.equals(originalPass)) {
				if (!ValidationUtil.isRegex(password1)) {
					request.setAttribute("passError", StringUtil.MESSAGE_ERROR_PASSWORD_FORMAT);
					request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
					return;
				} else if (!ValidationUtil.isMatched(password1, password2)) {
					request.setAttribute("passError", StringUtil.MESSAGE_ERROR_PASSWORD_UNMATCHED);
					request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
					return;
				} else {
					String ecPass = PasswordEncryptionWithAes.encrypt(user.getPhone(), password1);
					if (dbController.updateFarmerPassword(ecPass, user.getPhone()) == 1) {
						request.setAttribute("passSuccess", "password changed successfully");
						request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
					} else {
						request.setAttribute("passError", "Server error");
						request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
					}
					return;
				}
			} else {
				request.setAttribute("passError", StringUtil.MESSAGE_ERROR_WRONG_PASS);
				request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
			}
		}

		if (updateDetails != null) {
			if (userName != null && !userName.isEmpty() && userAddress != null && !userAddress.isEmpty()) {
				FarmerModel newFarmer = new FarmerModel();
				newFarmer.setFullname(userName);
				newFarmer.setAddress(userAddress);
				if (dbController.updateFarmerDetails(newFarmer, user.getPhone()) == 1) {
					user.setAddress(userAddress);
					user.setFullname(userName);
					request.setAttribute("profileSuccess", "Profile Updated successfully");
					request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
				} else {
					request.setAttribute("profileError", "Server error");
					request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
				}
			} else {
				request.setAttribute("profileError", "Fields are Empty");
				request.getRequestDispatcher(StringUtil.URL_FARMER_PROFILE).forward(request, response);
			}
		}
	}

	/**
	 * Handles HTTP DELETE requests. Calls doDelete method to delete farmer profile.
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

		if (dbController.deleteFarmerProfile(user) == 1) {
			session.invalidate();
			request.setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_DELETE);
			response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);
		} else {
			request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_DELETE);
			response.sendRedirect(request.getContextPath() + StringUtil.URL_INDEX);
		}
	}

}
