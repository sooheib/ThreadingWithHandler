package com.vivianaranha.threadingwithhandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    Thread thread;
    Handler handler;
    ProgressBar progessBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        progessBar = (ProgressBar) findViewById(R.id.progressBar);
        progessBar.setVisibility(View.GONE);

    }

    public void startThread(View view) {
        button.setVisibility(View.GONE);
        progessBar.setVisibility(View.VISIBLE);
        thread = new Thread(new ProcessThis());
        thread.start();
// Handler in the main thread
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                progessBar.setProgress(msg.arg1);
                if(msg.arg1 == 100){
                    progessBar.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                }

            }
        };

    }

    class ProcessThis implements Runnable{

        @Override
        public void run() {
            for(int i=0; i<=100 ; i++) {
                Message message = Message.obtain();
                message.arg1 = i;
                handler.sendMessage(message);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
