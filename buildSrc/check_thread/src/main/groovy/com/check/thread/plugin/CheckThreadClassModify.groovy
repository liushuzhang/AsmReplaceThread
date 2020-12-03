package com.check.thread.plugin

import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.IOUtils

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.regex.Matcher

class CheckThreadClassModify {

    /**
     * 修改class文件
     * @param dirFile 文件夹
     * @param classFile 文件
     * @param tempDir 临时文件夹地址
     * @return
     */
    static File modifyClassFile(File dirFile, File classFile, File tempDir) {
        File modified = null
        try {
            String path = pathSeparator2Point(classFile.absolutePath.replace(dirFile.absolutePath + File.separator, ""))
            String className = moveAfterFix(path)
            byte[] sourceClassBytes = IOUtils.toByteArray(new FileInputStream(classFile))
            byte[] modifyClassBytes = modifyClass(sourceClassBytes, "")
            if (modifyClassBytes) {
                modified = new File(tempDir, className.replace(".", "") + ".class")
            }
            if (modified.exists()) {
                modified.delete()
            }
            modified.createNewFile()
            new FileOutputStream(modified).write(modifyClassBytes)
        } catch (Exception e) {
            e.printStackTrace()
            modified = classFile
        }
        return modified
    }

    /**
     * 修改jar文件
     * @param jarFile
     * @param tempDir
     * @param nameHex
     * @return
     */
    static File modifyJar(File jarFile, File tempDir, boolean nameHex) {
        def file = new JarFile(jarFile, false)
        def hexName = ""
        if (nameHex) {
            hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        }
        def outPutJar = new File(tempDir, hexName + jarFile.name)
        JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(outPutJar))
        Enumeration enumeration = file.entries()
        while (enumeration.hasMoreElements()) {
            JarEntry jarEntry = enumeration.nextElement()
            InputStream inputStream
            try {
                inputStream = file.getInputStream(jarEntry)
            } catch (Exception e) {
                return null
            }
            String entryName = jarEntry.getName()
            if (entryName.endsWith(".DSA") || entryName.endsWith(".SF")) {

            } else {
                String className
                JarEntry jarEntry2 = new JarEntry(entryName)
                jarOutputStream.putNextEntry(jarEntry2)

                byte[] modifiedClassBytes = null
                byte[] sourceClassBytes = IOUtils.toByteArray(inputStream)
                if (entryName.endsWith(".class")) {
                    className = entryName.replace(Matcher.quoteReplacement(File.separator), ".").replace(".class", "")
                    if (FilterClass.canModify(className)) {
                        modifiedClassBytes = modifyClass(sourceClassBytes, jarFile.name)
                    }
                }
                if (modifiedClassBytes == null) {
                    modifiedClassBytes = sourceClassBytes
                }
                jarOutputStream.write(modifiedClassBytes)
                jarOutputStream.closeEntry()
            }
        }
        jarOutputStream.close()
        file.close()
        return outPutJar
    }

    /**
     * 利用ASM修改class字节码
     * @param srcClass
     * @return
     * @throws IOException
     */
    private static byte[] modifyClass(byte[] srcClass, String jarName) throws IOException {
        //ClassWriter的flags
        //new ClassWrite（0）：这种方式不会自动计算操作数栈和局部变量表的大小，需要我们手动指定
        //new ClassWrite（ClassWriter.COMPUTE_MAXS）：这种方式会自动计算操作数栈和局部变量表的大小，前提是需要调用visitMaxs方法来触发计算上述两个值，参数值可以随意指定。
        //new ClassWrite（ClassWriter.COMPUTE_FRAMES）：不仅会自动计算操作数栈和局部变量表的大小，还会自动计算StackMapFrames。在Java6之后JVM在class的Code属性引入StackMapFrames
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS)
        ClassVisitor classVisitor = new CheckThreadClassVisitor(classWriter, jarName)
        ClassReader classReader = new ClassReader(srcClass)
        //flags几个参数含义
        //SKIP_DEBUG:跳过类文件中的调试信息，比如行号信息（LineNumberTable）
        //SKIP_CODE：跳过方法体中的Code属性（方法字节码、异常表等）
        //EXPAND_FRAMES:展开StackMapTable属性
        //SKIP_FRAMES：跳过StackMapTable属性
        classReader.accept(classVisitor, ClassReader.SKIP_FRAMES)
        return classWriter.toByteArray()
    }


    /**
     * 修改路径 把com/example/replacecode 修改成 com.example.replacecode
     * @param pathName
     * @return
     */
    static String pathSeparator2Point(String pathName) {
        return pathName.replace(File.separator, ".")
    }

    /**
     * 修改路径 把com.example.replacecode修改成 com/example/replacecode
     * @param pathName
     * @return
     */
    static String pathPoint2Separator(String pathName) {
        return pathName.replace(".", File.separator)
    }

    static String moveAfterFix(String file) {
        return file.replace(".class", ".")
    }
}