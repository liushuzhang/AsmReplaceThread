package com.thread.plugin.check

import org.apache.http.util.TextUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import com.thread.plugin.constant.ClassConstant
import com.thread.plugin.constant.ThreadDataUtils


class CheckThreadClassVisitor extends ClassVisitor implements Opcodes {
    //类名
    private String className;
    //jar名字
    private String jarName;

    CheckThreadClassVisitor(final ClassVisitor classVisitor, String jarName) {
        super(Opcodes.ASM6, classVisitor)
        this.jarName = (TextUtils.isEmpty(jarName) ? "localFile" : jarName)
    }

    /**
     * 解析class文件的入口，每个类仅访问一次
     * @param version class版本
     * @param access （关键字的相加）关键字如final等
     * @param name 类全限定名（包含路径，包中的点变成对应的/）
     * @param signature 泛型签名
     * @param superName 父类
     * @param interfaces 接口
     */
    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        className = name
        /**
         * 关于这块的处理逻辑为何是这样的解析：
         * 修饰class的Opcodes标识的符号是经过选择的，转化为对应的二进制都是某一位为1，其余为0
         * 如果有多个修饰符，在把多个Opcodes的值相加
         * 比如我如果发现一个code为49，则对应的二进制00110001，可以发现该二进制有3个1，则对应的有三个修饰符
         * 0000 0001、0001 0000、0010 0000 转化为10进制为 1、16、32 对应的Opcodes码为 ACC_PUBLIC 、ACC_FINAL、ACC_SUPER
         * 即该类的定义时应该是这样的 public final className extend superClass
         * 由此可知下面的判断逻辑是，该类是有继承关系的
         * 下面这段代码的作用就是统计继承线程相关类的代码的地方
         */
        if ((access & Opcodes.ACC_SUPER) > 0) {
            switch (superName) {
                case ClassConstant.M_Thread:
                    ThreadDataUtils.extendThreadList.add("jarName: $jarName \nClassName: $className  \nextends $superName  \n")
                    break
                case ClassConstant.M_ThreadPoolExecutor:
                    ThreadDataUtils.extendThreadPooList.add("jarName: $jarName\nClassName: $className \nextends $superName\n")
                    break
                case ClassConstant.M_ScheduledThreadPoolExecutor:
                    ThreadDataUtils.extendScheduledThreadPoolExecutor.add("jarName:$jarName \nClassName: $className \nextends $superName\n")
                    break
                case ClassConstant.M_ForkJoinPool:
                    ThreadDataUtils.extendForkJoinPool.add("jarName: $jarName \nClassName: $className \nextends $superName\n")
                    break
                case ClassConstant.M_Executors:
                    ThreadDataUtils.extendExecutorsThreadList.add("jarName: $jarName \nClassName: $className \nextends $superName\n")
                    break
            }

        }
        super.visit(version, access, name, signature, superName, interfaces)
    }

    /**
     * 访问方法，这里访问的次数和方法数相关
     * @param access 方法访问标记
     * @param name 方法名字
     * @param desc 方法签名
     * @param signature 大部分情况为null,在使用泛型情况下：
     * -----desc----(Ljava/lang/Object;Ljava/lang/String;)Ljava/lang/Object;
     * -----signature----(TT;Ljava/lang/String;)TT;
     * @param exceptions 异常信息
     * @return
     */
    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions)
        mv = new CheckThreadMethodVisitor(ASM6, mv, jarName, className, name)
        return mv
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }


}