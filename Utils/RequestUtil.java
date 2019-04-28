package com.k2data.kbc.audit.Utils;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.javassist.ClassClassPath;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.javassist.Modifier;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.ibatis.javassist.bytecode.CodeAttribute;
import org.apache.ibatis.javassist.bytecode.LocalVariableAttribute;
import org.apache.ibatis.javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;

public class RequestUtil {

    public static String getRequestIp(HttpServletRequest request) throws Exception {
        if (null == request) {
            throw new Exception("HttpServletRequest is null");
        }
        String ip = request.getHeader("x-forwarded-for");
        if (null == ip || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (null == ip || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (null == ip || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                return str;
            }
        }
        return ip;
    }


    public static Map<String, Object> getJoinPointInfoMap(JoinPoint joinPoint)
        throws NotFoundException, ClassNotFoundException {
        Map<String, Object> joinPointInfoMap = new HashMap<>();
        String classPath = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        joinPointInfoMap.put("classMethodPath", classPath);
        joinPointInfoMap.put("classMethodName", methodName);
        Class<?> clazz = null;
        CtMethod ctMethod = null;
        LocalVariableAttribute attr = null;
        int len = 0;
        int pos = 0;
        try {
            clazz = Class.forName(classPath);
            String clazzName = clazz.getName();
            ClassPool pool = ClassPool.getDefault();
            ClassClassPath classClassPath = new ClassClassPath(clazz);
            pool.insertClassPath(classClassPath);
            CtClass ctClass = pool.get(clazzName);
            ctMethod = ctClass.getDeclaredMethod(methodName);
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (null == attr) {
                return joinPointInfoMap;
            }
            len = ctMethod.getParameterTypes().length;
            pos = Modifier.isStatic(ctMethod.getModifiers()) ? 0 : 1;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClassNotFoundException("Failed to get class instance");
        } catch (NotFoundException e) {
            e.printStackTrace();
            throw new NotFoundException("Parameter type not found");
        }
        Map<String, Object> paramMap = new HashMap<>();
        Object[] paramsArgsValues = joinPoint.getArgs();
        String[] paramsArgsNames = new String[len];
        for (int i = 0; i < len; i++) {
            paramsArgsNames[i] = attr.variableName(i + pos);
            String paramsArgsName = attr.variableName(i + pos);
            if (paramsArgsName.equalsIgnoreCase("request") || paramsArgsName.equalsIgnoreCase("response")
                || paramsArgsName.equalsIgnoreCase("session")) {
                break;
            }
            Object paramsArgsValue = paramsArgsValues[i];
            paramMap.put(paramsArgsName, paramsArgsValue);
        }
        joinPointInfoMap.put("paramMap", JSON.toJSONString(paramMap));
        return joinPointInfoMap;
    }

}
