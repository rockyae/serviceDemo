package com.example.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.R;
import com.example.dto.AccountDto;
import com.example.po.Account;
import com.example.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/account2")
@RequiredArgsConstructor
public class AccountController2 {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @RequestMapping("/printMybatisConfig")
    public Mono<R<String>> printMybatisConfig() {
        org.apache.ibatis.session.Configuration cfg = sqlSessionFactory.getConfiguration();
        System.out.println("mapUnderscoreToCamelCase = " + cfg.isMapUnderscoreToCamelCase());
        System.out.println("jdbcTypeForNull = " + cfg.getJdbcTypeForNull());
        System.out.println("isShrinkWhitespacesInSql = " + cfg.isShrinkWhitespacesInSql());
        return Mono.just(R.ok("ok"));
    }

    private final AccountService accountService;

    @GetMapping("/test")
    public R<Page<Account>> test(AccountDto accountDto) {
        Page<Account> page = new Page<>(1, 10);
        Page<Account> accountIPage = (Page<Account>) accountService.selectBatch(accountDto, page);
        return R.ok(accountIPage);
    }

}
