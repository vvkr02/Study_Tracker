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
import android.widget.TextView;
import android.widget.Toast;

import CoreClasses.DataBaseHandler;
import CoreClasses.ImageProcessor;
import CoreClasses.User;
import be.kuleuven.study_tracker.ChallengeOperations.ChallengeDatabaseHandler;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.R;

public class ChallengeActivity extends AppCompatActivity {

    int current,target;
    private ImageView imageView;
    private TextView label;
    User user;
    private int PICK_IMAGE_REQUEST = 111;
    private Bitmap bitmap;
    ImageProcessor imageProcessor = new ImageProcessor();
    ChallengeDatabaseHandler cHandler = new ChallengeDatabaseHandler(this);
    Button pick,challenge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        user = getIntent().getParcelableExtra("User");
        current =getIntent().getIntExtra("Current",0);
        target =getIntent().getIntExtra("Target",0);
        imageView = findViewById(R.id.image_view);
        pick = findViewById(R.id.btn_prof);
        challenge = findViewById(R.id.btn_challenge);
        label = findViewById(R.id.textView3);

    }

    public void onBtnPickClicked(View caller)
    {
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
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onBtnPostClicked(View view) {
        cHandler.addQuestiontoDB(imageProcessor.toBase64(bitmap),current,target);
        Toast.makeText(this, "Question sent", Toast.LENGTH_SHORT).show();
        pick.setVisibility(View.INVISIBLE);
        challenge.setVisibility(View.INVISIBLE);
        label.setText("Question you Uploaded");
    }

    public void onBtnBackChallenge_Clicked(View caller)
    {
        Intent intent = new Intent(this, GroupPageActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);

    }
}