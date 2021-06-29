package com.example.asmrepalcethread.koltin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asmrepalcethread.R;

/**
 * @Author: liushuzhang
 * @Date: 2021-03-19 09:53
 * @Description:
 */
class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String mess = "dasasdas";
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)findViewById(R.id.tv)).setText(mess);
                Log.d("assd","sdds----"+mess);
            }
        });
    }
}
