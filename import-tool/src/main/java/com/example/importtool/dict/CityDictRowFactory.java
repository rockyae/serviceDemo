package com.example.importtool.dict;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将 {@link CityDictEnvelope} 转为待写入 {@code sys_area} 的扁平行（审计字段由调用方传入）。
 */
public final class CityDictRowFactory {

    private CityDictRowFactory() {
    }

    public static List<SysAreaDraft> toDrafts(CityDictEnvelope envelope, String createBy, LocalDateTime createTime) {
        // 无 body / dictMap 时不生成行
        if (envelope == null || envelope.getBody() == null || envelope.getBody().getDictMap() == null) {
            return List.of();
        }
        Body body = envelope.getBody(); // 字典主体
        DictMap dict = body.getDictMap(); // state + area
        long rootPid = parseLongOrZero(body.getParentId()); // state 行的父 id（一般为 0）

        List<SysAreaDraft> rows = new ArrayList<>();
        //添加根节点
        SysAreaDraft root = SysAreaDraft.builder()
                .id(rootPid)
                .pid(0L)
                .name("尼日利亚")
                .letter("")
                .adcode(200000)
                .location("")
                .areaStatus("1")
                .areaType("0")
                .hot("0")
                .cityCode("")
                .createBy(createBy)
                .updateBy(createBy)
                .createTime(createTime)
                .updateTime(createTime)
                .build();
        rows.add(root);
        if (dict.getState() != null) {
            for (DictEntry e : dict.getState()) {
                rows.add(fromStateLevel(e, root.getAdcode(), createBy, createTime));
            }
        }
        // dictMap.area：外层 key=上级编码，内层为下级列表 → 每行 area_type='3'，pid=外层 key
        for (Map.Entry<String, List<DictEntry>> group : dict.getArea().entrySet()) {
            long parentAdcode = parseLongOrZero(group.getKey()); // 作为子行的 pid,州编号
            List<DictEntry> children = group.getValue();
            if (children == null) {
                continue; // 该组无子项则跳过
            }
            for (DictEntry e : children) {
                rows.add(fromUnderState(parentAdcode, e, createBy, createTime));
            }
        }
        return rows; // 顺序：先全部 state，再按 area 分组顺序展开
    }

    /** 对应 JSON 中 state 列表：按表语义映射为省级类型 {@code area_type = '1'}（可按业务改为 '2'）。 */
    private static SysAreaDraft fromStateLevel(DictEntry e, long pid, String createBy, LocalDateTime createTime) {
        long adcode = parseLongOrZero(e.getKey());
        String name = e.resolveName();
        return new SysAreaDraft(
                null,
                pid,
                name,
                "",
                adcode,
                "",
                null,
                "1",
                "1",
                "0",
                "",
                createBy,
                createTime,
                "admin",
                createTime,
                "0"
        );
    }

    /** 对应 dictMap.area 下某州下的区县：{@code area_type = '3'}，pid 为外层 key。 */
    private static SysAreaDraft fromUnderState(long parentAdcode, DictEntry e, String createBy, LocalDateTime createTime) {
        long adcode = parseLongOrZero(e.getKey());
        String name = e.resolveName();
        return new SysAreaDraft(
                null,
                parentAdcode,
                name,
                "",
                adcode,
                "",
                null,
                "1",
                "2",
                "0",
                "",
                createBy,
                createTime,
                "admin",
                createTime,
                "0"
        );
    }

    private static long parseLongOrZero(String raw) {
        if (raw == null || raw.isBlank()) {
            return 0L;
        }
        try {
            return Long.parseLong(raw.trim());
        } catch (NumberFormatException ex) {
            return 0L;
        }
    }
}
