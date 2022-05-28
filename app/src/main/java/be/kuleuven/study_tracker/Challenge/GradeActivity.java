package be.kuleuven.study_tracker.Challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import be.kuleuven.study_tracker.R;

public class GradeActivity extends AppCompatActivity {
    int current,target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        current =getIntent().getIntExtra("Current",0);
        target =getIntent().getIntExtra("Target",0);
    }
}