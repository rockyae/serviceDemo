package com.example.importtool.dict;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {

    private DictMap dictMap;
    private String parentId;

    public DictMap getDictMap() {
        return dictMap;
    }

    public void setDictMap(DictMap dictMap) {
        this.dictMap = dictMap;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
