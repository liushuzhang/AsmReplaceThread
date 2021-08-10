package com.thread.plugin;

import com.android.build.gradle.AppExtension
import com.thread.plugin.check.CheckThreadExtension
import com.thread.plugin.check.CheckThreadTransform;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Properties;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-18 18:37
 * @Description:定义的Plugin入口
 */
class CheckThreadPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        CheckThreadExtension checkThreadExtension = project.getExtensions().create("checkThread", CheckThreadExtension)
        boolean disableCheckThreadPlugin = false
        Properties properties = new Properties()
        if (project.getRootProject().file("gradle.properties").exists()) {
            properties.load(project.getRootProject().file("gradle.properties").newDataInputStream())
            disableCheckThreadPlugin = Boolean.parseBoolean(properties.getProperty("checkThread.disablePlugin", "false"))
        }
        if (!disableCheckThreadPlugin) {
            println("----------检测线程的插件开始运行---------")
            project.afterEvaluate {
                AppExtension appExtension = project.extensions.findByType(AppExtension.class)
                appExtension.registerTransform(new CheckThreadTransform(project, checkThreadExtension))
            }
        } else {
            println("----------您关闭了检测线程的插件---------")
        }
    }
}
