package com.springmvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//getrootconfigclass se khai bao file cau hinh cho tat ca servlet -> getservletconfigclass se ke thua getrootconfigclass
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() { //cau hinh chung
		return new Class[] { WebMvcConfig.class, AppContext.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() { //cau hinh rieng
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
	@Override
	protected String getServletName() {
		return "dispatcher";
	}

}
