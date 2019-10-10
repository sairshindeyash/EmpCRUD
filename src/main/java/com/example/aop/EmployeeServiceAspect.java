package com.example.aop;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmployeeServiceAspect {
	static Logger logger=Logger.getLogger(EmployeeServiceAspect.class.getName());
	
	@Before(value="execution(* com.example.service.EmployeeService.*(..))")
	public void beforeAdvice(JoinPoint joinPoint){
		System.out.println("beforse service method got called");
		logger.info("beforse service method got called");
	}
	
	@After(value="execution(* com.example.service.EmployeeService.*(..))")
	public void afterAdvice(JoinPoint joinPoint){
		logger.info("after service method got called");
	}
	
	@AfterReturning(value="execution(* com.example.service.EmployeeService.*(..))")
	public void afterReturningAdvice(JoinPoint joinPoint){
		logger.info("AfterReturning service method got called");
	}
}
