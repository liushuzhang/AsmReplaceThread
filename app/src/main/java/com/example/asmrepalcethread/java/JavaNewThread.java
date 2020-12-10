package com.example.asmrepalcethread.java;

/**
 * @Author: liushuzhang
 * @Date: 2020-12-03 16:36
 * @Description:
 */
public class JavaNewThread {
    //创建线程。休眠5分钟，方便查看进程中的线程，查看是否成功替换
    public static void createNewThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Thread.currentThread().setName("JavaNewThread");
                try {
                    Thread.sleep(5 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //通过集成的方式启动线程
    public static void createExtendThread() {
        new MyExtendsThread().start();
    }


    static class MyExtendsThread extends Thread {

        @Override
        public void run() {
            Thread.currentThread().setName("JavaExtendThread");
            try {
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
