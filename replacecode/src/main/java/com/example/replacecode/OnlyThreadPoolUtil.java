package com.example.replacecode;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-16 16:49
 * @Description:项目中的线程管理类
 * 项目中维护以下线程池，
 * 1、IO线程池：处理类似IO的操作，通常用来处理计算量不大的线程工作，这个线程数量不做控制，最大值设为Interger最大值
 * 2、CPU线程池：用于计算量比较大的线程，核心线程数和最大线程数需要根据CPU的数量控制
 * 3、single线程池：单例线程池
 * 4、抢占式线程池 ForkJoinPool
 */

public class OnlyThreadPoolUtil {

    /**
     * 创建线程池的参数
     * corePoolSize 核心线程池数
     * maximumPoolSize 最大线程数
     * keepAliveTime 线程使用完成后需要的等待的时间，过时销毁
     * unit 时间单位
     * workQueue 阻塞队列 一般情况下使用SynchronousQueue或者LinkedBlockingQueue
     * threadFactory 创建线程的工厂,一般情况下会对线程进行命名
     * defaultHandler 线程数量过多的时候拒绝策略
     */

    //返回可用处理器的Java虚拟机的数量
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数
    public static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //最大线程数
    public static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;

    //获取IO线程池
    private static final ThreadPoolExecutor cacheTreadPool = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>(), new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "rp cache #" + mCount.getAndIncrement());
        }
    });

    //CPU线程池
    private static final ThreadPoolExecutor fixedCountThreadPool = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "rp fixed #" + mCount.getAndIncrement());
        }
    });

    //CPU线程池
    private static final ThreadPoolExecutor singleThreadPool = new ThreadPoolExecutor(
            1, 1, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "rp single" + mCount.getAndIncrement());
        }
    });

    //抢占式线程池
    private static final ForkJoinPool forkJoinPool =  new ForkJoinPool();

    //获取IO线程池
    public static ThreadPoolExecutor getFixedThreadPool() {
        return fixedCountThreadPool;
    }

    //获取CPU线程池
    public static ThreadPoolExecutor getCacheThreadPool() {
        return cacheTreadPool;
    }

    //获取单个线程池
    public static ThreadPoolExecutor getSinglehreadPool() {
        return singleThreadPool;
    }

    //抢占式线程池
    public static ForkJoinPool getForkJoinPool(){
        return forkJoinPool;
    }

}