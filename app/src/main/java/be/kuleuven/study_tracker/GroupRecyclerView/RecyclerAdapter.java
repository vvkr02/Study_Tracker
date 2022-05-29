package be.kuleuven.study_tracker.GroupRecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import CoreClasses.BasicDetails;
import CoreClasses.DataBaseHandler;
import CoreClasses.ImageProcessor;
import CoreClasses.User;
import Interfaces.VolleyCallBack;
import be.kuleuven.study_tracker.Challenge.AnswerActivity;
import be.kuleuven.study_tracker.Challenge.ChallengeActivity;
import be.kuleuven.study_tracker.Challenge.GradeActivity;
import be.kuleuven.study_tracker.ChallengeOperations.ChallengeDatabaseHandler;
import be.kuleuven.study_tracker.GroupOperations.GroupMemberProfileViewActivity;
import be.kuleuven.study_tracker.GroupOperations.JoinGroup;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.ProfileViewActivity;
import be.kuleuven.study_tracker.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Map<Integer,Integer> groupMembers;
    User user;
    DataBaseHandler dataBaseHandler;
    ImageProcessor imageProcessor = new ImageProcessor();
    Context context;
    int second;
    int first;
    ChallengeDatabaseHandler cHandler;

    public RecyclerAdapter(Map<Integer,Integer> groupMembers,int first,User user) {
        this.groupMembers = groupMembers;
        this.first = first;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        dataBaseHandler = new DataBaseHandler(parent.getContext());
        cHandler = new ChallengeDatabaseHandler(parent.getContext());  

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        dataBaseHandler.getBasicUserDetails(groupMembers.get(position),
                new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        BasicDetails basicDetails = dataBaseHandler.basicDetails;
                        holder.name.setText(basicDetails.getName());
                        holder.score.setText(""+basicDetails.getScore());
                        Bitmap bitmap= imageProcessor.process(basicDetails.getProfilePic());
                        holder.imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFail() {

                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return groupMembers.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View view,int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView name,score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img_user);
            name = itemView.findViewById(R.id.txt_name);
            score = itemView.findViewById(R.id.txt_score);


            itemView.setOnClickListener(this);

        }




        @Override
        public void onClick(View view) {
            showDialog(getAdapterPosition());

        }
    }

    private void showDialog(int i) {

        Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_ThemeOverlay_AppCompat_Dialog);
        dialog.setContentView(R.layout.popupbox);

        Button profile = dialog.findViewById(R.id.btn_prof);
        Button challenge = dialog.findViewById(R.id.btn_challenge);
        Button answer = dialog.findViewById(R.id.btn_answer);
        Button grade = dialog.findViewById(R.id.btn_grade);
        Button close = dialog.findViewById(R.id.btn_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHandler.getBasicUserDetails(groupMembers.get(i),
                        new VolleyCallBack() {
                            @Override
                            public void onSuccess() {
                                BasicDetails basicDetails = dataBaseHandler.basicDetails;
                                Intent intent = new Intent(context, GroupMemberProfileViewActivity.class);
                                intent.putExtra("User", user);
                                intent.putExtra("BasicDetails",basicDetails);
                                context.startActivity(intent);
                            }

                            @Override
                            public void onFail() {

                            }
                        }
                );
            }
        });

        challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                cHandler.checkIfQuestionExists(first, groupMembers.get(i), new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "The last Question you sent to this person is Pending", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFail() {
                        challenge(i);

                    }
                });

                

            }
        });
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first == groupMembers.get(i))
                {
                    Toast.makeText(context, "Can't Challenge/Answer/Grade Yourself", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(context, AnswerActivity.class);
                    intent.putExtra("Current",first);
                    intent.putExtra("Target",groupMembers.get(i));
                    context.startActivity(intent);
                }


            }
        });

        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first == groupMembers.get(i))
                {
                    Toast.makeText(context, "Can't Challenge/Answer/Grade Yourself", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(context, GradeActivity.class);
                    intent.putExtra("Current",first);
                    intent.putExtra("Target",groupMembers.get(i));
                    context.startActivity(intent);
                }


            }
        });





        dialog.show();
    }

    private void challenge(int i) {

        if(first == groupMembers.get(i))
        {
            Toast.makeText(context, "Can't Challenge/Answer/Grade Yourself", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(context, ChallengeActivity.class);
            intent.putExtra("Current",first);
            intent.putExtra("Target",groupMembers.get(i));
            intent.putExtra("User", user);
            context.startActivity(intent);

        }
    }
}
