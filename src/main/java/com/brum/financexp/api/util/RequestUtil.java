package com.brum.financexp.api.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {

    private static final String COMMA = ",";
    private static final String UNKNOWN = "UNKNOWN";

    private static final String[] IP_HEADER_CANDIDATES = { 
        "X-FORWARDED-FOR", "PROXY-CLIENT-IP", "WL-PROXY-CLIENT-IP",
        "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR" };

    public static String getClientIp() {

        RequestAttributes reqAttr = RequestContextHolder.getRequestAttributes();

        if (reqAttr == null) {
            return "0.0.0.0";
        }

        HttpServletRequest request = ((ServletRequestAttributes) reqAttr).getRequest();

        for (String header: IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (Strings.isNotBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                return ip.split(COMMA)[0];
            }
        }

        return request.getRemoteAddr();
    }
}