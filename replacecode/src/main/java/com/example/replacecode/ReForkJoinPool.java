package com.example.replacecode;

import java.util.concurrent.ForkJoinPool;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-17 14:55
 * @Description:抢占式线程池
 */
public class ReForkJoinPool {

    /**
     * ForkJoinPool运用了Fork/Join原理，使用“分而治之”的思想，将大任务分拆成小任务分配给多个线程执行，最后合并得到最终结果，加快运算。
     * 下面几个参数
     * parallelism(即配置线程池个数) 最大值不能超过MAX_CAP（32767）如果没有指定则默认为Runtime.getRuntime().availableProcessors() - 1
     * threadFactory 用于创建线程池、默认defaultForkJoinWorkerThreadFactory
     * exceptionHandler 异常处理，默认为null
     * asyncMode
     * mode //mode可选FIFO_QUEUE和LIFO_QUEUE，默认是LIFO_QUEUE，具体用哪种，就要看业务
     * workerNamePrefix 设置工作线程前缀
     * 另外的每个工作线程都维护着一个工作队列（WorkQueue），这是一个双端队列（Deque），里面存放的对象是任务（ForkJoinTask）。
     */

    /**
     * 默认数量
     */
    public static ForkJoinPool newForkJoinPool() {
        return OnlyThreadPoolUtil.getForkJoinPool();
    }


    public static ForkJoinPool newForkJoinPool(int parallelism) {
        return OnlyThreadPoolUtil.getForkJoinPool();
    }


    public static ForkJoinPool newForkJoinPool(int parallelism,
                                               ForkJoinPool.ForkJoinWorkerThreadFactory factory,
                                               Thread.UncaughtExceptionHandler handler,
                                               boolean asyncMode) {
        return OnlyThreadPoolUtil.getForkJoinPool();
    }

}
