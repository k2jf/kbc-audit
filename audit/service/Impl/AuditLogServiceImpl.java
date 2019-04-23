package com.k2data.kbc.audit.service.Impl;

import com.k2data.kbc.audit.dao.AuditLogMapper;
import com.k2data.kbc.audit.model.AuditLog;
import com.k2data.kbc.audit.service.AuditLogService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    AuditLogMapper mapper;

    @Override
    public List<AuditLog> list(Map<String, Object> map) {
        return mapper.list(map);
    }
}
