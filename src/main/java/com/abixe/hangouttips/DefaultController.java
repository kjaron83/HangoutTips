package com.abixe.hangouttips;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

public abstract class DefaultController {

	@Value("${root}")
	private String root;
	
	@Autowired
	private HttpServletRequest request;	
	
	public String redirect(@Nullable String to) {
		if ( to == null )
			to = "/";
		
		return new StringBuilder("redirect:")
				.append(request.isSecure() ? "https" : "http")
				.append("://")
				.append(root)
				.append(to)
				.toString();
	}
	
}
