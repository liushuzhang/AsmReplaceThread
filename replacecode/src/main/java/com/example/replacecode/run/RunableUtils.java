package com.example.replacecode.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: liushuzhang
 * @Date: 2021-07-11 22:41
 * @Description:
 */
public class RunableUtils {
    public static ConcurrentHashMap<String,Long> map = new ConcurrentHashMap<>();

    public static void addRunable(String runName){
        map.put(runName,System.currentTimeMillis());
    }

    public static void removeRunable(String runName){
        if(map.containsKey(runName)){
            map.remove(runName);
        }
    }

    public static List<RunableEntity> getSortedList(){
        long currentTime = System.currentTimeMillis();
        List<RunableEntity> mlist = new ArrayList<>();
        for(Map.Entry entry: map.entrySet()) {
            mlist.add(new RunableEntity((String)entry.getKey(),currentTime - (long)entry.getValue()));
        }
        Collections.sort(mlist, new Comparator<RunableEntity>() {
            @Override
            public int compare(RunableEntity p1, RunableEntity p2) {
                if (p1.getStartTime()>p2.getStartTime())
                    return -1;
                else if (p1.getStartTime()<p2.getStartTime())
                    return 1;
                else
                    return 0;
            }
        });
        return mlist;
    }
}
