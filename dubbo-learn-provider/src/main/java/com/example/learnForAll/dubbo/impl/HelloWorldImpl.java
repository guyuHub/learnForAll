package com.example.learnForAll.dubbo.impl;

import com.example.learnForAll.dubbo.api.HelloWorld;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;


@DubboService
public class HelloWorldImpl implements HelloWorld{
    @Override
    public void welcomeToDubbo(String visitName) {
        System.out.println("欢迎访问，by:{}"+visitName);
    }
}
