package be.kuleuven.study_tracker.Challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import CoreClasses.ImageProcessor;
import CoreClasses.User;
import Interfaces.VolleyCallBack;
import be.kuleuven.study_tracker.ChallengeOperations.ChallengeDatabaseHandler;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.R;

public class GradeActivity extends AppCompatActivity {
    int current,target;
    TextView label_grade;
    private ImageView imageView;
    private EditText grade;
    Button correct,wrong,submit;
    User user;
    private int PICK_IMAGE_REQUEST = 111;
    private Bitmap bitmap;
    ImageProcessor imageProcessor = new ImageProcessor();
    ChallengeDatabaseHandler cHandler = new ChallengeDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        user = getIntent().getParcelableExtra("User");
        current =getIntent().getIntExtra("Current",0);
        target =getIntent().getIntExtra("Target",0);
        imageView = findViewById(R.id.img_solution);
        grade = findViewById(R.id.edit_grade);
        correct = findViewById(R.id.btn_correct);
        wrong = findViewById(R.id.btn_wrong);
        label_grade = findViewById(R.id.lbl_grade);
        submit = findViewById(R.id.btn_submit_grade);

        label_grade.setVisibility(View.INVISIBLE);
        grade.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);



        cHandler.getAnswer(current, target, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                imageView.setImageBitmap(cHandler.getGetAnswerBitamp());
            }

            @Override
            public void onFail() {

            }
        });

    }

    public void onBtnCorrect_CLicked(View caller)
    {
        correct.setVisibility(View.INVISIBLE);
        wrong.setVisibility(View.INVISIBLE);
        label_grade.setVisibility(View.VISIBLE);
        grade.setVisibility(View.VISIBLE);
        submit.setVisibility(View.VISIBLE);

    }

    public void onBtnWrong_CLicked(View caller)
    {
        label_grade.setVisibility(View.INVISIBLE);
        grade.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);

        cHandler.wrongAnswerDB(target);

    }

    public void onBtnSubmitGrade_Clicked(View caller)
    {
        String gardeVal = grade.getText().toString();
        
        cHandler.correctAnswerDB(target,gardeVal);
        cHandler.clearQAPair(current,target);

        Toast.makeText(this, "Graded successful", Toast.LENGTH_SHORT).show();

        submit.setVisibility(View.INVISIBLE);
        grade.setVisibility(View.INVISIBLE);

    }

    public void onBtnBackgrade_Clicked(View view) {

        Intent intent = new Intent(this, GroupPageActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}