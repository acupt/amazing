package com.acupt.aspect;

import com.acupt.service.domain.ServiceResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by liujie on 2017/8/30.
 */
@Aspect
@Component
public class ServiceAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Around("execution(com.acupt.service.domain.ServiceResult com.acupt.service..*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println(this);
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            return ServiceResult.newFailed(e, "something wrong");
        }
    }
}
