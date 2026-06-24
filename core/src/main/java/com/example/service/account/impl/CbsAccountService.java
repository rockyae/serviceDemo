package com.example.service.account.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.cbs.CbsAccountMapper;
import com.example.po.CbsAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CbsAccountService extends ServiceImpl<CbsAccountMapper, CbsAccount>  {

    @Autowired
    CbsAccountMapper cbsAccountMapper;


    @DS("slave_1")
    public CbsAccount getOne(){
        String peek = DynamicDataSourceContextHolder.peek();
        log.info("peek:{}", peek);
        LambdaQueryWrapper<CbsAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CbsAccount::getId, "2048582185245573122");
        return cbsAccountMapper.selectOne(wrapper);
    }

    /**
     * 类内部调用导致被调用方法的增强失效，@DS注解不会生效，就像调用普通的方法一样，绕过了aop
     * @return
     */
    public CbsAccount getOne2(){
        return this.getOne();
    }
}
