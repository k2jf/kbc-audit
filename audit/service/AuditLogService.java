package com.k2data.kbc.audit.service;

import com.k2data.kbc.audit.model.AuditLog;
import java.util.List;
import java.util.Map;

public interface AuditLogService {

    List<AuditLog> list(Map<String, Object> map);
}
