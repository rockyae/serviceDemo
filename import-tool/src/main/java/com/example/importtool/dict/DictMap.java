package com.example.importtool.dict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DictMap {

    /**
     * 外层 key 为上级区划编码（如州/省），value 为其下级列表。
     */
    private Map<String, List<DictEntry>> area = new LinkedHashMap<>();

    private List<DictEntry> state;

    public Map<String, List<DictEntry>> getArea() {
        return area;
    }

    public void setArea(Map<String, List<DictEntry>> area) {
        this.area = area != null ? area : new LinkedHashMap<>();
    }

    public List<DictEntry> getState() {
        return state;
    }

    public void setState(List<DictEntry> state) {
        this.state = state;
    }
}
