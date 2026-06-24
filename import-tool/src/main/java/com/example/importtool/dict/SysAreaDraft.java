package com.example.importtool.dict;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 与表 sys_area 列对齐的导入草稿。{@code id} 为 {@code null} 时表示由数据库自增生成。
 */
@Data
@Builder
public final class SysAreaDraft {

    private final Long id;
    private final long pid;
    private final String name;
    private final String letter;
    private final long adcode;
    private final String location;
    private final Long areaSort;
    private final String areaStatus;
    private final String areaType;
    private final String hot;
    private final String cityCode;
    private final String createBy;
    private final LocalDateTime createTime;
    private final String updateBy;
    private final LocalDateTime updateTime;
    private final String delFlag;
}
