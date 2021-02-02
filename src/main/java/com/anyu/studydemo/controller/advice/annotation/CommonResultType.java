package com.anyu.studydemo.controller.advice.annotation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

@Documented
@Inherited
@ResponseBody
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface CommonResultType {
}
