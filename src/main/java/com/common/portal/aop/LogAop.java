package com.common.portal.aop;

import com.common.portal.controller.vo.UserVO;
import com.common.portal.entity.Log;
import com.common.portal.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class LogAop {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogService logService;

    @Around(value = "@annotation(OperationLog)")
    public Object logAround(ProceedingJoinPoint point){
        Log log = buildLog(point);
        Object result = null;
        try {
            result = point.proceed(point.getArgs());
        } catch (Throwable throwable) {
            logger.info("LogAop buildLog error.",throwable.getMessage());
            log.setResult(0);
        }
        UserVO userVO = (UserVO) WebUtils.getSession().getAttribute("user");
        if (userVO != null && userVO.getId() != null){
            log.setUserId(userVO.getId());
        }
        logService.save(log);
        return result;
    }

    private Log buildLog(ProceedingJoinPoint point) {
        Log entity = new Log();

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        OperationLog operationLog = methodSignature.getMethod().getAnnotation(OperationLog.class);

        entity.setContent(operationLog.content());
        entity.setCreateTime(LocalDateTime.now());
        entity.setOperationType(operationLog.operationType().getName());
        if (operationLog.operationType().equals(OperationType.LOGIN)
                || operationLog.operationType().equals(OperationType.LOGOUT)) {
            entity.setType(LogType.SECURITY.getType());
        }else{
            entity.setDetail(point.getArgs()[0].toString());
            entity.setType(LogType.OPERATION.getType());
        }
        if (operationLog.operationType().equals(OperationType.LOGOUT)){
            UserVO userVO = (UserVO) WebUtils.getSession().getAttribute("user");
            entity.setUserId(userVO.getId());
        }
        entity.setResult(1);
        return entity;
    }

    @AfterThrowing(value = "@annotation(OperationLog)")
    public void logException(JoinPoint point){
//        Log log = buildLog(point);
//        log.setResult(0);
//        logService.save(log);
    }


}
