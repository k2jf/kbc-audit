package com.k2data.kbc.audit.Interceptor;

import com.k2data.kbc.audit.Utils.LogUtil;
import com.k2data.kbc.audit.Utils.RequestUtil;
import com.k2data.kbc.audit.model.AuditLog;
import java.lang.reflect.Method;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.LoggerFactory;

public class AuditLogInterceptor implements HandlerInterceptor {

    private ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    private static final Logger logger = LoggerFactory.getLogger(AuditLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        logger.info("preHandle():{}");
        threadLocal.set(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 因为api的@ControllerAdvice注解自定义了异常处理，在DispatcherServlet源码987行doDispatch()中的
     * *   1055行processDispatchResult()中的1109行processHandlerException()方法导致exMv不为null丢失ex信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
        Object handler, Exception e) throws Exception {
        logger.info("afterCompletion():{}");
        AuditLog log = new AuditLog();
        HandlerMethod object = (HandlerMethod) handler;
        Method method = object.getMethod();
        log.setClassMethodName(method.getName());
        log.setClassMethodPath(method.getDeclaringClass().getName() + "." + method.getName());
        log.setIp(RequestUtil.getRequestIp(request));
        log.setParams(request.getParameterMap());
        log.setRequestMethod(request.getMethod());
        log.setRequestUrl(request.getRequestURL().toString());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setRequestStartTime(new Date(threadLocal.get()));
        log.setRequestFinshTime(System.currentTimeMillis() - threadLocal.get());
        log.setReturnTime(new Date());
        LogUtil.saveLog(log);
    }

}
