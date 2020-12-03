package com.example.replacecode;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-16 17:52
 * @Description:用于替换通过ExecutorService创建线程
 */
public class ReExecutorService {

    //创建一个固定数量的线程池
    public static ExecutorService newFixedThreadPool(int nThreads) {
        return OnlyThreadPoolUtil.getFixedThreadPool();
    }


    //创建一个具有抢占式操作的线程池

    /**
     * 这里是Java 1.8加入的一个线程池
     * 它是一个并行线程池，参数中传入的是一个线程并发额数量，这里和之前的线程池有很明显的区别，
     * 普通的线程池都有核心线程数、最大线程数等等，而这就使用了一个并发线程数解决问题。从介绍中，
     * 还说明这个线程池不会保证任务的顺序执行，也就是WorkStealing的意思，抢占式的工作
     * @param parallelism
     * @return
     */
    public static ExecutorService newWorkStealingPool(int parallelism) {
        return OnlyThreadPoolUtil.getForkJoinPool();

    }


    public static ExecutorService newWorkStealingPool() {
        return OnlyThreadPoolUtil.getForkJoinPool();
    }


    public static ExecutorService newFixedThreadPool(int nThreads, ThreadFactory threadFactory) {
        return OnlyThreadPoolUtil.getFixedThreadPool();
    }


    public static ExecutorService newSingleThreadExecutor() {
        return OnlyThreadPoolUtil.getSinglehreadPool();
    }


    public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
        return OnlyThreadPoolUtil.getSinglehreadPool();
    }


    public static ExecutorService newCachedThreadPool() {
        return OnlyThreadPoolUtil.getCacheThreadPool();
    }


    public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
        return OnlyThreadPoolUtil.getCacheThreadPool();
    }

    //ScheduledExecutorService在ExecutorService提供的功能之上再增加了延迟和定期执行任务的功能。

//    public static ScheduledExecutorService newSingleThreadScheduledExecutor() {
//
//    }
//
//
//    public static ScheduledExecutorService newSingleThreadScheduledExecutor(ThreadFactory threadFactory) {
//
//    }
//
//
//    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize) {
//
//    }
//
//
//    public static ScheduledExecutorService newScheduledThreadPool(
//            int corePoolSize, ThreadFactory threadFactory) {
//    }
//
//
//    public static ExecutorService unconfigurableExecutorService(ExecutorService executor) {
//
//    }
//
//
//    public static ScheduledExecutorService unconfigurableScheduledExecutorService(ScheduledExecutorService executor) {
//
//    }

}
