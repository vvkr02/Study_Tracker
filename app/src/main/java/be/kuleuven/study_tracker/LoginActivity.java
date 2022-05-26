package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import CoreClasses.DataBaseHandler;
import CoreClasses.User;
import Interfaces.VolleyCallBack;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button btnLogin;
    DataBaseHandler databasehandler = new DataBaseHandler(LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        username.setText("madlad");
        password.setText("123");

    }

    public void onBtnLogin_Clicked(View caller)
    {
        User user = new User(username.getText().toString(),password.getText().toString());
        databasehandler.getUserFromLogin(
                user, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        User user = databasehandler.user;
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("User", user);
                        startActivity(intent);
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }

    public void onBtnRegister_Clicked(View caller)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}