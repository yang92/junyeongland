package com.pknu.bbs.aop;

import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Named
@Aspect
public class LoginAspect {
	
	@Pointcut("execution(* com.pknu.bbs.controller.BBSController.writeForm(..))")
	public void writeForm(){}
	
	@Around(value="writeForm()")
	public Object writeLogin(ProceedingJoinPoint pjt) throws Throwable{
		HttpSession session = null;
		for(Object obj : pjt.getArgs()){
			if(obj instanceof HttpSession){
				session = (HttpSession)obj;
			}
			
		}
		
		if(session.getAttribute("id")==null){
			session.setAttribute("writeForm", "ok");
			return "writeLogin";
//			res.sendRedirect("/bbs/loginForm.bbs");
		}
		
		Object result = pjt.proceed();
		return result;
	}
}
