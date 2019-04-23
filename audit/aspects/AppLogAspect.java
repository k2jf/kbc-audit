package com.k2data.kbc.audit.aspects;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.k2data.kbc.audit.Utils.LogUtils;
import com.k2data.kbc.audit.Utils.RequestUtil;
import com.k2data.kbc.audit.annotation.Operation;
import com.k2data.kbc.audit.dao.AuditLogMapper;
import com.k2data.kbc.audit.model.AuditLog;
import com.k2data.kbc.audit.model.ExceptionLog;
import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.ibatis.javassist.NotFoundException;
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
    @Pointcut(value = "@annotation(operation)")
    public void serviceStatistics(Operation operation) {
    }

    /**
     * 前置通知
     */
    @Before("serviceStatistics(operation)")
    public void doBefore(JoinPoint joinPoint, Operation operation) throws Exception {
        logger.info("doBefore():{}", joinPoint.toString());
        //绑定当前线程计算耗时
        threadLocal.set(System.currentTimeMillis());
        Map<String, Object> joinPointInfoMap = RequestUtil.getJoinPointInfoMap(joinPoint);
        log.setRequestUrl(request.getRequestURL().toString());
        log.setRequestStartTime(new Date(System.currentTimeMillis()));
        log.setClassMethodName(joinPointInfoMap.get("classMethodName").toString());//具体路径
        log.setClassMethodPath(joinPointInfoMap.get("classMethodPath").toString());//具体路径
        log.setIp(RequestUtil.getRequestIp(request));
        log.setOperation(operation.value());
        log.setRequestMethod(request.getMethod());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setParams(joinPointInfoMap.get("paramMap").toString());
    }

    @After("serviceStatistics(operation)")
    public void doAfter(JoinPoint joinPoint, Operation operation) {
        logger.info("doAfter():{}", joinPoint.toString());
    }

    /**
     * 返回通知
     */
    @AfterReturning(value = "serviceStatistics(operation)", returning = "retrunValue")
    public void doAfterReturning(Operation operation, Object retrunValue) {
        logger.info("doAfterReturning(){}");
        log.setReturnTime(new Date(System.currentTimeMillis()));
        log.setReturnData(JSON.toJSONString(retrunValue, SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteMapNullValue));
        log.setRequestFinshTime(System.currentTimeMillis() - threadLocal.get());
        LogUtils.saveLog(log);
    }

    /**
     * 异常通知
     */
    @AfterThrowing(value = "serviceStatistics(operation)", throwing = "e")
    public void doAfterThrowing(Operation operation, Throwable e) {
        logger.info("doAfterThrowing(){}" + e);
        exLog.setcExceptionJson(JSON.toJSONString(e, SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteMapNullValue));
        exLog.setcExceptionCreateTime(new Date(System.currentTimeMillis()));
        exLog.setcExceptionMessage(e.getMessage());
        LogUtils.saveExLogRunnable(exLog);
    }
}
