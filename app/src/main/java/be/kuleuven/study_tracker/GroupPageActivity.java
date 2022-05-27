package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import CoreClasses.User;
import Interfaces.VolleyCallBack;
import be.kuleuven.study_tracker.GroupOperations.CreateGroup;
import be.kuleuven.study_tracker.GroupOperations.GroupDataBaseHandler;

public class GroupPageActivity extends AppCompatActivity {
    private User user;
    GroupDataBaseHandler groupDataBaseHandler = new GroupDataBaseHandler(this);
    private TextView groupname;
    private boolean isinGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_page);
        groupname = findViewById(R.id.txt_group_name);
        user = getIntent().getParcelableExtra("User");


        groupDataBaseHandler.checkIfPresent(
                user.getIdUser(), new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        groupname.setText(groupDataBaseHandler.getGroupname());
                        isinGroup = true;
                    }

                    @Override
                    public void onFail() {
                        isinGroup = false;
                    }
                }
        );

    }

    public void onBack_Clicked(View caller)
    {
        Intent intent = new Intent(this,HomeActivity.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }

    public void onCreate_Clicked(View caller)
    {
        if(isinGroup == false)
        {
            Intent intent = new Intent(this, CreateGroup.class);
            intent.putExtra("User", user);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Already part of a group", Toast.LENGTH_SHORT).show();
        }

    }
}