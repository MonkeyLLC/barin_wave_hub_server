package com.llc.search_service.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.llc.search_service.model.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Arrays;
import java.util.Optional;


@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.llc.*.controller.*.*(..))")
    public void controllerExpress() {

    }

    @AfterReturning(value = "controllerExpress()")
    public void afterReturning(JoinPoint joinPoint) {

        Class<?> clz = joinPoint.getTarget().getClass();

        Log log = new Log();
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            Object requestAttribute = attributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

            if (requestAttribute instanceof RequestFacade requestFacade) {

                // 获取请求路径
                String requestURI = Optional.ofNullable(requestFacade.getRequestURI())
                        .orElse("Unknown URI");
                log.setUri(requestURI);
                log.setType(requestFacade.getMethod());

            }
        }
        log.setClazz(clz.getName());
        log.setArgs(Arrays.toString(joinPoint.getArgs()));
        log.setMethod(joinPoint.getSignature().getName());
        log.setTime(System.currentTimeMillis());
        log.setMessage("AfterReturning log recorded");

        System.out.println("==============================================================================================================================");

        LoggerFactory.getLogger(clz).info(JSON.toJSONString(log, SerializerFeature.PrettyFormat));

        System.out.println("==============================================================================================================================");

    }


}
