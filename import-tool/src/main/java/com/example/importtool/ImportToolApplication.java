package com.example.importtool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.importtool.mapper")
public class ImportToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImportToolApplication.class, args);
    }
}
