package com.thread.plugin.time

import org.objectweb.asm.Handle
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import com.thread.plugin.constant.ClassConstant
import com.thread.plugin.constant.ThreadDataUtils
import org.objectweb.asm.Type
import com.thread.plugin.constant.TimeThreadUtils


/**
 * 具体执行方法的时候调用
 */
class ThreadTimeMethodVisitor extends MethodVisitor {
    //jar 名字
    private String jarName
    //class名字
    private String className
    //method 名字
    private String methodName

    private boolean isRun = false

    private String nameDesc;

    ThreadTimeMethodVisitor(int api, MethodVisitor mv, String jarName, String className, String methodName, boolean isRun, String nameDesc) {
        super(api, mv)
        this.jarName = jarName
        this.className = className
        this.methodName = methodName
        this.isRun = isRun
        this.nameDesc = nameDesc

    }


    /**
     * @param opcode 主要处理这几种操作符NEW, ANEWARRAY, CHECKCAST or INSTANCEOF.
     * @param type
     */
    @Override
    void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
    }

    @Override
    void visitInsn(int opcode) {
        if (((isRun)) && ((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW)) {
            mv.visitLdcInsn("JarName:" + jarName + "  className:" + className + " methodName:" + methodName)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/replacecode/run/RunableUtils", "removeRunable", "(Ljava/lang/String;)V", false);
        }
        super.visitInsn(opcode)

    }

    @Override
    void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc)
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
        super.visitMethodInsn(opcode, owner, name, descriptor, itf);
    }

    @Override
    void visitCode() {
        super.visitCode()
        if (isRun) {
            if (TimeThreadUtils.hashMap.containsKey(nameDesc)) {
                TimeThreadUtils.hashMap.remove(nameDesc)
            }
            mv.visitLdcInsn("JarName:" + jarName + "  className:" + className + " methodName:" + methodName)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "com/example/replacecode/run/RunableUtils", "addRunable", "(Ljava/lang/String;)V", false);
        }
    }

    @Override
    void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs)
        try {
            if (name == "run" && desc == "()Ljava/lang/Runnable;") {
                Handle it = (Handle) bsmArgs[1]
                System.out.println("---mLambdaMethodCells-----" + it.name + it.desc)
                TimeThreadUtils.hashMap.put(it.name + it.desc, name + desc)
            }

        } catch (Exception e) {
            e.printStackTrace()
        }
        // methodVisitor.visitInvokeDynamicInsn("run", "()Ljava/lang/Runnable;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/LambdaMetafactory", "metafactory", " (Ljava/lang/invoke/MethodHandles $ Lookup ;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite; ", false), new Object[]{Type.getType("()V"), new Handle(Opcodes.H_INVOKESTATIC, "com/example/asmrepalcethread/run/ThreadUtils", "lambda$runWithThreadPoolLambda$1", "()V", false), Type.getType("()V")});
    }


}