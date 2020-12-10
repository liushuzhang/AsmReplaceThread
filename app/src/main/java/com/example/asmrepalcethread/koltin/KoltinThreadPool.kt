package com.example.asmrepalcethread.koltin

import android.util.Log
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * @Author: liushuzhang
 * @Date: 2020-12-03 16:50
 * @Description:
 */
object KoltinThreadPool {
    //返回可用处理器的Java虚拟机的数量
    val CPU_COUNT = Runtime.getRuntime().availableProcessors()
    //核心线程数
    val CORE_POOL_SIZE =
        Math.max(2, Math.min(CPU_COUNT - 1, 4))
    //最大线程数
    val MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1

    fun createFixedThreadPool() {
        val threadPoolExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE,
            0L,
            TimeUnit.MILLISECONDS,
            LinkedBlockingQueue(),
            object : ThreadFactory {
                private val mCount =
                    AtomicInteger(1)

                override fun newThread(r: Runnable): Thread {
                    return Thread(r, "koltin fixed #" + mCount.getAndIncrement())
                }
            })

        for (i in 0..5) {
            threadPoolExecutor.execute(Runnable {
                Log.d(
                    "replceThread",
                    "i------------" + Thread.currentThread().name
                )
            })
        }
    }

    fun createCachedThreadPool() {
        val threadPoolExecutor = ThreadPoolExecutor(10,
            Int.MAX_VALUE,
            60L,
            TimeUnit.SECONDS,
            SynchronousQueue(),
            object : ThreadFactory {
                private val mCount =
                    AtomicInteger(1)

                override fun newThread(r: Runnable): Thread {
                    return Thread(r, "koltin cache #" + mCount.getAndIncrement())
                }
            })

        for (i in 0..9) {
            threadPoolExecutor.execute(Runnable {
                Log.d(
                    "replceThread",
                    "i------------" + Thread.currentThread().name
                )
            })
        }
    }

    fun createSingleThreadPool() {
        val threadPoolExecutor =  ThreadPoolExecutor(
            1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            LinkedBlockingQueue(),
            object : ThreadFactory {
                private val mCount =
                    AtomicInteger(1)

                override fun newThread(r: Runnable): Thread {
                    return Thread(r, "koltin single" + mCount.getAndIncrement())
                }
            })

        for (i in 0..3) {
            threadPoolExecutor.execute(Runnable {
                Log.d(
                    "replceThread",
                    "i------------" + Thread.currentThread().name
                )
            })
        }
    }
}