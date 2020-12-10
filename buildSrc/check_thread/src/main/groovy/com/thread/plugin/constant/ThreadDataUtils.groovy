package com.thread.plugin.constant

import com.thread.plugin.check.CheckThreadTransform


//用于统计项目中使用线程的情况
class ThreadDataUtils {
    //直接new线层的地方
    public static ArrayList<String> newThreadList = new ArrayList<>();
    //继承thread的地方
    public static ArrayList<String> extendThreadList = new ArrayList<>();

    //直接创建ThreadPoolExecutor的地方
    public static ArrayList<String> createThreadPoolList = new ArrayList<>();
    //继承线程池的地方
    public static ArrayList<String> extendThreadPooList = new ArrayList<>();

    //直接new Executors的地方
    public static ArrayList<String> newExecutorsThreadList = new ArrayList<>();
    //继承Executors的地方
    public static ArrayList<String> extendExecutorsThreadList = new ArrayList<>();
    //创建Executors的地方
    public static ArrayList<String> createExecutorsThreadList = new ArrayList<>();

    //直接创建ForkJoinPool的地方
    public static ArrayList<String> createForkJoinPool = new ArrayList<>();
    //继承线程池的地方
    public static ArrayList<String> extendForkJoinPool = new ArrayList<>();

    //直接创建ScheduledThreadPoolExecutor的地方
    public static ArrayList<String> createScheduledThreadPoolExecutor = new ArrayList<>();
    //继承线程池的地方
    public static ArrayList<String> extendScheduledThreadPoolExecutor = new ArrayList<>();


    //把检测出来的线程打印出来
    static void writeThreadToFile() {
        String rootPath = CheckThreadTransform.project.projectDir.toString()
        println("rootPath-----------------------------------" + rootPath)
        String fileName = "checkThreadResult"
        String reslut = priltnMessToFile()
        File mFile = new File(rootPath + "/" + fileName)
        if (mFile.exists()) {
            mFile.delete()
        }
        FileOutputStream out = new FileOutputStream(mFile, false)
        out.write(reslut.toString().getBytes("utf-8"))
        out.close()
    }

    /**
     * 清除数据
     */
    static void clearData() {
        newThreadList.clear()
        createThreadPoolList.clear()
        extendThreadList.clear()
        extendThreadPooList.clear()
        newExecutorsThreadList.clear()
        extendExecutorsThreadList.clear()
        createExecutorsThreadList.clear()
        createForkJoinPool.clear()
        extendForkJoinPool.clear()
        createScheduledThreadPoolExecutor.clear()
        extendScheduledThreadPoolExecutor.clear()
    }


    //把检测出来的线程打印出来
    static String priltnMessToFile() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("new thread-----------------------------------" + newThreadList.size()).append("\n")
        for (String thread : newThreadList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }
        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("new threadpool-----------------------------------" + createThreadPoolList.size()).append("\n")
        for (String thread : createThreadPoolList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }
        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("extend thread-----------------------------------" + extendThreadList.size()).append("\n")
        for (String thread : extendThreadList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }
        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("extend threadpool-----------------------------------" + extendThreadPooList.size()).append("\n")
        for (String thread : extendThreadPooList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("newExecutorsThreadList-----------------------------------" + newExecutorsThreadList.size()).append("\n")
        for (String thread : newExecutorsThreadList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("extendExecutorsThreadList-----------------------------------" + extendExecutorsThreadList.size()).append("\n")
        for (String thread : extendExecutorsThreadList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("createExecutorsThreadList-----------------------------------" + createExecutorsThreadList.size()).append("\n")
        for (String thread : createExecutorsThreadList) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("createForkJoinPool-----------------------------------" + createForkJoinPool.size()).append("\n")
        for (String thread : createForkJoinPool) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("extendForkJoinPool-----------------------------------" + extendForkJoinPool.size()).append("\n")
        for (String thread : extendForkJoinPool) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("createScheduledThreadPoolExecutor----------------------------------" + createScheduledThreadPoolExecutor.size()).append("\n")
        for (String thread : createScheduledThreadPoolExecutor) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        stringBuffer.append("\n").append("\n").append("\n")
        stringBuffer.append("extendScheduledThreadPoolExecutor-----------------------------------" + extendScheduledThreadPoolExecutor.size()).append("\n")
        for (String thread : extendScheduledThreadPoolExecutor) {
            println(thread)
            stringBuffer.append(thread).append("\n")
        }

        return stringBuffer.toString()
    }

}