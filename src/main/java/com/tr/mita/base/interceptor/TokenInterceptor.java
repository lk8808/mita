package com.tr.mita.base.interceptor;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tr.mita.base.entity.RespData;
import com.tr.mita.base.entity.Rtsts;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@Component
public class TokenInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisUtil redisUtil;
	
	Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {

		String token = request.getHeader("Token");
		if (token != null && redisUtil.get(token) != null) {
			redisUtil.expire(token,2L, TimeUnit.HOURS);
			return true;
		}
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");

		PrintWriter printWriter = response.getWriter();
		RespData respData = new RespData(new Rtsts("100003", "登录状态失效!"));
		String respStr = new Gson().toJson(respData);

        printWriter.write(respStr);
        printWriter.close();

		return false;
	}

}