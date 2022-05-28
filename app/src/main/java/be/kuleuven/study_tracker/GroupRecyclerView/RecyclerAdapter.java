package be.kuleuven.study_tracker.GroupRecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import be.kuleuven.study_tracker.GroupOperations.JoinGroup;
import be.kuleuven.study_tracker.GroupPageActivity;
import be.kuleuven.study_tracker.ProfileViewActivity;
import be.kuleuven.study_tracker.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Map<Integer,Integer> groupMembers;
    DataBaseHandler dataBaseHandler;
    ImageProcessor imageProcessor = new ImageProcessor();
    private RecyclerViewClickListener listener;

    public RecyclerAdapter(Map<Integer,Integer> groupMembers,RecyclerViewClickListener listener) {
        this.groupMembers = groupMembers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        dataBaseHandler = new DataBaseHandler(parent.getContext());

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

            listener.onClick(view,getAdapterPosition());

        }
    }
}
