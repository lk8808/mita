package com.tr.ibs.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tr.ibs.utils.RandomValidateCodeUtil;

@Controller
@RequestMapping("/verify")
public class VerifyController {
	
	private final static Logger logger = LoggerFactory.getLogger(VerifyController.class);
	
	/**
	 * 生成验证码
	 */
	@GetMapping(value = "/getVerify")
	public void getVerify(HttpServletRequest request, HttpServletResponse response) {
	    try {
	        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
	        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expire", 0);
	        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
	        randomValidateCode.getRandcode(request, response);//输出验证码图片方法
	    } catch (Exception e) {
	        logger.error("获取验证码失败>>>>   ", e);
	    }
	}
	
	/**
	 * 忘记密码页面校验验证码
	 */
	@PostMapping(value = "/checkVerify")
	@ResponseBody
	public boolean checkVerify(@RequestParam String inputStr, HttpSession session) {
	    try{
	        //从session中获取随机数
	        String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
	        if (random == null) {
	            return false;
	        }
	        if (random.equals(inputStr)) {
	            return true;
	        } else {
	            return false;
	        }
	    }catch (Exception e){
	        logger.error("验证码校验失败", e);
	        return false;
	    }
	}
	
}
