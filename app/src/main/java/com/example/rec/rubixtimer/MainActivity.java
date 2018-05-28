package com.example.rec.rubixtimer;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.os.CountDownTimer;

import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView timer ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds ;
    private static final long START_TIME_IN_MILLIS = 15000;

    private RelativeLayout layout;
    private TextView mTextViewCountDown;


    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView)findViewById(R.id.tvTimer);


        mTextViewCountDown = findViewById(R.id.text_view_countdown);

        layout=findViewById(R.id.layout);




        countdown();





    }

    public Runnable runnable = new Runnable() {

        @SuppressLint({"SetTextI18n", "DefaultLocale"})
        public void run() {

            MillisecondTime = SystemClock.uptimeMillis()-StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            timer.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }

    };
    @SuppressLint("ClickableViewAccessibility")
    public void timer(){
        mTextViewCountDown.setVisibility(View.INVISIBLE);

        timer.setVisibility(View.VISIBLE);


        handler = new Handler() ;



        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);



        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    TimeBuff += MillisecondTime;

                    handler.removeCallbacks(runnable);

                }return true;

            }
        });


    }
    @SuppressLint("ClickableViewAccessibility")
    public void countdown(){
        mTextViewCountDown.setVisibility(View.VISIBLE);

        timer.setVisibility(View.INVISIBLE);



        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {
                    if (mTimerRunning) {
                        pauseTimer();
                    } else {
                        startTimer();
                    }
                }
                return true;
            }
        });





        updateCountDownText();
    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;

                timer();

            }
        }.start();

        mTimerRunning = true;


    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;


    }



    private void updateCountDownText() {
        int milliseconds = (int) (mTimeLeftInMillis % 1000);
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d",seconds,  milliseconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}