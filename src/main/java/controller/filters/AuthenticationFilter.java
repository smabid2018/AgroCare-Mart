package controller.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import util.StringUtil;

public class AuthenticationFilter  implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Cast request and response objects to HttpServletRequest and HttpServletResponse for type safety
	    HttpServletRequest req = (HttpServletRequest) request;
	    //HttpServletResponse res = (HttpServletResponse) response;
	    
	    
	 // Get the requested URI
	    String uri = req.getRequestURI();
	    
	    
	    // Allow access to static resources (CSS) and the index page without checking login
		
		  if (uri.endsWith(".css") || uri.endsWith(".png") || uri.endsWith(".jpg")||uri.endsWith(".js")) {
		  chain.doFilter(request, response); return; }
		 
	    
	    
	    // Separate flags for login, login/logout servlets, and register page/servlet for better readability
		  if(uri.endsWith(StringUtil.URL_INDEX) || uri.endsWith("/")) {
				request.getRequestDispatcher(StringUtil.URL_SERVLET_PRODUCT_USER).forward(request, response);
			  return;
	    	}

	    
	    
	 // Check if a session exists and if the username attribute is set to determine login status
	    HttpSession session = req.getSession(false); // Don't create a new session if one doesn't exist
	    boolean isFarmerLoggedIn = session != null && session.getAttribute(StringUtil.PHONE_NUMBER) != null;
	    boolean isSellerLoggedIn = session != null && session.getAttribute(StringUtil.ADMIN_PHONE) != null;
	    
	    if(isSellerLoggedIn && uri.endsWith("/pages/adminProducts.jsp")) {
	    	request.getRequestDispatcher(StringUtil.URL_SERVLET_ADMIN_PRODUCT).forward(request, response);
			return;
	    }
	    
	    
	    
	    if(!isFarmerLoggedIn && uri.endsWith(StringUtil.URL_FARMER_PROFILE)) {
	    	request.getRequestDispatcher(StringUtil.URL_SERVLET_PRODUCT_USER).forward(request, response);
			return;
	    }
	    
	    if(uri.endsWith(StringUtil.URL_FARMER_SEARCH_PAGE)) {
	    	request.getRequestDispatcher(StringUtil.URL_SERVLET_ALL_PRODUCT).forward(request, response);
			return;
	    }
	    chain.doFilter(request, response); 
	    
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
