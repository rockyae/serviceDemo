package com.example.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.Instant;
import java.time.ZoneId;

@Slf4j
public class DemoQuartzJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("DemoQuartzJob fired at {}, jobKey={}, triggerKey={}",
                Instant.now().atZone(ZoneId.systemDefault()),
                context.getJobDetail().getKey(),
                context.getTrigger().getKey());
    }
}
