package com.ps.security.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequirePermission {

    String value() default "";

    String[] permissions() default {};

    String message() default "Insufficient permissions";

}
