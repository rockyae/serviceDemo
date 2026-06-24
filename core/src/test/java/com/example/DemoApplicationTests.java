package com.example;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.dto.AccountDto;
import com.example.mapper.cbs.CbsAccountMapper;
import com.example.po.Account;
import com.example.po.CbsAccount;
import com.example.service.account.impl.AccountServiceImpl2;
import com.example.service.account.impl.CbsAccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
class DemoApplicationTests {

    @Autowired
    private AccountServiceImpl2 accountService;

    @Autowired
    private CbsAccountService cbsAccountService;

    @Autowired
    CbsAccountMapper cbsAccountMapper;

    @Test
    void testMasterSlave() {
        AccountDto dto = new AccountDto();
        dto.setAccountStatus(1);
        dto.setAccountType(2);
        Page<Account> page = new Page<>(1, 10);
        IPage<Account> accountIPage = accountService.selectBatch(dto, page);
        CbsAccount cbsAccount = cbsAccountService.getOne2();

      //  CbsAccount cbsAccount = cbsAccountMapper.selectById("2048582185245573122");
        log.info("cbsAccount:{}", cbsAccount);

    }

}
