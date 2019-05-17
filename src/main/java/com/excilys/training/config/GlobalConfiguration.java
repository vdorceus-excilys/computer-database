package com.excilys.training.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class GlobalConfiguration implements WebApplicationInitializer{
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ConfigurationSpringMVC.class);
		rootContext.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispacher", new DispatcherServlet(rootContext));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		
	}

}
