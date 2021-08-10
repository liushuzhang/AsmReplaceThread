package com.thread.plugin.replace


import org.apache.http.util.TextUtils
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

class ReplaceThreadClassVisitor extends ClassVisitor implements Opcodes {
    //类名
    private String className;
    //jar名字
    private String jarName;

    ReplaceThreadClassVisitor(final ClassVisitor classVisitor, String jarName) {
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
        mv = new ReplaceThreadMethodVisitor(ASM6, mv, jarName, className, name)
        return mv
    }

    @Override
    void visitEnd() {
        super.visitEnd()
    }


}