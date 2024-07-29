package controller.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DBController;
import model.LoginModel;
import util.StringUtil;

/**
 * Servlet implementation class LoginFarmer
 * This Servlet class handles login requests for farmers of Agrocare Mart.
 * It retrieves phone and password from the login form submission,
 * validates them against a database using a `DBController`, and redirects the user
 * accordingly based on the login result.
 */
@WebServlet(urlPatterns="/LoginUser", asyncSupported = true)
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DBController dbController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginUser() {
    	this.dbController = new DBController();
    }


    /**
     * Handles HTTP POST requests for user login.
     * Retrieves phone number and password from the login form submission.
     * Validates the credentials against the database using a DBController.
     * Redirects the user based on the login result:
     * - If login is successful, creates a session, sets session attributes, and redirects to the appropriate home page.
     * - If username or password mismatch, sets an error message attribute and forwards to the index page.
     * - If username not found, sets an error message attribute and forwards to the index page.
     * - If an internal server error occurs, sets an error message attribute and forwards to the index page.
     * 
     * @param request  The HttpServletRequest object containing login form data.
     * @param response The HttpServletResponse object for sending responses.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Extract username and password from the request parameters
        String phone = request.getParameter(StringUtil.PHONE_NUMBER);
        String password = request.getParameter(StringUtil.PASSWORD);
        System.out.println(phone);
        System.out.println(password);
        // Create a LoginModel object to hold user credentials
        LoginModel loginModel = new LoginModel(phone, password);
        
        // Call DBController to validate login credentials
        int isFarmerResult = dbController.isFarmerLoginInfo(loginModel);
        int isSellerResult = dbController.isSellerLoginInfo(loginModel);
        
     // Handle login results with appropriate messages and redirects
        if(isFarmerResult == 1 || isSellerResult == 1) {
        	 // Login successful
        	HttpSession userSession = request.getSession();
			userSession.setMaxInactiveInterval(30*60);
			
			Cookie userCookie= new Cookie(StringUtil.USER, phone);
			userCookie.setMaxAge(30*60);
			response.addCookie(userCookie);
			
			if (isFarmerResult == 1) {
		           
				userSession.setAttribute(StringUtil.PHONE_NUMBER, phone);
	            request.setAttribute(StringUtil.MESSAGE_SUCCESS, StringUtil.MESSAGE_SUCCESS_LOGIN);
				response.sendRedirect(request.getContextPath() + StringUtil.SERVLET_URL_FARMER_HOME);
	        }else if(isSellerResult == 1){
	        	userSession.setAttribute(StringUtil.ADMIN_PHONE, phone);
	        	response.sendRedirect(request.getContextPath() + StringUtil.SERVLET_URL_SELLER_HOME);
	        }
        	
        }
         else if (isSellerResult == 0 || isSellerResult == 0) {
            // Username or password mismatch
            request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_LOGIN);
			request.setAttribute(StringUtil.PHONE_NUMBER, phone);
            request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
        } else if (isSellerResult == -1 || isSellerResult == -1) {
            // Username not found
            request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_CREATE_ACCOUNT);
			request.setAttribute(StringUtil.PHONE_NUMBER, phone);
            request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
        } else {
            // Internal server error
            request.setAttribute(StringUtil.MESSAGE_ERROR, StringUtil.MESSAGE_ERROR_SERVER);
			request.setAttribute(StringUtil.PHONE_NUMBER, phone);
            request.getRequestDispatcher(StringUtil.URL_INDEX).forward(request, response);
        }
	}

}
