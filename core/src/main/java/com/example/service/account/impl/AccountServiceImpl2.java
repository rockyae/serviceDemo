package com.example.service.account.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.dto.AccountDto;
import com.example.mapper.account.AccountMapper;
import com.example.po.Account;
import com.example.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@DS("master")
@Slf4j
public class AccountServiceImpl2 extends ServiceImpl<AccountMapper, Account> implements AccountService {
    //分页查询，每次1000条数据

    @Override
    public IPage<Account> selectBatch(AccountDto accountDto, Page<Account> page) {
        String peek = DynamicDataSourceContextHolder.peek();
        log.info("peek:{}", peek);
        LambdaQueryWrapper<Account> wrapper = Wrappers.lambdaQuery();
//        wrapper.eq(Objects.nonNull(accountDto.getAccountType()), Account::getAccountType, accountDto.getAccountType())
//                .eq(Objects.nonNull(accountDto.getAccountStatus()), Account::getAccountName, accountDto.getAccountStatus())
//                .ge(Objects.nonNull(accountDto.getCreateTimeStart()), Account::getCreateTime, accountDto.getCreateTimeStart())
//                .le(Objects.nonNull(accountDto.getCreateTimeEnd()), Account::getCreateTime, accountDto.getCreateTimeEnd())
//                .orderByDesc(Account::getCreateTime);
        Page<Account> page1 = null;
        try{
            page1 = this.page(page, wrapper);
        }catch (Exception e){
            e.printStackTrace();
        }
        return page1;
    }
}




