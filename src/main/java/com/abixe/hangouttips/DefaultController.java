/*
 * DefaultController.java
 * Create Date: Aug 11, 2019
 * Initial-Author: Janos Aron Kiss
 */
package com.abixe.hangouttips;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

/**
 * This class provides some useful functionality to the offsprings.
 * @author kjaron83
 */
public abstract class DefaultController {

    @Value("${root}")
    private String root;

    @Autowired
    private HttpServletRequest request;

    /**
     * Returns an absolute URL from the specified path, with a redirect prefix.
     */
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
