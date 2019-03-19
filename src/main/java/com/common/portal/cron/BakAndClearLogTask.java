package com.common.portal.cron;

import com.common.portal.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 专门接收OPEN的性能数据
 */

@Component
public class BakAndClearLogTask {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    LogService logService;

    /**
     * 每隔1天执行一次
     *
     * @param
     */
    @Scheduled(cron = "0 0 0 0/1 * ?")
    public void executeTaskEveryDay() {

        String retentionTime = logService.getLogConfigVO().getRetentionTime();
        if (1 == Integer.parseInt(retentionTime)){
            logger.info("BakAndClearLogTask executeTaskEveryDay begin.");
            executeBakAndClear();
            logger.info("BakAndClearLogTask executeTaskEveryDay end.");
        }
    }

    private void executeBakAndClear() {
        try {
            bakLog();
        } catch (IOException e) {
            logger.error("executeBakAndClear error.",e);
            return;
        }
        clearDB();
    }

    private void clearDB() {
        logService.clearDB();
    }

    private void bakLog() throws IOException {
        logService.bakSecurityLog();
        logService.bakOperationLog();
        logService.bakEventLog();
    }

    /**
     * 每隔一周执行一次
     *
     * @param
     */
    @Scheduled(cron = "0 0 0 0/7 * ?")
    public void executeTaskEveryWeekly() {
        String retentionTime = logService.getLogConfigVO().getRetentionTime();
        if (7 == Integer.parseInt(retentionTime)){
            logger.info("BakAndClearLogTask executeTaskEveryWeekly begin.");
            executeBakAndClear();
            logger.info("BakAndClearLogTask executeTaskEveryWeekly end.");
        }
    }

    /**
     * 每隔一月执行一次
     *
     * @param
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void executeTaskEveryMonth() {
        String retentionTime = logService.getLogConfigVO().getRetentionTime();
        if (30 == Integer.parseInt(retentionTime)){
            logger.info("BakAndClearLogTask executeTaskEveryMonth begin.");
            executeBakAndClear();
            logger.info("BakAndClearLogTask executeTaskEveryMonth end.");
        }
    }



}
