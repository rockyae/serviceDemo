package com.example.batch;


import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@EnableBatchProcessing
//@SpringBootApplication
//public class BatchApplication {
//
//    @Autowired
//    private JobExplorer jobExplorer;
//
//    @Autowired
//    private JobRegistry jobRegistry;
//
//    @Autowired
//    private JobLauncher jobLauncher;
//
//    @Autowired
//    private JobOperator jobOperator;
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(BatchApplication.class, args);
//    }
//}
