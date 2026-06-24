package com.example.quartz.config;

import com.example.quartz.job.DemoQuartzJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzJobConfig {

    @Bean
    public JobDetail demoJobDetail() {
        return JobBuilder.newJob(DemoQuartzJob.class)
                .withIdentity("demoJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger demoJobTrigger(JobDetail demoJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(demoJobDetail)
                .withIdentity("demoTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/30 * * * * ?"))
                .build();
    }
}
