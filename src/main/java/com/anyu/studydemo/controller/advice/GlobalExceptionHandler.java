package com.anyu.studydemo.controller.advice;


import com.anyu.studydemo.exception.GlobalException;
import com.anyu.studydemo.model.CommonResult;
import com.anyu.studydemo.model.enums.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

/**
*统一异常处理
* @author Anyu
* @since 2021/2/4 上午11:03
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    /**
     * 提供对标准Spring MVC异常的处理 {@link org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler handleException()}
     *
     * @param ex      the target exception
     * @param request the current request
     */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CommonResult<?>> exceptionHandler(Exception ex, WebRequest request) {
        logger.error("ExceptionHandler: {}", ex.getMessage());
        var headers = new HttpHeaders();
        if (ex instanceof GlobalException) {
            return handleGlobalException((GlobalException) ex, headers, request);
        }
        // TODO:  这里可以自定义其他的异常拦截
        return this.handleException(ex, headers, request);
    }

    /**
     * 对GlobalException类返回返回结果的处理
     */
    protected ResponseEntity<CommonResult<?>> handleGlobalException(GlobalException ex, HttpHeaders headers, WebRequest request) {
        var body = CommonResult.with(ex.getResultType());
        var httpStatus = ex.getHttpStatus();
        return handleExceptionInternal(ex, body, headers, httpStatus, request);
    }

    /**
     * 异常类的统一处理
     */
    protected ResponseEntity<CommonResult<?>> handleException(Exception ex, HttpHeaders headers, WebRequest request) {
        var body = CommonResult.with(ResultType.FAILED);
        return this.handleExceptionInternal(ex, body, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler#handleExceptionInternal(java.lang.Exception, java.lang.Object, org.springframework.http.HttpHeaders, org.springframework.http.HttpStatus, org.springframework.web.context.request.WebRequest)
     * <p>
     * A single place to customize the response body of all exception types.
     * <p>The default implementation sets the {@link WebUtils#ERROR_EXCEPTION_ATTRIBUTE}
     * request attribute and creates a {@link ResponseEntity} from the given
     * body, headers, and status.
     */
    protected ResponseEntity<CommonResult<?>> handleExceptionInternal(
            Exception ex, CommonResult<?> body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
