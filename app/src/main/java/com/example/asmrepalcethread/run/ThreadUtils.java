package com.example.asmrepalcethread.run;

import com.example.asmrepalcethread.java.JavaThreadPool;

/**
 * @Author: liushuzhang
 * @Date: 2021-07-11 19:26
 * @Description:
 */
public class ThreadUtils {

    public static void startThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("startThread------");

            }
        }).start();
    }

    public static void startThreadWithLambda() {
        new Thread(() -> {
            try {
                Thread.sleep(2*60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("startThreadWithLambda------");
        }).start();
    }

    public static void runWithThreadPool() {
        JavaThreadPool.usePoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3*60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("runWithThreadPool------");
            }
        });
    }

    public static void runWithThreadPoolLambda() {
        JavaThreadPool.usePoolExecutor.execute(() ->{
            try {
                Thread.sleep(3*60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("runWithThreadPoolLambda------");
        });
    }
}
