package be.kuleuven.study_tracker.GroupOperations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import CoreClasses.DataBaseHandler;
import CoreClasses.User;
import Interfaces.VolleyCallBack;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.R;

public class CreateGroup extends AppCompatActivity {
    private User user;
    DataBaseHandler databasehandler = new DataBaseHandler(CreateGroup.this);
    GroupDataBaseHandler groupDataBaseHandler = new GroupDataBaseHandler(this);
    private EditText groupname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        user = getIntent().getParcelableExtra("User");
        groupname = findViewById(R.id.groupname);
    }

    public void onBtnCeateok_Clicked(View caller)
    {

        databasehandler.createTeam(groupname.getText().toString(),
                user, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        User user = databasehandler.user;
                        Intent intent = new Intent(CreateGroup.this, GroupPageActivity.class);
                        intent.putExtra("User", user);
                        Toast.makeText(CreateGroup.this, "Group Created", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(CreateGroup.this, "Unable to create Group", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}