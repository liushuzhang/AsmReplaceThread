package com.example.asmrepalcethread.lambda;

/**
 * @Author: liushuzhang
 * @Date: 2021-07-18 19:22
 * @Description:
 */
public class ThreadUtils {

    private String mess = "";
    public void startThread(){
        new Thread(() -> System.out.println(mess));
    }
}
