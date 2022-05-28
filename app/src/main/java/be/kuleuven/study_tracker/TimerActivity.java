package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.Locale;

import CoreClasses.DataBaseHandler;
import CoreClasses.User;
import Interfaces.VolleyCallBack;

public class TimerActivity extends AppCompatActivity {
    private User user;
    private int START_TIME_IN_MILLIS = 600000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private int min =START_TIME_IN_MILLIS/60000;
    DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        user = getIntent().getParcelableExtra("User");

        mTextViewCountDown = findViewById(R.id.timer_module);
        mButtonStartPause = findViewById(R.id.mButtonStartPause);
        mButtonReset = findViewById(R.id.mButtonReset);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();

    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                updateScore(START_TIME_IN_MILLIS);
                mButtonStartPause.setText("Start");
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();

        mTimerRunning = true;
        mButtonStartPause.setText("pause");
        mButtonReset.setVisibility(View.INVISIBLE);

    }



    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText("Start");
        mButtonReset.setVisibility(View.VISIBLE);

    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    public void onBtn_plus(View v)
    {
        START_TIME_IN_MILLIS = START_TIME_IN_MILLIS + (10*60000);
        mTimeLeftInMillis = mTimeLeftInMillis + (10*60000);
        updateCountDownText();
    }

    public void onBtn_minus(View v)
    {
        START_TIME_IN_MILLIS = START_TIME_IN_MILLIS - (10*60000);
        mTimeLeftInMillis = mTimeLeftInMillis - (10*60000);
        if(mTimeLeftInMillis<600000)
        {
            START_TIME_IN_MILLIS = 600000;
            mTimeLeftInMillis = 600000;
            Toast.makeText(this, "10 min is the least possible time", Toast.LENGTH_SHORT).show();
        }
        updateCountDownText();
    }

    private void updateScore(int start_time_in_millis) {

        dataBaseHandler.updateScore(start_time_in_millis/60000,user.getIdUser(),new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(TimerActivity.this, "Score Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(TimerActivity.this, "Unable to update score", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    public void onBtnBack_Clicked(View caller)
    {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}