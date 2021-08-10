package com.example.replacecode.basethread;

import com.example.replacecode.run.RunableUtils;

/**
 * @Author: liushuzhang
 * @Date: 2021-07-18 22:19
 * @Description:
 */
abstract class BaseRunable implements Runnable {

    @Override
    public void run() {
        RunableUtils.addRunable("startName");
        realRun();
        RunableUtils.removeRunable("startName");
    }

    abstract void realRun();

}
