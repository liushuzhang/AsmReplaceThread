package com.example.asmrepalcethread

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class MainActivity : AppCompatActivity() {

    private val threadPoolExecutor =
        ThreadPoolExecutor(4 /* corePoolSize */,
            Int.MAX_VALUE,
            60L /* keepAliveTime */,
            TimeUnit.SECONDS,
            SynchronousQueue(),
            object : ThreadFactory {
                private val mCount =
                    AtomicInteger(1)

                override fun newThread(r: Runnable): Thread {
                    return Thread(
                        r,
                        "Main threadPoolExecutor #" + mCount.getAndIncrement()
                    )
                }
            })

    private val executor =
        Executors.newFixedThreadPool(
            4,
            object : ThreadFactory {
                private val mCount =
                    AtomicInteger(1)

                override fun newThread(r: Runnable): Thread {
                    return Thread(r, "MainJavaActivity #" + mCount.getAndIncrement())
                }
            })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        Thread(Runnable {
            Log.d("checkThread","new thread-----")
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
