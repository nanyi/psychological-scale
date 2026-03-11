package com.ps.security.aspect;

import com.ps.security.annotation.RequireLogin;
import com.ps.security.annotation.RequirePermission;
import com.ps.security.service.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class PermissionAspect {

    @Around("@annotation(com.ps.security.annotation.RequireLogin) || @within(com.ps.security.annotation.RequireLogin)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new SecurityException("Please login first");
        }
        return joinPoint.proceed();
    }

    @Around("@annotation(com.ps.security.annotation.RequirePermission)")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequirePermission annotation = method.getAnnotation(RequirePermission.class);

        if (annotation == null) {
            annotation = method.getDeclaringClass().getAnnotation(RequirePermission.class);
        }

        if (annotation != null) {
            String[] permissions = annotation.permissions();
            if (permissions.length > 0) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.getAuthorities() != null) {
                    boolean hasPermission = Arrays.stream(permissions)
                            .anyMatch(perm -> authentication.getAuthorities().stream()
                                    .anyMatch(auth -> auth.getAuthority().equals(perm)));

                    if (!hasPermission) {
                        throw new SecurityException(annotation.message());
                    }
                }
            }
        }

        return joinPoint.proceed();
    }
}
