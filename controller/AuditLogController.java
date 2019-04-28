package com.k2data.kbc.audit.controller;

import com.k2data.kbc.api.KbcResponse;
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

    @ApiOperation("get logs")
    @GetMapping(value = {"logs"})
    public KbcResponse list() {
        Map<String, Object> map = new HashMap<>();
        KbcResponse response = new KbcResponse();
        response.getBody().put("logs", auditLogService.list(map));
        return response;
    }

    @ApiOperation("get log")
    @GetMapping(value = {"logs/{id}"})
    public KbcResponse log(@PathVariable Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        KbcResponse response = new KbcResponse();
        response.getBody().put("log", auditLogService.list(map));
        return response;
    }
}
