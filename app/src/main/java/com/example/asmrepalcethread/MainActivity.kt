package com.example.asmrepalcethread

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.asmrepalcethread.java.JavaNewThread
import com.example.asmrepalcethread.java.JavaThreadPool
import com.example.asmrepalcethread.koltin.KoltinNewThread
import com.example.asmrepalcethread.koltin.KoltinThreadPool
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initJavaThread()

        initKoltinThread()
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
    }
}
