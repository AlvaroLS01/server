package com.comerzzia.ametller.pos.ncr.aspect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * NCR envía dos mensajes DataNeeded (Type 1 y Type 0 con Mode 1) tras el
 * EndTransaction. En la integración de Ametller estos mensajes provocan que el
 * terminal se quede bloqueado, por lo que interceptamos exclusivamente esos
 * dos envíos posteriores al EndTransaction.
 */
@Aspect
@Component
public class DataNeededSuppressorAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataNeededSuppressorAspect.class);

    private static final Pattern MESSAGE_NAME_PATTERN = Pattern.compile("<message[^>]*name=\\\"([^\\\"]+)\\\"");
    private static final Pattern MODE_PATTERN = Pattern.compile("<field[^>]*name=\\\"Mode\\\"[^>]*>(\\d+)<");

    private static final int POST_TRANSACTION_DATA_NEEDED_COUNT = 2;

    private final ThreadLocal<Integer> pendingSuppressions = ThreadLocal.withInitial(() -> 0);

    @Around("execution(* com.comerzzia.pos.ncr.NCRController.sendMessage(..))")
    public Object filterBlockingDataNeededMessages(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
            String payload = extractPayload(args[0]);
            if (payload != null) {
                String messageName = extractMessageName(payload);
                if ("StartTransaction".equals(messageName)) {
                    resetPendingSuppressions();
                }
                if ("EndTransaction".equals(messageName)) {
                    armPendingSuppressions();
                } else if ("DataNeeded".equals(messageName) && shouldSuppressDataNeeded(payload)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Suppressing post-EndTransaction DataNeeded message to avoid NCR lock-up: {}", payload);
                    }
                    return null;
                }
            }
        }
        return pjp.proceed();
    }

    private String extractPayload(Object candidate) {
        if (candidate == null) {
            return null;
        }
        if (candidate instanceof String) {
            return (String) candidate;
        }
        return String.valueOf(candidate);
    }

    private String extractMessageName(String payload) {
        Matcher matcher = MESSAGE_NAME_PATTERN.matcher(payload);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Integer extractMode(String payload) {
        Matcher matcher = MODE_PATTERN.matcher(payload);
        if (matcher.find()) {
            try {
                return Integer.valueOf(matcher.group(1));
            } catch (NumberFormatException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Unable to parse Mode value from NCR message: {}", payload, e);
                }
            }
        }
        return null;
    }

    private boolean shouldSuppressDataNeeded(String payload) {
        int pending = pendingSuppressions.get();
        if (pending <= 0) {
            return false;
        }

        Integer mode = extractMode(payload);
        if (mode != null && mode == 1) {
            pendingSuppressions.set(pending - 1);
            return true;
        }

        // Si el modo no es 1, cancelamos la supresión para no afectar a otros flujos.
        resetPendingSuppressions();
        return false;
    }

    private void armPendingSuppressions() {
        pendingSuppressions.set(POST_TRANSACTION_DATA_NEEDED_COUNT);
    }

    private void resetPendingSuppressions() {
        pendingSuppressions.set(0);
    }
}
