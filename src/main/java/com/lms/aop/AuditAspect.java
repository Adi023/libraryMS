package com.lms.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.constant.Operation;
import com.lms.entity.AuditLog;
import com.lms.repository.AuditLogRepository;

@Aspect
@Component
public class AuditAspect {

	private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);

	@Autowired
	private AuditLogRepository auditLogRepository;

	@Autowired
	private ObjectMapper objectMapper;

	// Track Add Operation
	@AfterReturning(pointcut = "execution(* com.lms.services.*.add*(..))", returning = "result")
	public void logAddOperation(Object result) {
		logOperation(result, Operation.ADD);
	}

	// Track Update Operation
	@AfterReturning(pointcut = "execution(* com.lms.services.*.update*(..))", returning = "result")
	public void logUpdateOperation(Object result) {
		logOperation(result, Operation.MODIFIED);
	}

//	// Track Delete Operation
//	@After("execution(* com.lms.services.*.delete*(..)) && args(id)")
//	public void logDeleteOperation(Long id) {
//		logger.info("Entity deleted with ID: {}", id);
//		auditLogRepository.save(new AuditLog("Unknown", id, "Deleted", Operation.DELETE));
//	}

	// Helper method to log operations
	private void logOperation(Object entity, Operation operation) {
		try {
			String entityName = entity.getClass().getSimpleName();
			Long entityId = extractEntityId(entity);
			String entityDetails = objectMapper.writeValueAsString(entity);

			logger.info("{} operation performed on {}: {}", operation.getValue(), entityName, entityDetails);
			auditLogRepository.save(new AuditLog(entityName, entityId, entityDetails, operation));

		} catch (Exception e) {
			logger.error("Error while logging operation: {}", e.getMessage(), e);
		}
	}

	// Extract ID dynamically (Assumes entity has a method getId() or getISBN())
	private Long extractEntityId(Object entity) {
		try {
			return (Long) entity.getClass().getMethod("getId").invoke(entity);
		} catch (NoSuchMethodException e1) {
			try {
				return (Long) entity.getClass().getMethod("getISBN").invoke(entity);
			} catch (Exception e2) {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

}