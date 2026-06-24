package com.example.po;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountJsonFormatTest {

    @Test
    void jsonFormatOnCreateTime() throws Exception {
        Account account = new Account();
        account.setCreateTime(new Date(0L)); // 1970-01-01 08:00:00 in Asia/Shanghai

        ObjectMapper mapper = new ObjectMapper();
        //mapper.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        String json = mapper.writeValueAsString(account);
        System.out.println(json);
        // @JsonFormat 生效: "createTime":"1970-01-01 08:00:00"
        // 未生效则可能是: "createTime":0 或 ISO-8601 格式
        assertTrue(json.contains("\"createTime\":\"1970-01-01 08:00:00\""), json);
    }
}
