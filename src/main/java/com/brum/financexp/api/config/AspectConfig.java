package com.brum.financexp.api.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brum.financexp.api.util.Messages;
import com.brum.financexp.api.util.ServiceException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Component
public class AspectConfig {

	 private static final String METODO_ID = "method";
	    private static final String EXECUTANDO_MSG = "executando";
	    private static final String ERRO_AO_EXECUTAR_MSG = "erro ao executar - campo: {}, status: {} ";
	    private static final String FIM_DE_EXECUÇÃO_MSG = "fim de execução - duração de: ";

	    @Autowired
	    private Messages messages;

	    public AspectConfig() {

	    }

	    @Around("@annotation(com.brum.financexp.api.config.LogExecutionTime)")
	    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

	        return aroundJoinPoint(joinPoint);
	    }

	    @Around("execution(* com.brum.financexp.api.service..*.*(..))")
	    public Object aroundService(ProceedingJoinPoint joinPoint) throws Throwable {

	        return aroundJoinPoint(joinPoint);
	    }

	    @Before("execution(* com.brum.financexp.api.controller..*.*(..))")
	    public void beforeController(JoinPoint joinPoint) {

	        beforeJoinPoint(joinPoint);
	    }

	    @Before("execution(* com.brum.financexp.api.service..*.*(..))")
	    public void beforeService(JoinPoint joinPoint) {

	        beforeJoinPoint(joinPoint);
	    }

	    @After("execution(* com.brum.financexp.api.controller..*.*(..))")
	    public void afterController(JoinPoint joinPoint) {

	        this.setMethodName("");
	    }

	    private Object aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {

	        long start = System.currentTimeMillis();

	        Object proceed;

	        try {

	            proceed = joinPoint.proceed();

	        } catch (ServiceException svcEx) {

	            logServiceException(svcEx);

	            throw svcEx;
	        }

	        this.setMethodName(getMethodName(joinPoint));

	        log.info(FIM_DE_EXECUÇÃO_MSG + (System.currentTimeMillis() - start) + "ms");

	        return proceed;
	    }

	    private void beforeJoinPoint(JoinPoint joinPoint) {

	        this.setMethodName(getMethodName(joinPoint));

	        this.logJoinPoint(joinPoint.getArgs());
	    }

	    private void logJoinPoint(Object[] joinPointArgs) {

	        if (joinPointArgs.length == 0) {

	            log.info(EXECUTANDO_MSG);

	        } else {

	            String args = "#";

	            for (Object arg : joinPointArgs) {

	                args += ", " + arg.getClass().getSimpleName() + ":" + getValueOfRequest(arg);
	            }

	            log.info(EXECUTANDO_MSG + " - " + args.replace("#, ", ""));
	        }
	    }

	    private void logServiceException(ServiceException ex) {

	        String message = messages.get(ex.getChave());

	        if (message == null) {

	            log.info(ERRO_AO_EXECUTAR_MSG + ", chave: {}, ", ex.getCampo(), ex.getStatus(), ex.getChave());

	        } else {

	            log.info(ERRO_AO_EXECUTAR_MSG + ", message: {}", ex.getCampo(), ex.getStatus(), message);
	        }
	    }

	    private String getMethodName(JoinPoint joinPoint) {
	        return joinPoint.getSignature().toShortString().replace(joinPoint.getArgs().length > 0 ? "(..)" : "()", "");
	    }

	    private void setMethodName(String metodo) {

	        MDC.put(METODO_ID, metodo + " ");
	    }

	    private String getValueOfRequest(Object value) {

	        if(value == null) return "";

	        try {

	            return value.toString();

	        } catch (Exception e) {

	            return "erro ao obter json do argumento";
	        }
	    }
}