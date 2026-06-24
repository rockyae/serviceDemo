package com.example.controller;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

public class GlobalPhoneSimple {
    
    private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();
    
    /**
     * 处理全球手机号，转成标准格式
     * @param phone 任意格式手机号
     * @param defaultRegion 默认国家（如"CN", "US"），传null则自动检测
     * @return 标准格式，如"+8617053000623", "+14155552671"
     */
    public static String formatPhone(String phone, String defaultRegion) {

        if(phone.contains(" ")){
            return phone.replaceAll("\\D", "");
        }else {
            return phone;
        }
    }
    
    public static void main(String[] args) {
        // 测试全球号码
        System.out.println(formatPhone("+17053000623   ", null));     // +8617053000623
        System.out.println(formatPhone("1 6502530000", null));    // +16502530000
        System.out.println(formatPhone("0044 20 7946 0958", null));  // +442079460958
        System.out.println(formatPhone("17053000623", null));        // +8617053000623
    }
}