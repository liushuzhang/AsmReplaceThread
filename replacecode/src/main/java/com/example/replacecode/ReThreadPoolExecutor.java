package com.example.replacecode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-16 17:36
 * @Description:该类左右是用静态方法替换new ThreadPoolExecutor()创建对象
 * 几个方法和参数对应的是ThreadPoolExecutor构造方法
 * 虽然传递过来的参数很多没有用到，但是为了在进行ASM操作时，最小程度的影响栈数据操作，所有参数都传递过来
 */
public class ReThreadPoolExecutor {

    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue) {
        return ChooseThreadPool.getThreadPoolExecutor(corePoolSize,maximumPoolSize);
    }


    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue,
                                                           ThreadFactory threadFactory) {
        return ChooseThreadPool.getThreadPoolExecutor(corePoolSize,maximumPoolSize);

    }

    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue,
                                                           RejectedExecutionHandler handler) {
        return ChooseThreadPool.getThreadPoolExecutor(corePoolSize,maximumPoolSize);
    }

    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize,
                                                           int maximumPoolSize,
                                                           long keepAliveTime,
                                                           TimeUnit unit,
                                                           BlockingQueue<Runnable> workQueue,
                                                           ThreadFactory threadFactory,
                                                           RejectedExecutionHandler handler) {
        return ChooseThreadPool.getThreadPoolExecutor(corePoolSize,maximumPoolSize);
    }
}
