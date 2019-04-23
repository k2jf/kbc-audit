package com.k2data.kbc.audit.controller;

import com.k2data.kbc.api.KbcResponse;
import com.k2data.kbc.audit.annotation.Operation;
import com.k2data.kbc.audit.model.AuditLog;
import com.k2data.kbc.audit.service.AuditLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api("日志管理接口")
@RestController
public class AuditLogController {


    @Autowired
    AuditLogService auditLogService;

    @ApiOperation("获取审计日志列表")
    @GetMapping(value = {"auditLogList", "auditLogList/{creator}", "auditLogList/{beginDate}/{endDate}",
        "auditLogList/{creator}/{beginDate}/{endDate}"})
    @Operation(value = "查询log列表")
    public KbcResponse list(@PathVariable(required = false) String creator,
        @PathVariable(required = false) String beginDate, @PathVariable(required = false) String endDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("creator", creator);
        map.put("beginDate", beginDate);
        map.put("endDate", endDate);
        KbcResponse response = new KbcResponse();
        response.getBody().put("audiLogList", auditLogService.list(map));
        return response;
    }
}
