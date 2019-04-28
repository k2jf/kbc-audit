package com.k2data.kbc.audit.dao;

import com.k2data.kbc.audit.model.AuditLog;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditLogMapper {

    void insert(AuditLog object);

    List<AuditLog> list(Map<String, Object> map);
}