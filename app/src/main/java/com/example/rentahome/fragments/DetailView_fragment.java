package com.example.rentahome.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rentahome.Post;
import com.example.rentahome.PostsAdapter;
import com.example.rentahome.R;
import com.example.rentahome.ReviewAdapter;
import com.example.rentahome.Reviews;

import com.example.rentahome.StartingActivity;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class DetailView_fragment extends Fragment {
    //private Button btnsignout;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //private SharedViewModel viewModel;

    private RecyclerView rvReviews; //recyclerView for Home fragment.
    private ReviewAdapter adapter;
    protected Post loadpost;
    protected List<Reviews> allreviews = new ArrayList<>();
    private TextView address;
    private  TextView price;
    private TextView description;
    private ImageView tvimage;
    private Button mkreview;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public void updatePost(Post post){
        loadpost = post;
    }
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailView_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailView_fragment newInstance(String param1, String param2) {
        DetailView_fragment fragment = new DetailView_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.detail_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvReviews= view.findViewById(R.id.recyclerView_reviews);
        tvimage = view.findViewById(R.id.tvimage_dv);
        description = view.findViewById(R.id.description_dv);
        address = view.findViewById(R.id.tvAddress_dv);
        price = view.findViewById(R.id.tvPrice_dv);
        mkreview = view.findViewById(R.id.add_review_btn);

        String postID = loadpost.getobjectID();
        ParseQuery<ParseObject> query = loadpost.getrelation().getQuery();
        //query.whereEqualTo("post_id",postID);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                for(ParseObject i : objects){
                    Reviews temp = new Reviews();
                    temp.setDescription(i.getString("Description"));
                    temp.setdislikesCount(i.getInt("dislikesCount"));
                    temp.setlikesCount(i.getInt("likesCount"));
                    temp.setRating((float)i.getDouble("rating"));
                    allreviews.add(temp);
                }
                adapter = new ReviewAdapter(getContext(), allreviews);
                rvReviews.setAdapter(adapter);
                rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });


        description.setText(loadpost.getString("description"));
        address.setText(loadpost.getString("address"));
        price.setText(String.format(String.valueOf(loadpost.getInt("price"))));

        ParseFile image = loadpost.getImage();
        if (image != null) {
            Glide.with(getContext()).load(loadpost.getImage().getUrl()).into(tvimage);
        }

        mkreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewFragment nextFrag= new ReviewFragment();

                nextFrag.updatePost(loadpost);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContainer, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

}