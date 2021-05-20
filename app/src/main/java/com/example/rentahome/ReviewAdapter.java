package com.example.rentahome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context context;
    private List<Reviews> reviews;

    public ReviewAdapter(Context context, List<Reviews>reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_detailview, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        Reviews review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView description;
        private ImageButton like;
        private ImageButton dislike;
        private TextView like_count;
        private TextView dislike_count;
        private RatingBar ratingBar_r;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description_rv);
            like = itemView.findViewById(R.id.like_btn_dv);
            dislike = itemView.findViewById(R.id.dislike_btn_dv);
            ratingBar_r = itemView.findViewById(R.id.ratingBar_rv);
            like_count= itemView.findViewById(R.id.Like_count_dv);
            dislike_count = itemView.findViewById(R.id.dislike_count_dv);
        }
        public void bind(Reviews reviews) {
            description.setText(reviews.getDescription());
            like_count.setText(String.format(String.valueOf(reviews.getlikesCount())));
            dislike_count.setText(String.format(String.valueOf(reviews.getdislikesCount())));
            ratingBar_r.setRating((float)reviews.getRating());

        }
    }
}
