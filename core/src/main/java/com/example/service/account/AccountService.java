package com.example.service.account;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.dto.AccountDto;
import com.example.po.Account;


/**
* @author WCT.DEV
* @description 针对表【t_account(账户信息表)】的数据库操作Service
* @createDate 2026-04-21 16:22:40
*/
public interface AccountService extends IService<Account> {
    IPage<Account> selectBatch(AccountDto accountDto, Page<Account> page);
}
