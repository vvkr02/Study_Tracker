package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import CoreClasses.DataBaseHandler;
import CoreClasses.ImageProcessor;
import CoreClasses.User;

public class HomeActivity extends AppCompatActivity {
    private final DataBaseHandler dataBaseHandler = new DataBaseHandler(this);
    private ImageProcessor imageHandler = new ImageProcessor();
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
        profilePic.setImageBitmap(imageHandler.process(user.getProfilePic()));


    }
}