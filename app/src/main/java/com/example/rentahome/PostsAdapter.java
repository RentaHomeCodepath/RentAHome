package com.example.rentahome;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

    public PostsAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;

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

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvPrice;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            Resources res = itemView.getResources();
        }

        public void bind(Post post) {
            // Bind the post data to the view wlwments
            ParseQuery<ParseObject> query = post.getrelation().getQuery();
            query.orderByDescending("createdAt");
            query.setLimit(10);
            query.include("Post");

            query.findInBackground((objects, e) -> {
                for(ParseObject i: objects){
                    ParseObject review = i.getParseObject("reviews");
                    double hi = review.getDouble("rating");
                    Log.d("Reviews", "retrieved a related post");
                    Log.i("Rating",String.valueOf(hi));
                }
            });

            tvDescription.setText((post.getDescription()));
            tvUsername.setText(post.getUser().getUsername());
            tvPrice.setText(String.format(String.valueOf(post.getPrice())));
            //ratingBar.setRating((float));
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }

        }
    }
}
