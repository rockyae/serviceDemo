package com.example.importtool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.importtool.dict.SysAreaDraft;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_area")
public class SysArea {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long pid;
    private String name;
    private String letter;
    private Long adcode;
    private String location;
    private Long areaSort;
    private String areaStatus;
    private String areaType;
    private String hot;
    private String cityCode;
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;
    private String delFlag;

    public static SysArea fromDraft(SysAreaDraft d) {
        SysArea e = new SysArea();
        if (d.getId() != null) {
            e.setId(d.getId());
        }
        e.setPid(d.getPid());
        e.setName(d.getName());
        e.setLetter(d.getLetter());
        e.setAdcode(d.getAdcode());
        e.setLocation(d.getLocation());
        e.setAreaSort(d.getAreaSort());
        e.setAreaStatus(d.getAreaStatus());
        e.setAreaType(d.getAreaType());
        e.setHot(d.getHot());
        e.setCityCode(d.getCityCode());
        e.setCreateBy(d.getCreateBy());
        e.setCreateTime(d.getCreateTime());
        e.setUpdateBy(d.getUpdateBy());
        e.setUpdateTime(d.getUpdateTime());
        e.setDelFlag(d.getDelFlag());
        return e;
    }
}
