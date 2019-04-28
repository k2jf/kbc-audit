package com.k2data.kbc.audit.Utils;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
public class SpringContextHolderUtil implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolderUtil.class);

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        assertContextInjected();
        return getApplicationContext().getBean(name, clazz);
    }

    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("Clear SpringContextHolder of ApplicationContext:" + applicationContext);
        }
        applicationContext = null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.info("Inject ApplicationContext to SpringContextHolderUtil:{}", applicationContext);
        if (SpringContextHolderUtil.applicationContext != null) {
            logger
                .info("ApplicationContext in SpringContextHolderUtil is overwritten. SpringContextHolderUtil was:"
                    + SpringContextHolderUtil
                    .applicationContext);
        }
        SpringContextHolderUtil.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        SpringContextHolderUtil.clearHolder();
    }

    private static void assertContextInjected() {
        Validate.validState(applicationContext != null,
            "applicaitonContext Property is not injected, check if it is defined in the configuration file SpringContextHolderUtil.");
    }
}