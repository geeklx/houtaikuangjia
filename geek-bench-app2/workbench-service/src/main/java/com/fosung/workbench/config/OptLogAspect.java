package com.fosung.workbench.config;

import com.alibaba.fastjson.JSON;
import com.fosung.framework.common.util.UtilIp;
import com.fosung.framework.web.common.interceptor.support.AppServletContextHolder;
import com.fosung.workbench.entity.common.ManageOptLogEntity;
import com.fosung.workbench.service.common.ManageOptLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 切面处理类，操作日志异常日志记录处理
 *
 * @author 高健
 * @date 2020/12/31
 */
@Aspect
@Component
public class OptLogAspect {

    /**
     * 描述:  操作日志服务层
     * @createDate: 2021/10/15 16:52
     * @author: gaojian
     */
    @Autowired
    private ManageOptLogService manageOptLogService;

    /**
     * 设置操作日志切入点 记录操作日志 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.fosung.workbench.config.OptLog)")
    public void optLogPointCut() {
    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint  切入点
     * @param respParams 返回值
     */
    @AfterReturning(value = "optLogPointCut()", returning = "respParams")
    public void saveOptLog(JoinPoint joinPoint, Object respParams) {

        // 1. 获取HttpServletRequest的信息
        HttpServletRequest request = AppServletContextHolder.getHttpServletRequest();
        ManageOptLogEntity optLog = new ManageOptLogEntity();
        try {

            // 2. 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // 3. 获取操作
            OptLog optLogAnnotation = method.getAnnotation(OptLog.class);
            if (optLogAnnotation != null) {
                optLog.setOptModule(optLogAnnotation.optModule());
                optLog.setOptType(optLogAnnotation.optType());
                optLog.setOptName(optLogAnnotation.optName());
            }

            // 4. 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName;
            optLog.setReqMethod(methodName);

            // 5. 请求的参数
            if(joinPoint.getArgs() == null || joinPoint.getArgs()[0] == null){
                optLog.setReqParam(null);
            }else{
                String params = JSON.toJSONString(joinPoint.getArgs()[0]);
                optLog.setReqParam(params);
            }
            optLog.setReqIp(UtilIp.getHostInfo().getIpAddress());
            optLog.setReqUri(request.getRequestURI());

            // 6. 返回结果
            optLog.setResParam(JSON.toJSONString(respParams));
            manageOptLogService.save(optLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "optLogPointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {

        // 1. 获取HttpServletRequest的信息
        HttpServletRequest request = AppServletContextHolder.getHttpServletRequest();
        ManageOptLogEntity optLog = new ManageOptLogEntity();

        // 2. 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 3. 获取操作
        OptLog optLogAnnotation = method.getAnnotation(OptLog.class);
        if (optLogAnnotation != null) {
            optLog.setOptModule(optLogAnnotation.optModule());
            optLog.setOptType("error");
            optLog.setOptName(optLogAnnotation.optName());
        }

        // 4. 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        optLog.setReqMethod(methodName);

        // 5. 请求的参数
        String params = JSON.toJSONString(joinPoint.getArgs());
        optLog.setReqParam(params);
        optLog.setReqIp(UtilIp.getHostInfo().getIpAddress());
        optLog.setReqUri(request.getRequestURI());

        // 6. 返回结果
        optLog.setResParam(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
        manageOptLogService.save(optLog);
    }

    /**
     * 转换异常信息为字符串
     *
     * @param exceptionName    异常名称
     * @param exceptionMessage 异常信息
     * @param elements         堆栈信息
     */
    public String stackTraceToString(String exceptionName, String exceptionMessage, StackTraceElement[] elements) {
        StringBuffer strBuff = new StringBuffer();
        for (StackTraceElement stet : elements) {
            strBuff.append(stet + "\n");
        }
        String message = exceptionName + ":" + exceptionMessage + "\n\t" + strBuff.toString();
        return message;
    }
}