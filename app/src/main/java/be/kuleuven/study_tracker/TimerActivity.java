package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import CoreClasses.User;

public class TimerActivity extends AppCompatActivity {
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        user = getIntent().getParcelableExtra("User");
    }
}