package com.example.asmrepalcethread

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.asmrepalcethread.java.JavaNewThread
import com.example.asmrepalcethread.java.JavaThreadPool
import com.example.asmrepalcethread.koltin.KoltinNewThread
import com.example.asmrepalcethread.koltin.KoltinThreadPool
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val url = "http://img1.dzwww.com:8080/tupian_pl/20150813/16/7858995348613407436.jpg"
//        Glide.with(this)
//                .load(url)
//                .into(iv_pic)
          val message:String?=null;

//
//        initJavaThread()
//
//        initKoltinThread()
//
//        netRequest()
//
//        getProcess()
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
