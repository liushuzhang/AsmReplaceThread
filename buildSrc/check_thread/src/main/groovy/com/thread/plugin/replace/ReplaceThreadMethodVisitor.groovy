package com.thread.plugin.replace

import com.thread.plugin.constant.ClassConstant
import com.thread.plugin.constant.ThreadDataUtils
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

/**
 * 具体执行方法的时候调用
 * 这里进行替换线程池的操作
 * 线程池的创建分为两种
 * 第一种 new ThreadPoolExecutor
 * 第二种 Executors调用方法创建
 *
 * 针对第一种
 * 针对new的操作，在字节码过程中会产生三个指令
 * new  创建一个累的实例
 * dup  复制到栈顶
 * invokespecial 调用构造函数
 * 我们希望的方式是使用我们公用的线程池替换整个操作
 * 方案一：
 * 通过new匹配到对应创建线程池操作，然后取消dup和invokespecial操作，替换到我们自己的线程池
 * 方案二：
 * 在变量赋值的时候进行修改，因为赋值操作只有一个指令，所以替换一个指令即可
 * 缺点是：此时线程池已经创建，只是后续传递的过来的Runable使用我们的线程进行处理
 * 同时由于变量定义的时候名称不固定，所以不能统一处理，只能单个处理实现，所以不推荐
 * 处理细节，拦截赋值操作符visitFieldInsn
 * 调用我们的方法获取线程池，赋值给该变量
 *
 */
class ReplaceThreadMethodVisitor extends MethodVisitor {
    //jar 名字
    private String jarName
    //class名字
    private String className
    //method 名字
    private String methodName

    private boolean isStopDub;
    private boolean isReplaceInit;

    ReplaceThreadMethodVisitor(int api, MethodVisitor mv, String jarName, String className, String methodName) {
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
                    break
                case ClassConstant.M_ThreadPoolExecutor:
                    //匹配到ThreadPoolExecutor，取消new操作
                    // mv.visitTypeInsn(opcode, type);
                    println("new----jarName---" + jarName + "---className----" + className + "---methodName---" + methodName);
                    isStopDub = true
                    isReplaceInit = true
                    return
                case ClassConstant.M_ScheduledThreadPoolExecutor:
                    break
                case ClassConstant.M_ForkJoinPool:
                    break
                case ClassConstant.M_Executors:
                    break
            }
        }
        super.visitTypeInsn(opcode, type);
    }

    @Override
    void visitInsn(int opcode) {
        if (opcode == Opcodes.DUP && isStopDub) {
            //取消复制操作
           // super.visitInsn(opcode);
            isStopDub = false;
            println("dup----jarName---" + jarName + "---className----" + className + "---methodName---" + methodName);
            return
        }
        super.visitInsn(opcode);
    }

    /**
     *
     * @param opcode
     * @param owner
     * @param name
     * @param desc
     */
    @Override
    void visitFieldInsn(int opcode, String owner, String name, String desc) {
        //这里替换线程池的方式，是在set变量的时候替换
//        if (opcode == Opcodes.PUTFIELD && owner.equals("com/example/aop/MyTest") && name.equals("executor") && desc.equals("Ljava/util/concurrent/ThreadPoolExecutor;")) {
//            //把当前的this设置到栈顶
//            mv.visitVarInsn(Opcodes.ALOAD, 0);
//            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/aop/asm/ThreadPoolUtil", "getAAThread", "()Ljava/util/concurrent/ThreadPoolExecutor;", false);
//            mv.visitFieldInsn(Opcodes.PUTFIELD, "com/example/aop/MyTest", "executor", "Ljava/util/concurrent/ThreadPoolExecutor;");
//            return;
//        }
        super.visitFieldInsn(opcode, owner, name, desc);
    }

    /**
     * 方法调用
     * @param opcode INVOKEVIRTUAL INVOKESPECIAL INVOKESTATIC INVOKEINTERFACE
     * @param owner
     * @param name
     * @param descriptor
     * @param itf
     */
    @Override
    void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean itf) {
        if (opcode == Opcodes.INVOKESPECIAL && owner.equals("java/util/concurrent/ThreadPoolExecutor") && name.equals("<init>") && isReplaceInit) {
            /**
             * 取消init操作，修改为我们全局的线程方法进行替换，需要替换的是：
             * opcode 从调用构造方法用的INVOKESPECIAL 替换成我们用的 静态方法INVOKESTATIC
             * 替换类名和方法名，需要注意的是为了减少栈的操作，我们的方法参数应尽量和构造方法传递进来的参数一致
             * descriptor方法签名修改，由于原来的构造方法返回值为void，而我们当前的返回ThreadPoolExecutor这个类，所以方法签名也进行修改
             */
            println("initThreadPoolExecutor----jarName---" + jarName + "---className----" + className + "---methodName---" + methodName);
            isReplaceInit = false
            descriptor = descriptor.substring(0, descriptor.length() - 1) + "Ljava/util/concurrent/ThreadPoolExecutor;"
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/replacecode/ReThreadPoolExecutor", "getThreadPoolExecutor", descriptor, false);
            return
        } else if ((name.equals("newWorkStealingPool") && descriptor.equalsIgnoreCase("(I)Ljava/util/concurrent/ExecutorService;"))

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
            mv.visitMethodInsn(opcode, owner, name, descriptor, itf)
            return
        }
        super.visitMethodInsn(opcode, owner, name, descriptor, itf);
    }
}