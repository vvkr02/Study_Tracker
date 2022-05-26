package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import CoreClasses.DataBaseHandler;
import CoreClasses.ImageHandler;
import CoreClasses.User;
import Interfaces.VolleyCallBack;

public class HomeActivity extends AppCompatActivity {
    private final DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    private ImageHandler imageHandler = new ImageHandler(this);
    private User user;
    private TextView nameTxt,scoreTxt;
    private ImageView profilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        user = getIntent().getParcelableExtra("User");
        nameTxt = findViewById(R.id.txt_hello);
        scoreTxt = findViewById(R.id.txt_scoreval);
        profilePic = findViewById(R.id.img_profilepic);
        nameTxt.setText("Hello "+user.getName()+" !");
        scoreTxt.setText(user.getScoreString());


    }
}