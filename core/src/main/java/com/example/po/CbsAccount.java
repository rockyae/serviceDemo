package com.example.po;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Doug Zhou
 * @description
 * @date Created in 2026-02-27 10:50
 * @modified By:
 */

@Data
@TableName("cbs_account")
public class CbsAccount  {

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String cifNo;

    private String accountNo;

    private String accountType;

    private String currency;
    private String accountName;
    private BigDecimal balance;

    private BigDecimal lockedBalance;

    private BigDecimal holdBalance;

    private BigDecimal availableBalance;

    private String status;

    private String pin;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)

    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)

    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)

    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @TableLogic
    private String delFlag;

    private String tenantId;

}
