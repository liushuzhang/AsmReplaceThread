package com.thread.plugin;

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import com.thread.plugin.time.ThreadTimeExtension
import com.thread.plugin.time.ThreadTimeTransform

import java.util.Properties;

/**
 * @Author: liushuzhang
 * @Date: 2020-11-18 18:37
 * @Description:定义的Plugin入口
 */
class ThreadTimePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        ThreadTimeExtension threadTimeExtension = project.getExtensions().create("threadTime", ThreadTimeExtension)
        boolean disableCheckThreadPlugin = false
        Properties properties = new Properties()
        if (project.getRootProject().file("gradle.properties").exists()) {
            properties.load(project.getRootProject().file("gradle.properties").newDataInputStream())
            disableCheckThreadPlugin = Boolean.parseBoolean(properties.getProperty("threadTime.disablePlugin", "false"))
        }
        if (!disableCheckThreadPlugin) {
            println("----------检测线程时间的插件开始运行---------")
            project.afterEvaluate {
                AppExtension appExtension = project.extensions.findByType(AppExtension.class)
                appExtension.registerTransform(new ThreadTimeTransform(project, threadTimeExtension))
            }
        } else {
            println("----------您关闭了检测线程时间的插件---------")
        }
    }
}
