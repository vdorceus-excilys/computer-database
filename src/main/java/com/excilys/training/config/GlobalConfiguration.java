package com.excilys.training.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class GlobalConfiguration extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	/*
	 * @Override public void onStartup(ServletContext servletContext) throws
	 * ServletException { AnnotationConfigWebApplicationContext rootContext = new
	 * AnnotationConfigWebApplicationContext();
	 * rootContext.register(ConfigurationSpringMVC.class);
	 * rootContext.setServletContext(servletContext); ServletRegistration.Dynamic
	 * servlet = servletContext.addServlet("dispacher", new
	 * DispatcherServlet(rootContext)); servlet.setLoadOnStartup(1);
	 * servlet.addMapping("/");
	 * 
	 * }
	 */
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
	    boolean done = registration.setInitParameter("throwExceptionIfNoHandlerFound", "true"); // -> true
	    if(!done) throw new RuntimeException();
	}
	@Override
    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = (DispatcherServlet) super.createDispatcherServlet(servletAppContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
	
	

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ConfigurationSpringMVC.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

}
