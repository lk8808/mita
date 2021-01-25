package com.tr.mita.base.aop;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tr.mita.base.entity.RequestLog;
import com.tr.mita.base.entity.UserObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志切面类
 * @author liukai
 *
 */
//@Aspect
@Component
public class LogAspect {
	
	@Pointcut("execution(public * com.tr.mita.*.controller.*.*(..))")
    public void controllerAspect() {
    }
	
	@Around("controllerAspect()")
	public Object doAround(ProceedingJoinPoint call) {
		Object object = null;
		
		Logger logger = LoggerFactory.getLogger(call.getSignature().getDeclaringTypeName());
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = requestAttributes.getRequest();
	    HttpSession session = request.getSession();
	    UserObject userObject = (UserObject)session.getAttribute("userObject");
	    String requestcode = UUID.randomUUID().toString();
	    //初始化请求日志
	    RequestLog requestLog = new RequestLog();
	    requestLog.setRequestcode(requestcode);
	    requestLog.setType("BEGIN");
	    requestLog.setIp(request.getRemoteAddr());
	    requestLog.setUrl(request.getRequestURI());
	    if (userObject != null && userObject.getUser() != null) {
	    	requestLog.setUsername(userObject.getUser().getUsername());
	    } 
	    requestLog.setMethod(call.getSignature().getName());
	    //请求执行前记录日志
	    logger.info(requestLog.getRequestLog());
		try {
			//执行请求
			object = call.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			//请求异常时记录日志
			requestLog.setType("EXCEPTION");
			requestLog.setExt(e.getMessage());
			logger.error(requestLog.getRequestLog());
		} 
		//请求执行后记录日志
	    requestLog.setType("END");
	    logger.info(requestLog.getRequestLog());
	    
		return object;
	}
	
}
