package com.k2data.kbc.audit.aspects;

import com.alibaba.fastjson.JSON;
import com.k2data.kbc.audit.Utils.LogUtil;
import com.k2data.kbc.audit.Utils.RequestUtil;
import com.k2data.kbc.audit.model.AuditLog;
import com.k2data.kbc.audit.model.ExceptionLog;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * before：按order值有小到大的顺序执行
 * after：按order值由大到小的顺序执行
 */

@Aspect
@Order(2)
@Component
public class AppLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AppLogAspect.class);
    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    private AuditLog log = new AuditLog();
    private ExceptionLog exLog = new ExceptionLog();

    @Autowired
    private HttpServletRequest request;

    /**
     * 设置切点
     */
    @Pointcut("execution(* com.k2data.kbc..*Controller.*(..))")
    public void serviceStatistics() {
    }

    /**
     * 前置通知
     */
    @Before("serviceStatistics()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        logger.info("doBefore():{}", joinPoint.toString());
        threadLocal.set(System.currentTimeMillis());
        Map<String, Object> joinPointInfoMap = RequestUtil.getJoinPointInfoMap(joinPoint);
        log.setRequestUrl(request.getRequestURL().toString());
        log.setRequestStartTime(new Date(System.currentTimeMillis()));
        log.setClassMethodName(joinPointInfoMap.get("classMethodName").toString());
        log.setClassMethodPath(joinPointInfoMap.get("classMethodPath").toString());
        log.setIp(RequestUtil.getRequestIp(request));
        log.setRequestMethod(request.getMethod());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setParams(joinPointInfoMap.get("paramMap").toString());
    }

    @After("serviceStatistics()")
    public void doAfter(JoinPoint joinPoint) {
        logger.info("doAfter():{}", joinPoint.toString());
    }

    @AfterReturning(value = "serviceStatistics()", returning = "retrunValue")
    public void doAfterReturning(Object retrunValue) {
        logger.info("doAfterReturning(){}");
        log.setReturnTime(new Date(System.currentTimeMillis()));
        log.setRequestFinshTime(System.currentTimeMillis() - threadLocal.get());
        LogUtil.saveLog(log);
    }

    @AfterThrowing(value = "serviceStatistics()", throwing = "e")
    public void doAfterThrowing(Throwable e) {
        logger.info("doAfterThrowing(){}" + e);
        exLog.setExceptionJson(JSON.toJSONString(e));
        exLog.setExceptionCreateTime(new Date(System.currentTimeMillis()));
        exLog.setExceptionMessage(e.getMessage());
        LogUtil.saveExLogRunnable(exLog);
    }
}
