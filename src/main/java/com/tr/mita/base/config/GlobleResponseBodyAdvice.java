package com.tr.mita.base.config;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.comm.entity.Rtsts;
import com.tr.mita.comm.exception.RespException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.io.IOException;
import java.util.Map;

@ControllerAdvice
public class GlobleResponseBodyAdvice implements ResponseBodyAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 此处true代表执行当前advice的业务，false代表不执行
        return true;
    }

    /**
     * 统一封装返回结果
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof RespData) {
            return body;
        } else {
            RespData respData = new RespData();
            respData.setRtdata(body);
            return respData;
        }
    }

    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RespData errorHandler(Exception e) {
        logger.error(e.getMessage());
        RespData respData = new RespData(new Rtsts("999999", e.getMessage()));
        return respData;
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = RespException.class)
    public RespData handlerRespException(RespException e) {
        logger.error(e.getMessage());
        RespData respData = new RespData(new Rtsts(e.getErrcode(), e.getMessage()));
        return respData;
    }
}
