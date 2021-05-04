package com.example.rentahome;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.List;


public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;
    private OnItemClickListener Listener;

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener Listener){
        this.Listener = Listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvPrice;
        private RatingBar vratingBar;
        final double[] total = {0};

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            vratingBar = itemView.findViewById(R.id.ratingBar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            Listener.onItemClick(position);
                        }
                    }
                }
            });
            Resources res = itemView.getResources();
        }

        public void bind(Post post) {
            // Bind the post data to the view wlwments

            String postID = post.getobjectID();
            ParseQuery<ParseObject> query = post.getrelation().getQuery();
            //query.whereEqualTo("post_id",postID);
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    for(ParseObject i : objects){
                        total[0] = total[0] + i.getDouble("rating");
                    }
                    //Toast.makeText(context, total[0].getClass(), Toast.LENGTH_LONG).show();
                    //Toast.makeText(context, String.valueOf(total[0]), Toast.LENGTH_LONG).show();
                    vratingBar.setRating((float) total[0]);
                }
            });


            //vratingBar.setNumStars(5);

            tvDescription.setText((post.getDescription()));
            tvUsername.setText(post.getUser().getUsername());
            tvPrice.setText(String.format(String.valueOf(post.getPrice())));



            //Toast.makeText(context, String.valueOf(total), Toast.LENGTH_LONG).show();

            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }

        }


    }
}
