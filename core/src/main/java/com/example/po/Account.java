package com.example.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 账户信息表
 * @TableName t_account
 */
@TableName(value ="t_account")
public class Account {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账户ID
     */
    private String accountId;

    /**
     * 账户号
     */
    private String accountNo;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 会员ID
     */
    private String userId;

    /**
     * 会员编号
     */
    private String userNo;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * bvn号
     */
    private String bvn;

    /**
     * 账户用户类型：1：平台；2：商户；3：代理商；4：子商户；5：虚拟户；6：个人用户
     */
    private Integer userType;

    /**
     * 账户状态 1：正常；2：止出；3：冻结，不出不进；4:止入,9：关闭;
     */
    private Integer accountStatus;

    /**
     * 账户类型1:备付金账户;2:中间户;3:余额账户;4:待结算账户;5:手续费账户;6:虚拟账户;7:备付金负债账户;8:平账专用户;9:印花税账户;10:VAT账户;11:渠道成本账户;12:利润账户;13:子账户
     */
    private Integer accountType;

    /**
     * 借贷类型
     */
    private String dcType;

    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 货币类型
     */
    private String currency;

    /**
     * 总余额
     */
    private Long totalBalance;

    /**
     * 可用余额
     */
    private Long availableBalance;

    /**
     * 冻结余额
     */
    private Long frozenBalance;

    /**
     * 创建时间
     */
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    /**
     * 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 账户ID
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账户ID
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    /**
     * 账户号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 账户号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * 账户名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 账户名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 会员ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 会员ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 会员编号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * 会员编号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * 客户ID
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * 客户ID
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * bvn号
     */
    public String getBvn() {
        return bvn;
    }

    /**
     * bvn号
     */
    public void setBvn(String bvn) {
        this.bvn = bvn;
    }

    /**
     * 账户用户类型：1：平台；2：商户；3：代理商；4：子商户；5：虚拟户；6：个人用户
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 账户用户类型：1：平台；2：商户；3：代理商；4：子商户；5：虚拟户；6：个人用户
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 账户状态 1：正常；2：止出；3：冻结，不出不进；4:止入,9：关闭;
     */
    public Integer getAccountStatus() {
        return accountStatus;
    }

    /**
     * 账户状态 1：正常；2：止出；3：冻结，不出不进；4:止入,9：关闭;
     */
    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * 账户类型1:备付金账户;2:中间户;3:余额账户;4:待结算账户;5:手续费账户;6:虚拟账户;7:备付金负债账户;8:平账专用户;9:印花税账户;10:VAT账户;11:渠道成本账户;12:利润账户;13:子账户
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 账户类型1:备付金账户;2:中间户;3:余额账户;4:待结算账户;5:手续费账户;6:虚拟账户;7:备付金负债账户;8:平账专用户;9:印花税账户;10:VAT账户;11:渠道成本账户;12:利润账户;13:子账户
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 借贷类型
     */
    public String getDcType() {
        return dcType;
    }

    /**
     * 借贷类型
     */
    public void setDcType(String dcType) {
        this.dcType = dcType;
    }

    /**
     * 国家代码
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * 国家代码
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * 货币类型
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 货币类型
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 总余额
     */
    public Long getTotalBalance() {
        return totalBalance;
    }

    /**
     * 总余额
     */
    public void setTotalBalance(Long totalBalance) {
        this.totalBalance = totalBalance;
    }

    /**
     * 可用余额
     */
    public Long getAvailableBalance() {
        return availableBalance;
    }

    /**
     * 可用余额
     */
    public void setAvailableBalance(Long availableBalance) {
        this.availableBalance = availableBalance;
    }

    /**
     * 冻结余额
     */
    public Long getFrozenBalance() {
        return frozenBalance;
    }

    /**
     * 冻结余额
     */
    public void setFrozenBalance(Long frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Account other = (Account) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAccountId() == null ? other.getAccountId() == null : this.getAccountId().equals(other.getAccountId()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUserNo() == null ? other.getUserNo() == null : this.getUserNo().equals(other.getUserNo()))
            && (this.getCustomerId() == null ? other.getCustomerId() == null : this.getCustomerId().equals(other.getCustomerId()))
            && (this.getBvn() == null ? other.getBvn() == null : this.getBvn().equals(other.getBvn()))
            && (this.getUserType() == null ? other.getUserType() == null : this.getUserType().equals(other.getUserType()))
            && (this.getAccountStatus() == null ? other.getAccountStatus() == null : this.getAccountStatus().equals(other.getAccountStatus()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getDcType() == null ? other.getDcType() == null : this.getDcType().equals(other.getDcType()))
            && (this.getCountryCode() == null ? other.getCountryCode() == null : this.getCountryCode().equals(other.getCountryCode()))
            && (this.getCurrency() == null ? other.getCurrency() == null : this.getCurrency().equals(other.getCurrency()))
            && (this.getTotalBalance() == null ? other.getTotalBalance() == null : this.getTotalBalance().equals(other.getTotalBalance()))
            && (this.getAvailableBalance() == null ? other.getAvailableBalance() == null : this.getAvailableBalance().equals(other.getAvailableBalance()))
            && (this.getFrozenBalance() == null ? other.getFrozenBalance() == null : this.getFrozenBalance().equals(other.getFrozenBalance()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAccountId() == null) ? 0 : getAccountId().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUserNo() == null) ? 0 : getUserNo().hashCode());
        result = prime * result + ((getCustomerId() == null) ? 0 : getCustomerId().hashCode());
        result = prime * result + ((getBvn() == null) ? 0 : getBvn().hashCode());
        result = prime * result + ((getUserType() == null) ? 0 : getUserType().hashCode());
        result = prime * result + ((getAccountStatus() == null) ? 0 : getAccountStatus().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getDcType() == null) ? 0 : getDcType().hashCode());
        result = prime * result + ((getCountryCode() == null) ? 0 : getCountryCode().hashCode());
        result = prime * result + ((getCurrency() == null) ? 0 : getCurrency().hashCode());
        result = prime * result + ((getTotalBalance() == null) ? 0 : getTotalBalance().hashCode());
        result = prime * result + ((getAvailableBalance() == null) ? 0 : getAvailableBalance().hashCode());
        result = prime * result + ((getFrozenBalance() == null) ? 0 : getFrozenBalance().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", accountId=").append(accountId);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", accountName=").append(accountName);
        sb.append(", userId=").append(userId);
        sb.append(", userNo=").append(userNo);
        sb.append(", customerId=").append(customerId);
        sb.append(", bvn=").append(bvn);
        sb.append(", userType=").append(userType);
        sb.append(", accountStatus=").append(accountStatus);
        sb.append(", accountType=").append(accountType);
        sb.append(", dcType=").append(dcType);
        sb.append(", countryCode=").append(countryCode);
        sb.append(", currency=").append(currency);
        sb.append(", totalBalance=").append(totalBalance);
        sb.append(", availableBalance=").append(availableBalance);
        sb.append(", frozenBalance=").append(frozenBalance);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}