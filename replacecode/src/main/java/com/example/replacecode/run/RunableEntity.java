package com.example.replacecode.run;

/**
 * @Author: liushuzhang
 * @Date: 2021-07-11 22:53
 * @Description:
 */
public class RunableEntity  {
    public String key;
    public long startTime;

    public RunableEntity(String key,long startTime){
        this.key = key;
        this.startTime = startTime;
    }

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
