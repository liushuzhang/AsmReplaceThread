package com.check.thread.plugin

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 具体执行方法的时候调用
 */
class CheckThreadMethodVisitor extends MethodVisitor {
    //jar 名字
    private String jarName
    //class名字
    private String className
    //method 名字
    private String methodName

    CheckThreadMethodVisitor(int api, MethodVisitor mv, String jarName, String className, String methodName) {
        super(api, mv)
        this.jarName = jarName
        this.className = className
        this.methodName = methodName
    }


    /**
     * @param opcode 主要处理这几种操作符NEW, ANEWARRAY, CHECKCAST or INSTANCEOF.
     * @param type
     */
    @Override
    void visitTypeInsn(int opcode, String type) {
        if (opcode == Opcodes.NEW) {
            switch (type) {
                case ClassConstant.M_Thread:
                    ThreadDataUtils.newThreadList.add("jarName: $jarName \nclassName: $className \nmethod: $methodName \nnewThread \n")
                    break
                case ClassConstant.M_ThreadPoolExecutor:
                    ThreadDataUtils.createThreadPoolList.add("jarName: $jarName \nclassName: $className \nmethod: $methodName \nThreadPoolExecutor\n")
                    break
                case ClassConstant.M_ScheduledThreadPoolExecutor:
                    ThreadDataUtils.createScheduledThreadPoolExecutor.add("jarName: $jarName \nclassName: $className \nmethod: $methodName \nnew ScheduledThreadPoolExecutor\n")
                    break
                case ClassConstant.M_ForkJoinPool:
                    ThreadDataUtils.createForkJoinPool.add("jarName: $jarName \nclassName: $className \nmethod: $methodName \nnew ScheduledThreadPoolExecutor\n")
                    break
                case ClassConstant.M_Executors:
                    ThreadDataUtils.newExecutorsThreadList.add("jarName: $jarName \nclassName: $className \nmethod: $methodName \nnew Executors\n")
                    break
            }
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    void visitInsn(int opcode) {
        super.visitInsn(opcode)
    }

    @Override
    void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc)
    }

    /**
     * 方法调用
     * @param opcode     INVOKEVIRTUAL INVOKESPECIAL INVOKESTATIC INVOKEINTERFACE
     * @param owner
     * @param name
     * @param descriptor
     * @param itf
     */
    @Override
    void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean itf) {
        if ((name.equals("newWorkStealingPool") && descriptor.equalsIgnoreCase("(I)Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newFixedThreadPool") && descriptor.equalsIgnoreCase("(I)Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newWorkStealingPool") && descriptor.equalsIgnoreCase("()Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newSingleThreadExecutor") && descriptor.equalsIgnoreCase("()Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newSingleThreadExecutor") && descriptor.equalsIgnoreCase("(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newFixedThreadPool") && descriptor.equalsIgnoreCase("(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newCachedThreadPool") && descriptor.equalsIgnoreCase("()Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newCachedThreadPool") && descriptor.equalsIgnoreCase("(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("newSingleThreadScheduledExecutor") && descriptor.equalsIgnoreCase("()Ljava/util/concurrent/ScheduledExecutorService;"))

                || (name.equals("newSingleThreadScheduledExecutor") && descriptor.equalsIgnoreCase("(Ljava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ScheduledExecutorService;"))

                || (name.equals("newScheduledThreadPool") && descriptor.equalsIgnoreCase("(I)Ljava/util/concurrent/ScheduledExecutorService;"))

                || (name.equals("newScheduledThreadPool") && descriptor.equalsIgnoreCase("(ILjava/util/concurrent/ThreadFactory;)Ljava/util/concurrent/ScheduledExecutorService;"))

                || (name.equals("unconfigurableExecutorService") && descriptor.equalsIgnoreCase("(Ljava/util/concurrent/ExecutorService;)Ljava/util/concurrent/ExecutorService;"))

                || (name.equals("unconfigurableScheduledExecutorService") && descriptor.equalsIgnoreCase("(Ljava/util/concurrent/ScheduledExecutorService;)Ljava/util/concurrent/ScheduledExecutorService;"))

        ) {
            ThreadDataUtils.createExecutorsThreadList.add("jarName: $jarName \nclassName: $className \nmethod: $methodName \n$name:$descriptor\\n")
            mv.visitMethodInsn(opcode, owner, name, descriptor, itf)
            return
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, itf);
    }
}