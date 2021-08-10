package com.thread.plugin.check

import com.android.build.api.transform.Context
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformException
import com.android.build.api.transform.TransformInput
import com.android.build.api.transform.TransformInvocation
import com.android.build.api.transform.TransformOutputProvider
import com.android.build.gradle.internal.pipeline.TransformManager
import groovy.io.FileType
import org.apache.commons.io.FileUtils
import org.gradle.api.Project
import org.apache.commons.codec.digest.DigestUtils
import com.thread.plugin.constant.ThreadDataUtils
import com.thread.plugin.constant.FilterClass


import java.util.Set;

/**
 * @Author: liushuzhang* @Date: 2020-11-18 19:55
 * @Description:
 *  Transform实质上是Gradle中的一个task ，在plugin中注册进去
 *  在gradle tools插件中 addCompileTask(variantScope);
 *  此处会调用到 createPostCompilationTasks(variantScope) 方法
 *  ,此处会调用对应的方法将我们自定义的transform加入到其中
 *
 */
class CheckThreadTransform extends Transform {
    private static Project project
    private CheckThreadExtension checkThreadExtension

    CheckThreadTransform(Project project, CheckThreadExtension checkThreadExtension) {
        this.project = project
        this.checkThreadExtension = checkThreadExtension
    }

    @Override
    String getName() {
        return "checkThreadTransform"
    }

    /**
     * 需要处理的数据类型，有两种枚举类型
     * CLASSES 代表处理的 java 的 class 文件，RESOURCES 代表要处理 java 的资源
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 指 Transform 要操作内容的范围，官方文档 Scope 有 7 种类型：
     * 1. EXTERNAL_LIBRARIES        只有外部库
     * 2. PROJECT                   只有项目内容
     * 3. PROJECT_LOCAL_DEPS        只有项目的本地依赖(本地jar)
     * 4. PROVIDED_ONLY             只提供本地或远程依赖项
     * 5. SUB_PROJECTS              只有子项目。
     * 6. SUB_PROJECTS_LOCAL_DEPS   只有子项目的本地依赖项(本地jar)。
     * 7. TESTED_CODE               由当前变量(包括依赖项)测试的代码
     * @return
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        _transform(transformInvocation.context, transformInvocation.inputs, transformInvocation.outputProvider, transformInvocation.incremental)
    }

    void _transform(Context context, Collection<TransformInput> inputs, TransformOutputProvider outputProvider, boolean isIncremnetal) throws IOException, TransformException, InterruptedException {
        if (!incremental) {
            outputProvider.deleteAll()
            ThreadDataUtils.clearData()
        }
        println("----------transform   checkthread---------")

        /**
         * inputs有两种类型：
         * 一种是目录，这种情况多是我们自己的代码
         * 另一种是第三方的jar文件
         * 需要注意的是在打包的过程中androd.jar文件不参与打包，即我们系统提供的一些API是无法修改
         */
        inputs.forEach { TransformInput input ->

            //遍历目录
            input.directoryInputs.each {
                //本地class文件的绝对路径
                DirectoryInput directoryInput ->
                //定义输入文件的文件名、目录、类型等
                File dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                        directoryInput.scopes, Format.DIRECTORY)
                File dir = directoryInput.file
                if (dir) {
                    HashMap<String, File> modifyMap = new HashMap<>()
                    //遍历以某一扩展名结尾的文件
                    dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File classFile ->
                        if (FilterClass.canModify(classFile.name)) {
                            //模块里的开关，默认是开启的
                            if (!checkThreadExtension.disableCheckThread) {
                                File modifiedFile = CheckThreadClassModify.modifyClassFile(dir, classFile, context.getTemporaryDir())
                                if (modifiedFile != null) {
                                    //文件的绝对路径-文件夹的绝对路径 = 包名+类名 如下所示
                                    //dir.absolutePath :  /Users/liushu1123/Documents/Code/AsmReplaceThread/app/build/tmp/kotlin-classes/debug
                                    //classFile.absolutePath: /Users/liushu1123/Documents/Code/AsmReplaceThread/app/build/tmp/kotlin-classes/debug/com/example/asmrepalcethread/MainActivity.class
                                    //两者相减 com/example/asmrepalcethread/MainActivity.class
                                    String key = classFile.absolutePath.replace(dir.absolutePath, "")
                                    //存储修改的文件
                                    modifyMap.put(key, modifiedFile)
                                }
                            }
                        }

                    }
                    //copy 文件夹到对应点的位置
                    FileUtils.copyDirectory(directoryInput.file, dest)
                    //copy修改过的class文件，如果目标文件夹中存在该文件，则删除
                    modifyMap.entrySet().each { Map.Entry<String, File> entry ->
                        File target = new File(dest.absolutePath + entry.getKey())
                        if (target.exists()) {
                            target.delete()
                        }
                        FileUtils.copyFile(entry.getValue(), target)
                        entry.getValue().delete()
                    }
                }

            }

            //遍历jar文件
            input.jarInputs.each { JarInput jarInput ->
                //获取jar文件名字
                String destName = jarInput.file.name
                //获取文件绝对路径的MD5值，并截取前8位，防止输出文件名字重复
                String hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                //获取输出文件的地址
                File dest = outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes,
                        jarInput.scopes, Format.JAR)
                def modifyJar = null
                if (!checkThreadExtension.disableCheckThread) {
                    modifyJar = CheckThreadClassModify.modifyJar(jarInput.file, context.getTemporaryDir(), true)
                }
                if (modifyJar == null) {
                    modifyJar = jarInput.file
                }
                FileUtils.copyFile(modifyJar, dest)
            }

        }
        ThreadDataUtils.writeThreadToFile()
    }
}
