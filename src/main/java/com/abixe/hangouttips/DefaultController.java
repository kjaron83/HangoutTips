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

    private static final String PROTOCOL_HTTP = "http";
    private static final String PROTOCOL_HTTPS = "https";

    private static String REDIRECT_PREFIX = "redirect:";

    @Value("${root}")
    private String root;

    @Autowired
    private HttpServletRequest request;

    /**
     * Returns an absolute URL from the specified path.
     */
    public String getAbsoluteUrl(@Nullable String to) {
        return getAbsoluteUrl(to, null);
    }

    /**
     * Returns an absolute URL from the specified path, with a prefix.
     */
    public String getAbsoluteUrl(@Nullable String to, @Nullable String prefix) {
        if ( to == null )
            to = "/";

        int size = PROTOCOL_HTTPS.length() + root.length() + to.length();
        if ( prefix != null )
            size += prefix.length();

        StringBuilder sb = new StringBuilder(size);
        if ( prefix != null )
            sb.append(prefix);

        return sb
                .append(request.isSecure() ? PROTOCOL_HTTPS : PROTOCOL_HTTP)
                .append("://")
                .append(root)
                .append(to)
                .toString();
    }

    /**
     * Returns an absolute URL from the specified path, with a {@link #REDIRECT_PREFIX} prefix.
     */
    public String redirect(@Nullable String to) {
        return getAbsoluteUrl(to, REDIRECT_PREFIX);
    }

}
