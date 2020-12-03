package com.example.replacecode;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-16 18:14
 * @Description:选择使用何种线程池
 */
public class ChooseThreadPool {

    /**
     * 根据创建线程时的最大线程数来判断，用哪种线程池(判定方式有待优化)
     *
     * @param maximumPoolSize 核心线程数
     * @return 项目中唯一的线程池
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {
        if (corePoolSize == 1 && maximumPoolSize == 1) {
            return OnlyThreadPoolUtil.getSinglehreadPool();
        } else if (maximumPoolSize <= OnlyThreadPoolUtil.MAXIMUM_POOL_SIZE) {
            return OnlyThreadPoolUtil.getFixedThreadPool();
        } else {
            return OnlyThreadPoolUtil.getCacheThreadPool();

        }
    }
}
