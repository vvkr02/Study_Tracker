package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import CoreClasses.ImageProcessor;
import CoreClasses.User;

public class ProfileViewActivity extends AppCompatActivity {
    private TextView name,username,score;
    private ImageView profilepic;
    private User user;
    ImageProcessor processor = new ImageProcessor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        user = getIntent().getParcelableExtra("User");
        name = findViewById(R.id.txt_profilename);
        username = findViewById(R.id.txt_profile_username);
        score = findViewById(R.id.txt_profile_score);
        profilepic = findViewById(R.id.img_profile_pic_view);

        name.setText(user.getName());
        username.setText(user.getUsername());
        score.setText(""+user.getScore());
        profilepic.setImageBitmap(processor.process(user.getProfilePic()));
    }

    public void onBtnBack_Clicked(View caller)
    {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}