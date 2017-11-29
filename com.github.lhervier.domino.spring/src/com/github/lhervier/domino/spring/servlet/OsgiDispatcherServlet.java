package com.github.lhervier.domino.spring.servlet;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.lhervier.domino.spring.DominoApplicationContextInitializer;

public abstract class OsgiDispatcherServlet extends HttpServlet {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The delegated servlet
	 */
	private DispatcherServlet delegated;
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public OsgiDispatcherServlet() {
		this.delegated = new DispatcherServlet();
		this.delegated.setContextInitializers(new DominoApplicationContextInitializer());

	}
	
	/**
	 * To add context initializers
	 */
	@SuppressWarnings("unchecked")
	public void addContextInitializer(ApplicationContextInitializer<ConfigurableApplicationContext> initializer) {
		this.delegated.setContextInitializers(initializer);
	}
	
	/**
	 * @param config
	 * @throws ServletException
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(final ServletConfig config) throws ServletException {
		// Create a fake servlet config that will force the servlet
		// to load our spring config class
		ServletConfig delegatedConfig = new ServletConfig() {

			/**
			 * @see javax.servlet.ServletConfig#getInitParameter(java.lang.String)
			 */
			@Override
			public String getInitParameter(String name) {
				String value = config.getInitParameter(name);
				String contextClass = config.getInitParameter("contextClass");
				if( AnnotationConfigWebApplicationContext.class.getName().equals(contextClass) && "contextConfigLocation".equals(name) ) {
					return "\r\n" + SpringServletConfig.class.getName() + "\r\n" + value;
				} else 
					return value;
			}

			/**
			 * @see javax.servlet.ServletConfig#getInitParameterNames()
			 */
			@SuppressWarnings({ "rawtypes" })
			@Override
			public Enumeration getInitParameterNames() {
				return config.getInitParameterNames();
			}

			/**
			 * @see javax.servlet.ServletConfig#getServletContext()
			 */
			@Override
			public ServletContext getServletContext() {
				return config.getServletContext();
			}

			/**
			 * @see javax.servlet.ServletConfig#getServletName()
			 */
			@Override
			public String getServletName() {
				return config.getServletName();
			}
		};
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			System.out.println("Initializing osgi dispatcher servlet");
			this.delegated.init(delegatedConfig);
		} finally {
			Thread.currentThread().setContextClassLoader(loader);
		}
	}

	/**
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	public void destroy() {
		delegated.destroy();
	}

	/**
	 * @param o
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return delegated.equals(o);
	}

	/**
	 * @param name
	 * @return
	 * @see javax.servlet.GenericServlet#getInitParameter(java.lang.String)
	 */
	public String getInitParameter(String name) {
		return delegated.getInitParameter(name);
	}

	/**
	 * @return
	 * @see javax.servlet.GenericServlet#getInitParameterNames()
	 */
	@SuppressWarnings({ "rawtypes" })
	public Enumeration getInitParameterNames() {
		return delegated.getInitParameterNames();
	}

	/**
	 * @return
	 * @see javax.servlet.GenericServlet#getServletConfig()
	 */
	public ServletConfig getServletConfig() {
		return this.delegated.getServletConfig();
	}

	/**
	 * @return
	 * @see javax.servlet.GenericServlet#getServletContext()
	 */
	public ServletContext getServletContext() {
		return delegated.getServletContext();
	}

	/**
	 * @return
	 * @see javax.servlet.GenericServlet#getServletInfo()
	 */
	public String getServletInfo() {
		return delegated.getServletInfo();
	}

	/**
	 * @return
	 * @see javax.servlet.GenericServlet#getServletName()
	 */
	public String getServletName() {
		return delegated.getServletName();
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return delegated.hashCode();
	}

	/**
	 * @param msg
	 * @see javax.servlet.GenericServlet#log(java.lang.String)
	 */
	public void log(String msg) {
		delegated.log(msg);
	}

	/**
	 * @param message
	 * @param t
	 * @see javax.servlet.GenericServlet#log(java.lang.String, java.lang.Throwable)
	 */
	public void log(String message, Throwable t) {
		delegated.log(message, t);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @throws ServletException
	 * @throws IOException
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
	 */
	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		delegated.service(arg0, arg1);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return delegated.toString();
	}

	
}
