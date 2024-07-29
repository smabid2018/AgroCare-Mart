package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.StringUtil;

/**
 * Servlet implementation class LogOutServlet
 */
@WebServlet(urlPatterns = "/LogOutServlet", asyncSupported = true)
public class LogOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Handles HTTP POST requests for user logout. Clears existing cookies,
	 * invalidates the user session, and redirects to the login page.
	 * 
	 * @param request  The HttpServletRequest object containing logout request data.
	 * @param response The HttpServletResponse object for sending responses.
	 * @throws ServletException If a servlet-specific error occurs.
	 * @throws IOException      If an I/O error occurs.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. Clear existing cookies
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				// Set max age to 0 to effectively delete the cookie
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		// 2. Invalidate user session (if it exists)
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// 3. Redirect to login page
		response.sendRedirect(request.getContextPath() + StringUtil.URL_SERVLET_PRODUCT_USER);
	}

}
