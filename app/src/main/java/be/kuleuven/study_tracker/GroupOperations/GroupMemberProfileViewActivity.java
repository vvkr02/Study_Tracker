package be.kuleuven.study_tracker.GroupOperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import CoreClasses.BasicDetails;
import CoreClasses.ImageProcessor;
import CoreClasses.User;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.HomeActivity;
import be.kuleuven.study_tracker.R;

public class GroupMemberProfileViewActivity extends AppCompatActivity {
    private TextView name,username,score;
    private ImageView profilepic;
    private BasicDetails basicDetails;
    ImageProcessor processor = new ImageProcessor();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member_profile_view);
        user = getIntent().getParcelableExtra("User");
        basicDetails = getIntent().getParcelableExtra("BasicDetails");
        name = findViewById(R.id.name_gp);
        score = findViewById(R.id.score_gp);
        profilepic = findViewById(R.id.img_gp);

        name.setText(basicDetails.getName());
        score.setText(""+basicDetails.getScore());
        profilepic.setImageBitmap(processor.process(basicDetails.getProfilePic()));
    }

    public void onBtnBackgp_Clicked(View caller)
    {
        Intent intent = new Intent(this, GroupPageActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}