package com.brum.financexp.api.config;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.brum.financexp.api.util.RequestUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Order(1)
public class RequestLogger implements Filter {

    private static final String REQUEST_SESSION = "Request - [{}]{} - session: {} - headers: {}";
    private static final String RESPONSE_STATUS = "Response - status:{}";

    private static String REQUEST_IP = "requestIp";
    private static String REQUEST_ID = "requestId";

    @Override
    public void doFilter(ServletRequest request,  ServletResponse response, FilterChain chain) throws IOException, ServletException {
  
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        MDC.put(REQUEST_IP, RequestUtil.getClientIp() + " ");
        MDC.put(REQUEST_ID, UUID.randomUUID().toString().toUpperCase().replace("-", "") + " ");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode headers = objectMapper.createObjectNode();
        
        Enumeration<String> headerEnumeration = httpServletRequest.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String name = headerEnumeration.nextElement();
            headers.set(name, objectMapper.convertValue(httpServletRequest.getHeader(name), JsonNode.class));
        };

        log.info(REQUEST_SESSION, httpServletRequest.getMethod(), httpServletRequest.getRequestURI(), httpServletRequest.getSession().getId(), headers.toString());
        
        chain.doFilter(request, response);

        log.info(RESPONSE_STATUS, ((HttpServletResponse) response).getStatus());
    }
}