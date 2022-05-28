package be.kuleuven.study_tracker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import CoreClasses.DataBaseHandler;
import CoreClasses.ImageProcessor;
import CoreClasses.User;

public class RegisterActivity extends AppCompatActivity {
    DataBaseHandler dataBaseHandler = new DataBaseHandler(RegisterActivity.this);
    private EditText name,username,password;
    private ImageView image;
    private int PICK_IMAGE_REQUEST = 111;
    private Bitmap bitmap;
    ImageProcessor imageProcessor = new ImageProcessor();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        image = findViewById(R.id.imageView2);
    }

    public void onBtnSubmit_Clicked(View view) {

        User user = new User(username.getText().toString(),password.getText().toString(),name.getText().toString(),imageProcessor.toBase64(bitmap));
        dataBaseHandler.registerUser(user);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
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
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}