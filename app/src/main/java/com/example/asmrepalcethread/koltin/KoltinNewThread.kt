package com.example.asmrepalcethread.koltin

/**
 * @Author: liushuzhang
 * @Date: 2020-12-03 16:36
 * @Description:
 */
object KoltinNewThread {
    //创建线程。休眠5分钟，方便查看进程中的线程，查看是否成功替换
    fun createNewThread() {
        Thread(Runnable {
            Thread.currentThread().name = "KoltinNewThread"
            try {
                Thread.sleep(5 * 60 * 1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }).start()
    }

    //通过集成的方式启动线程
    fun createExtendThread() {
        MyExtendsThread().start()
    }

    internal class MyExtendsThread : Thread() {
        override fun run() {
            currentThread().name = "KoltinExtendThread"
            try {
                sleep(5 * 60 * 1000.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}