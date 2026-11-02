package com.university.erp.service;

import com.university.erp.entity.AuditLog;
import com.university.erp.entity.User;
import com.university.erp.repository.AuditLogRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository repo;

    @Async
    public void log(User user, String action, String entityType, Long entityId,
                    HttpServletRequest request, String details) {
        AuditLog entry = AuditLog.builder()
            .user(user)
            .action(action)
            .entityType(entityType)
            .entityId(entityId)
            .ipAddress(resolveIp(request))
            .userAgent(request != null ? request.getHeader("User-Agent") : null)
            .details(details)
            .build();
        repo.save(entry);
    }

    @Async
    public void log(User user, String action, HttpServletRequest request) {
        log(user, action, null, null, request, null);
    }

    private String resolveIp(HttpServletRequest request) {
        if (request == null) return null;
        String xff = request.getHeader("X-Forwarded-For");
        return (xff != null && !xff.isBlank()) ? xff.split(",")[0].trim() : request.getRemoteAddr();
    }
}
