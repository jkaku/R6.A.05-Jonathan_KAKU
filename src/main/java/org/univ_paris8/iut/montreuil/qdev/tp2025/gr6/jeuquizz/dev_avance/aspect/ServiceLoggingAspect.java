package org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(ServiceLoggingAspect.class);
    @Around("execution(* org.univ_paris8.iut.montreuil.qdev.tp2025.gr6.jeuquizz.dev_avance.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("[AOP] DÉBUT de la méthode : {}.{}", className, methodName);
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            log.info("[AOP] FIN de la méthode : {}.{} (Temps: {} ms)", className, methodName, duration);
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            log.error("[AOP] ERREUR dans {}.{} (Temps: {} ms) - Exception: {}", className, methodName, duration, ex.getMessage());
            throw ex;
        }
    }
}