package com.example.asmrepalcethread

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.asmrepalcethread.java.JavaNewThread
import com.example.asmrepalcethread.java.JavaThreadPool
import com.example.asmrepalcethread.koltin.KoltinNewThread
import com.example.asmrepalcethread.koltin.KoltinThreadPool
import com.example.asmrepalcethread.run.ShareThread
import com.example.asmrepalcethread.run.ThreadUtils
import com.example.replacecode.run.RunableUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val url = "http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg"
//        Glide.with(this)
//                .load(url)
//                .into(iv_pic)
//          val message:String?=null;

//
//        initJavaThread()
//
//        initKoltinThread()
//
//        netRequest()
//
//        getProcess()
//        addRunable()

        addRunableTest()

        tv_get.setOnClickListener {
            getRunable()
        }
    }

    fun addRunableTest(){
//        handler.postDelayed( {
            ShareThread().start()
//        },1000)

//        handler.postDelayed( {
            ThreadUtils.startThreadWithLambda()
//        },2000)

//        handler.postDelayed( {
            ThreadUtils.startThread()
//        },3000)

//        handler.postDelayed( {
            ThreadUtils.runWithThreadPool()
//        },4000)

//        handler.postDelayed( {
            ThreadUtils.runWithThreadPoolLambda()
//        },5000)

    }




     fun addRunable(){
        handler.postDelayed( {
            RunableUtils.addRunable("aaaa")
        },1000)
        handler.postDelayed( {
            RunableUtils.addRunable("bbbb")
        },2000)
        handler.postDelayed( {
            RunableUtils.addRunable("cccc")
        },3000)
        handler.postDelayed( {
            RunableUtils.addRunable("dddd")
        },4000)

    }

    fun getRunable(){
        RunableUtils.getSortedList().forEach {
           Log.d("testr","runName---${it.key}-----durtime---${it.startTime}")
        }
    }

    private fun getNull(mess:String?){
        var aa = mess?.length
    }

    private fun getProcess(){
        val activityManager = this
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val infos: List<RunningAppProcessInfo> = activityManager
            .getRunningAppProcesses()

        for (info in infos) {
            val name = info.processName
            val pid = info.pid
            Log.d("sasas","name-------"+name+"----pid------"+pid)
        }
    }

    private fun netRequest(){
        val url = "http://wwww.baidu.com"
        val okHttpClient = OkHttpClient()
        val request: Request = Request.Builder()
            .url(url)
            .get() //默认就是GET请求，可以不写
            .build()
        val call: Call = okHttpClient.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("dasas", "onFailure: ");
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d("dasas","onResponse: " + response.body().toString());
            }

        })

    }



    /**
     * Java创建线程
     */
    private fun initJavaThread() {
        //Java 创建新线程
        JavaNewThread.createNewThread()
        JavaNewThread.createExtendThread()

        //Java创建线程池
        JavaThreadPool.createCachedThreadPool()
        JavaThreadPool.createFixedThreadPool()
        JavaThreadPool.createSingleThreadPool()

        JavaThreadPool.executorsNewCacheThreadPool()
        JavaThreadPool.executorsNewFixedThreadPool()
        JavaThreadPool.executorsNewSingleThreadPool()
    }

    /**
     * Koltin创建线程
     */
    private fun initKoltinThread() {
        //Koltin 创建新线程
        KoltinNewThread.createNewThread()
        KoltinNewThread.createExtendThread()

        //Koltin 创建线程池
        KoltinThreadPool.createCachedThreadPool()
        KoltinThreadPool.createFixedThreadPool()
        KoltinThreadPool.createSingleThreadPool()

        KoltinThreadPool.executorsNewCacheThreadPool()
        KoltinThreadPool.executorsNewFixedThreadPool()
        KoltinThreadPool.executorsNewSingleThreadPool()
    }

}
