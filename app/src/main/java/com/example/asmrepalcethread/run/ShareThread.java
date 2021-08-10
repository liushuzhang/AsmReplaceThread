package com.example.asmrepalcethread.run;

/**
 * @Author: liushuzhang
 * @Date: 2021-07-11 19:24
 * @Description:
 */
public class ShareThread extends Thread{

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(100*60*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
