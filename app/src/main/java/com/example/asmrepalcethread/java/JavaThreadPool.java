package com.example.asmrepalcethread.java;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liushuzhang
 * @Date: 2020-12-03 16:50
 * @Description:
 */
public class JavaThreadPool {

    //返回可用处理器的Java虚拟机的数量
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数
    public static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //最大线程数
    public static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    public static ThreadPoolExecutor usePoolExecutor = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "java fixed #" + mCount.getAndIncrement());
        }
    });

    public static void createFixedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "java fixed #" + mCount.getAndIncrement());
            }
        });

        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("replceThread", "i------------" + Thread.currentThread().getName());
                }
            });
        }
    }

    public static void createCachedThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "java cache #" + mCount.getAndIncrement());
            }
        });

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("replceThread", "i------------" + Thread.currentThread().getName());
                }
            });
        }
    }

    public static void createSingleThreadPool() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1, 1, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(Runnable r) {
                return new Thread(r, "java single" + mCount.getAndIncrement());
            }
        });

        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("replceThread", "i------------" + Thread.currentThread().getName());
                }
            });
        }
    }


    public static void executorsNewFixedThreadPool(){
        ExecutorService executor = Executors.newFixedThreadPool(4, new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "java executor fixed #" + mCount.getAndIncrement());
            }
        });

        for (int i = 0; i < 5; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("replceThread", "i------------" + Thread.currentThread().getName());
                }
            });
        }
    }

    public static void executorsNewCacheThreadPool(){
        ExecutorService executor = Executors.newCachedThreadPool( new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "java executor cache #" + mCount.getAndIncrement());
            }
        });

        for (int i = 0; i < 8; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("replceThread", "i------------" + Thread.currentThread().getName());
                }
            });
        }
    }

    public static void executorsNewSingleThreadPool(){
        ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "java executor single #" + mCount.getAndIncrement());
            }
        });

        for (int i = 0; i < 2; i++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("replceThread", "i------------" + Thread.currentThread().getName());
                }
            });
        }
    }


}
