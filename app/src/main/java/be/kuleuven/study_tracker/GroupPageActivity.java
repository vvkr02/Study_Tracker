package be.kuleuven.study_tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import CoreClasses.BasicDetails;
import CoreClasses.DataBaseHandler;
import CoreClasses.User;
import Interfaces.VolleyCallBack;
import be.kuleuven.study_tracker.GroupOperations.CreateGroup;
import be.kuleuven.study_tracker.GroupOperations.GroupDataBaseHandler;
import be.kuleuven.study_tracker.GroupOperations.GroupMemberProfileViewActivity;
import be.kuleuven.study_tracker.GroupOperations.JoinGroup;
import be.kuleuven.study_tracker.GroupRecyclerView.RecyclerAdapter;

public class GroupPageActivity extends AppCompatActivity {
    private User user;
    GroupDataBaseHandler groupDataBaseHandler = new GroupDataBaseHandler(this);
    DataBaseHandler databasehandler = new DataBaseHandler(this);
    private TextView groupname;
    private boolean isinGroup = false;
    private boolean isAdmin = false;
    private Map<Integer,Integer> groupMembers= new TreeMap<Integer,Integer>();
    private String group;
    private RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    private RecyclerAdapter.RecyclerViewClickListener listener;

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
                        isinGroup = true;
                        setGroup(groupDataBaseHandler.getGroupname());
                        groupname.setText(getGroup());
                        isAdmin = groupDataBaseHandler.getisAdmin();

                        getGroupMembersMap();
                    }

                    @Override
                    public void onFail() {
                        isinGroup = false;
                        groupname.setText("Not in any Group");
                    }
                }
        );


    }

    private void setRecyclerviewData() {

        setOnClickListener();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(groupMembers,listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setOnClickListener() {
        listener = new RecyclerAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), GroupMemberProfileViewActivity.class);
                databasehandler.getBasicUserDetails(groupMembers.get(position),
                        new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                BasicDetails basicDetails = databasehandler.basicDetails;
                                intent.putExtra("User", user);
                               intent.putExtra("BasicDetails",basicDetails);
                               startActivity(intent);
                            }

                            @Override
                            public void onFail() {

                            }
                        }
                );

            }
        };
    }

    private void getGroupMembersMap() {

        groupDataBaseHandler.getGroupMembers(
                getGroup(), new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        groupMembers=groupDataBaseHandler.getGroupMap();
                        setRecyclerviewData();
                    }

                    @Override
                    public void onFail() {

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

    public void onJoin_Clicked(View caller)
    {
        Toast.makeText(this,""+isAdmin, Toast.LENGTH_SHORT).show();
        if(isinGroup == false)
        {
            Intent intent = new Intent(this, JoinGroup.class);
            intent.putExtra("User", user);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Already part of a group", Toast.LENGTH_SHORT).show();
        }

    }

    public void onDelete_Clicked(View caller)
    {

        if(isinGroup == true && isAdmin == true)
        {
            databasehandler.deleteTeam(getGroup(),new VolleyCallBack() {
                        @Override
                        public void onSuccess() {
                            Intent intent = new Intent(GroupPageActivity.this, GroupPageActivity.class);
                            intent.putExtra("User", user);
                            Toast.makeText(GroupPageActivity.this, "Group Deleted", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

                        @Override
                        public void onFail() {
                            Toast.makeText(GroupPageActivity.this, "Unable to delete Group", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
        else if (isinGroup == true && isAdmin == false)
        {
            Toast.makeText(this, "You're not the admin of the Group", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Not in any Group", Toast.LENGTH_SHORT).show();
        }

    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }


}