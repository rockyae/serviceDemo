package com.example.service.reflect;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private String name;

    private Integer age;

    private Integer level;

    public Integer getAge(){
        return this.age;
    }

    private Integer getN(){
        return 1;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Class.forName("com.example.service.reflect.Company");
        // 1. 获取类的基本信息
        Method method = clazz.getDeclaredMethod("getAge");
        System.out.println("方法名：" + method.getName());
        System.out.println("返回类型：" + method.getReturnType().getSimpleName());
        System.out.println("参数类型：" + Arrays.toString(method.getParameterTypes()));
        System.out.println("异常类型：" + Arrays.toString(method.getExceptionTypes()));
        System.out.println("修饰符：" + Modifier.toString(method.getModifiers()));

        // 2. 获取字段
        System.out.println(Arrays.toString(clazz.getDeclaredFields()));

        Company company = new Company();

        // 3. 获取方法签名
        //company.setAge(10);
        //运行时获取age
        Class aClass = company.getClass();
        //Field f = aClass.getField("age");

        // 可以获取private修饰的字段
        Field f = aClass.getDeclaredField("age");
        System.out.println(f.get(company));

    }

}
