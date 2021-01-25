package com.tr.mita.base.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tr.mita.base.entity.RespData;
import com.tr.mita.base.entity.Rtsts;
import com.tr.mita.base.exception.RespException;
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

/**
 * 响应统一处理
 */
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
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof String) {
            ObjectMapper om = new ObjectMapper();
            RespData respData = new RespData();
            respData.setRtdata(body);
            try {
                return om.writeValueAsString(respData);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if (body instanceof RespData) {
            return body;
        }
        RespData respData = new RespData();
        respData.setRtdata(body);
        return respData;
    }


    /**
     * 全局异常处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public RespData errorHandler(Exception e) {
        e.printStackTrace();
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
        RespData respData = new RespData(new Rtsts(e.getErrcode(), e.getMessage()));
        return respData;
    }
}
