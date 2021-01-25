package com.tr.mita;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ApplicationStart extends SpringBootServletInitializer {
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
       
        return builder.sources(Application.class);
    }
	
	public ApplicationStart() {
		super();
		setRegisterErrorPageFilter(false);
	}

}
