package be.kuleuven.study_tracker.Challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import CoreClasses.ImageProcessor;
import CoreClasses.User;
import Interfaces.VolleyCallBack;
import be.kuleuven.study_tracker.ChallengeOperations.ChallengeDatabaseHandler;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.R;

public class AnswerActivity extends AppCompatActivity {
    int current,target;
    User user;
    ImageView img_question,img_answer;
    private Button pick,submit;
    private int PICK_IMAGE_REQUEST = 111;
    private Bitmap bitmap;
    ImageProcessor imageProcessor = new ImageProcessor();
    ChallengeDatabaseHandler cHandler = new ChallengeDatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        user = getIntent().getParcelableExtra("User");
        current =getIntent().getIntExtra("Current",0);
        target =getIntent().getIntExtra("Target",0);
        img_question = findViewById(R.id.img_question);
        img_answer = findViewById(R.id.img_answer);
        pick = findViewById(R.id.btn_pick_answer);
        submit = findViewById(R.id.btn_submit_answer);

        cHandler.getQuestion(target, current, new VolleyCallBack() {
            @Override
            public void onSuccess() {
                img_question.setImageBitmap(cHandler.getGetQuestionBitmap());


            }

            @Override
            public void onFail() {

            }
        });
    }

    public void btnPick_Clicked(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);

        //this line will start the new activity and will automatically run the callback method below when the user has picked an image
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Rescale the bitmap to 400px wide (avoid storing large images!)
                bitmap = imageProcessor.getResizedBitmap( bitmap, 400);

                //Setting image to ImageView
                img_answer.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void btnSubmit_Clicked(View view) {
        cHandler.addAnswertoDB(imageProcessor.toBase64(bitmap),target,current);
        Toast.makeText(this, "Answer sent", Toast.LENGTH_SHORT).show();
        pick.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
    }

    public void onBtnBackAnswer_Clicked(View view) {

        Intent intent = new Intent(this, GroupPageActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}