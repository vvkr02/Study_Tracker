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

public class JoinGroup extends AppCompatActivity {
    private User user;
    DataBaseHandler databasehandler = new DataBaseHandler(this);
    GroupDataBaseHandler groupDataBaseHandler = new GroupDataBaseHandler(this);
    private EditText groupname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        user = getIntent().getParcelableExtra("User");
        groupname = findViewById(R.id.edit_gname);
    }

    public void onBtnJoin_Clicked(View caller)
    {
        groupDataBaseHandler.checkIfGroupExists(groupname.getText().toString(),
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        joingroup();
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(JoinGroup.this, "Group Doesn't Exists", Toast.LENGTH_SHORT).show();
                    }
                }
        );


    }

    private void joingroup() {

        databasehandler.joinTeam(groupname.getText().toString(),
                user, new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(JoinGroup.this, GroupPageActivity.class);
                        intent.putExtra("User", user);
                        Toast.makeText(JoinGroup.this, "Joined Group", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }

                    @Override
                    public void onFail() {
                        Toast.makeText(JoinGroup.this, "Unable to join Group", Toast.LENGTH_SHORT).show();
                    }
                }
        );

    }
}