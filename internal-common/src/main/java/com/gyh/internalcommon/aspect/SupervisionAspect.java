package com.gyh.internalcommon.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyh.internalcommon.dto.government.SupervisionData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.xml.soap.SAAJResult;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 对insert，update和delete进行切面操作
 *
 * @author gyh
 * @date 2023/2/15
 * */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class SupervisionAspect {
    @NonNull
    private ActiveMQQueue bufferQueue;

    @NonNull
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Value("${government-upload.enabled}")
    private boolean isGovernmentUploadEnabled;

    /**
     * 插入操作的切入点
     * */
    @Pointcut("execution(* com.gyh..mapper..*.insert*(..))")
    private void insert(){}

    /**
     * 更新操作的切入点
     * */
    @Pointcut("execution(* com.gyh..mapper..*.update*(..))")
    private void update(){}

    /**
     * 删除操作的切入点
     * */
    @Pointcut("execution(* com.gyh..mapper..*.delete*(..))")
    private void delete(){}

    /**
     * 插入成功后的操作
     * @param joinPoint
     * */
    @AfterReturning(pointcut = "insert()")
    public void doAfterInsert(JoinPoint joinPoint) {
        send(SupervisionData.OperationType.INSERT, joinPoint);
    }

    private void send(SupervisionData.OperationType operationType, JoinPoint joinPoint) {
        if (!isGovernmentUploadEnabled) {
            return;
        }
        Optional<Object> param = Arrays.stream(joinPoint.getArgs()).findFirst();
        if (param.isPresent()) {
            try {
                Object entity = param.get();
                Integer id;
                String claName;
                if (entity instanceof Integer) {
                    id = (Integer) entity;
                    String packageName = joinPoint.getSignature().getDeclaringTypeName();
                    claName = StringUtils.substringBeforeLast(packageName, "mapper").replace("mapper", "entity");
                }
                else {
                    id = (Integer) entity.getClass().getDeclaredMethod("getId").invoke(entity);
                    claName = param.get().getClass().getName();
                }
                SupervisionData data = new SupervisionData().setClassName(claName).setId(id).setOperationType(operationType);
                String json = new ObjectMapper().writeValueAsString(data);
                jmsMessagingTemplate.convertAndSend(bufferQueue, json);
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | JsonProcessingException e) {
                e.printStackTrace();
                log.error("SupervisionAspect发生错误！:", e);
            }
        }
    }
}
