package com.thread.plugin.constant;

class ClassConstant {
    public static final String M_Thread = "java/lang/Thread";
    public static final String M_ThreadPoolExecutor = "java/util/concurrent/ThreadPoolExecutor";
    public static final String M_ScheduledThreadPoolExecutor = "java/util/concurrent/ScheduledThreadPoolExecutor";
    public static final String M_ForkJoinPool = "java/util/concurrent/ForkJoinPool";
    public static final String M_Executors = "java/util/concurrent/Executors";
    public static final String M_Executor = "java/util/concurrent/Executor";
    public static final String M_Timer = "java/util/Timer";
    public static final String M_HandlerThread = "android/os/HandlerThread";

    public static final String Replace_Executors = "com/example/aop/asm/OnlyThreadPoolUtil";
    public static final String Replace_ThreadPoolExecutor = "com/example/aop/checkthread/ReplaceThreadPoolExecutor";
}