package com.anyu.studydemo.controller.advice;

import com.anyu.studydemo.controller.advice.annotation.CommonResultType;
import com.anyu.studydemo.model.CommonResult;
import com.anyu.studydemo.model.enums.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.annotation.Annotation;

//@RestControllerAdvice
public class RestCommonResultAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerFactory.getLogger(RestCommonResultAdvice.class);


    private static final Class<? extends Annotation> ANNOTATION_TYPE = CommonResultType.class;

    /**
     * 判断类或者方法是否使用了 {@link CommonResultType}注解
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ANNOTATION_TYPE) || returnType.hasMethodAnnotation(ANNOTATION_TYPE);
    }

    /**
     * TODO 无法对String封装，报错 ExceptionHandler: class com.anyu.common.entity.CommonResult cannot be cast to class java.lang.String
     * 如果类或者方法上有{@link CommonResultType}
     * @return CommonResult {@link CommonResult}
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //判断是否已经统一格式
        if (body instanceof CommonResult) {
            return body;
        }
        return CommonResult.with(ResultType.SUCCESS,body);
    }

}