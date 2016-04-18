package com.moviebay.pkg;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuthenticationFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		String path = ((HttpServletRequest) request).getRequestURI();
		System.out.println(path);
		if (path.endsWith("index.jsp") || path.endsWith("register.jsp") || path.endsWith("/Team18Project/")
				|| path.endsWith("login.jsp") || path.endsWith("LoginServlet") || path.endsWith("RegisterServlet"))
			//if the uri are any of the above pages, filter does nothing and passes through.
			chain.doFilter(request, response);
		else{
			HttpSession session = ((HttpServletRequest)request).getSession();
			if (session.getAttribute("currentUser") == null){
				//if user tries to access page without having logged in, redirect to login page.
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			else
				// pass the request along the filter chain
				chain.doFilter(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
