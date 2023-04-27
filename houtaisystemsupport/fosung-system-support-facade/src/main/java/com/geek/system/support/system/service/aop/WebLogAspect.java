package com.geek.system.support.system.service.aop;

import com.alibaba.fastjson.JSON;
import com.fosung.framework.common.secure.auth.AppUserDetails;
import com.fosung.framework.common.util.UtilIp;
import com.fosung.framework.web.common.interceptor.support.AppServletContextHolder;
import com.fosung.framework.web.http.AppIBaseController;
import com.fosung.framework.web.http.ResponseParam;
import com.geek.system.support.system.anno.SysLog;
import com.geek.system.support.system.entity.sys.SysLogManageEntity;
import com.geek.system.support.system.entity.sys.SysUserEntity;
import com.geek.system.support.system.service.sys.SysLogManageService;
import com.geek.system.support.system.service.sys.SysUserService;
import com.geek.system.support.util.IpUtil;
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
 * 描述: 日志操作切面层
 * @author fuhao
 * @date 2022/1/13 9:55
 **/
@Aspect
@Component
public class WebLogAspect extends AppIBaseController {

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Autowired
    private SysLogManageService sysLogManageService;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 定义切入点，以controller下所有包的请求为切入点
     */
    @Pointcut("@annotation(com.geek.system.support.system.anno.SysLog)")
    public void webLog(){

    }

    /**
     * 正常返回通知，拦截用户操作日志，连接点正常执行完成后执行， 如果连接点抛出异常，则不会执行
     *
     * @param joinPoint  切入点
     * @param respParams 返回值
     */
    @AfterReturning(value = "webLog()", returning = "respParams")
    public void saveOptLog(JoinPoint joinPoint, ResponseParam respParams) {

        // 1. 获取HttpServletRequest的信息
        HttpServletRequest request = AppServletContextHolder.getHttpServletRequest();
        SysLogManageEntity optLog = new SysLogManageEntity();
        try {

            // 2. 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();

            // 3. 获取操作
            SysLog optLogAnnotation = method.getAnnotation(SysLog.class);
            if (optLogAnnotation != null) {
                optLog.setOptModule(optLogAnnotation.optModule());
                optLog.setOptName(optLogAnnotation.optName());
                optLog.setOptType(optLogAnnotation.optType());
                optLog.setOptValue(optLogAnnotation.optValue());
            }
            // 4. 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            methodName = className + "." + methodName + "()";
            optLog.setMethod(methodName);
            // 5. ip
            optLog.setOperIp(IpUtil.getIpAddress(request));
            // 6. 请求地址
            optLog.setOperUrl(request.getRequestURI().toString());
            // 7. 请求方式
            optLog.setRequestMethod(request.getMethod());
            // 8. 请求的参数
            String params = JSON.toJSONString(joinPoint.getArgs()[0]);
            optLog.setOperParam(params);
            // 9. 返回结果
            optLog.setJsonResult(JSON.toJSONString(respParams));
            // 10. 操作结果
            Boolean success = (Boolean) respParams.get("success");
            optLog.setStatus(success);
            // 11. 操作人
            AppUserDetails loginAppUserDetails = null;
            try {
                loginAppUserDetails = getLoginAppUserDetails();
            }catch (Exception e){
                loginAppUserDetails = null;
            }
            if(loginAppUserDetails==null){
                optLog.setOperName("未知");
                // 13. 当前操作人的租户id
                optLog.setProjectId(0L);
            }else {
                optLog.setOperName(loginAppUserDetails.getUsername());
                // 12. 操作人所属组织
                Long orgId = Long.parseLong(loginAppUserDetails.getOrgId() == null ? "0" : loginAppUserDetails.getOrgId());
                optLog.setOrgId(orgId);
                optLog.setOrgName(loginAppUserDetails.getOrgName());
                // 13. 当前操作人的租户id
                SysUserEntity sysUserEntity = sysUserService.get(loginAppUserDetails.getUserId());
                optLog.setProjectId(sysUserEntity.getProjectId());
            }


            sysLogManageService.save(optLog);
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
    @AfterThrowing(pointcut = "webLog()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {

        // 1. 获取HttpServletRequest的信息
        HttpServletRequest request = AppServletContextHolder.getHttpServletRequest();
        SysLogManageEntity optLog = new SysLogManageEntity();

        // 2. 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 3. 获取操作
        SysLog optLogAnnotation = method.getAnnotation(SysLog.class);
        if (optLogAnnotation != null) {
            optLog.setOptModule(optLogAnnotation.optModule());
            optLog.setOptName(optLogAnnotation.optName());
            optLog.setOptType(optLogAnnotation.optType());
            optLog.setOptValue(optLogAnnotation.optValue());
        }

        // 4. 获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName + "()";
        optLog.setMethod(methodName);
        // 5. ip
        optLog.setOperIp(UtilIp.getHostInfo().getIpAddress());
        // 6. 请求地址
        optLog.setOperUrl(request.getRequestURI().toString());
        // 7. 请求方式
        optLog.setRequestMethod(request.getMethod());
        // 8. 请求的参数
        String params = JSON.toJSONString(joinPoint.getArgs()[0]);
        optLog.setOperParam(params);;
        // 9. 返回结果
        optLog.setJsonResult(stackTraceToString(e.getClass().getName(), e.getMessage(), e.getStackTrace()));
        // 10. 操作结果
        optLog.setStatus(false);
        // 11. 操作人
        try {
            AppUserDetails loginAppUserDetails = getLoginAppUserDetails();
            optLog.setOperName(loginAppUserDetails.getUsername());
            // 12. 操作人所属组织
            Long orgId = Long.parseLong(loginAppUserDetails.getOrgId() == null ? "0" : loginAppUserDetails.getOrgId());
            optLog.setOrgId(orgId);
            optLog.setOrgName(loginAppUserDetails.getOrgName());
            // 13. 当前操作人的租户id
            SysUserEntity sysUserEntity = sysUserService.get(loginAppUserDetails.getUserId());
            optLog.setProjectId(sysUserEntity.getProjectId());
        }catch(Exception ex) {
            optLog.setOperName("");
            optLog.setOrgId(0l);
            optLog.setOrgName("");

        }

        // 12. 操作人所属组织

        sysLogManageService.save(optLog);
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

