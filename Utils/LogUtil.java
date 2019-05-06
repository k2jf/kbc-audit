package com.k2data.kbc.audit.Utils;

import com.k2data.kbc.audit.dao.AuditLogMapper;
import com.k2data.kbc.audit.model.AuditLog;

public class LogUtil {


    private static AuditLogMapper auditLogDao = SpringContextHolderUtil.getBean(AuditLogMapper.class);


    public static void saveLog(AuditLog log) {
        new SaveLogRunnable(log).run();
    }


    public static class SaveLogRunnable implements Runnable {

        private AuditLog log;

        public SaveLogRunnable(AuditLog log) {
            log.setId(null);
            this.log = log;
        }

        @Override
        public void run() {
            auditLogDao.insert(log);
        }
    }

}
