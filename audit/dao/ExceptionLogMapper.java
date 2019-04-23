package com.k2data.kbc.audit.dao;

import com.k2data.kbc.audit.model.ExceptionLog;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExceptionLogMapper {
    void insert(ExceptionLog record);

    List<ExceptionLog> selectAll();
}