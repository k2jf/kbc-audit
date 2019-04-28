package com.k2data.kbc.audit.Utils;

import com.k2data.kbc.audit.dao.AuditLogMapper;
import com.k2data.kbc.audit.dao.ExceptionLogMapper;
import com.k2data.kbc.audit.model.AuditLog;
import com.k2data.kbc.audit.model.ExceptionLog;

public class LogUtil {


    private static AuditLogMapper auditLogDao = SpringContextHolderUtil.getBean(AuditLogMapper.class);

    private static ExceptionLogMapper exLogDao = SpringContextHolderUtil.getBean(ExceptionLogMapper.class);

    public static void saveLog(AuditLog log) {
        new SaveLogRunnable(log).run();
    }

    public static void saveExLogRunnable(ExceptionLog exlog) {
        new SaveExLogRunnable(exlog).run();
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

    public static class SaveExLogRunnable implements Runnable {

        private ExceptionLog exLog;

        public SaveExLogRunnable(ExceptionLog exLog) {
            this.exLog = exLog;
        }

        @Override
        public void run() {
            exLogDao.insert(exLog);
        }
    }
}
