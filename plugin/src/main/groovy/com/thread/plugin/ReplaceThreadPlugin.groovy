package com.thread.plugin

import com.android.build.gradle.AppExtension
import com.thread.plugin.check.CheckThreadExtension
import com.thread.plugin.check.CheckThreadTransform
import com.thread.plugin.replace.RepalceThreadExtension
import com.thread.plugin.replace.ReplaceThreadTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @Author: liushuzhang* @Date: 2020-11-18 18:37
 * @Description:定义的Plugin入口
 */
class ReplaceThreadPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        RepalceThreadExtension replaceThreadExtension = project.getExtensions().create("replaceThread", RepalceThreadExtension)
        boolean disableRepalceThreadPlugin = false
        Properties properties = new Properties()
        if (project.getRootProject().file("gradle.properties").exists()) {
            properties.load(project.getRootProject().file("gradle.properties").newDataInputStream())
            disableRepalceThreadPlugin = Boolean.parseBoolean(properties.getProperty("replaceThread.disablePlugin", "false"))
        }
        if (!disableRepalceThreadPlugin) {
            println("----------替换线程的插件开始运行---------")
            project.afterEvaluate {
                AppExtension appExtension = project.extensions.findByType(AppExtension.class)
                appExtension.registerTransform(new ReplaceThreadTransform(project, replaceThreadExtension))
            }
        } else {
            println("----------您关闭了替换线程的插件---------")
        }
    }
}
