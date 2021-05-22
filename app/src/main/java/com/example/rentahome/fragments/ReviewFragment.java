package com.example.rentahome.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.rentahome.Post;
import com.example.rentahome.R;
import com.example.rentahome.ReviewAdapter;
import com.example.rentahome.Reviews;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public Post loadpost;
    public EditText description;
    public RatingBar ratingBar;
    public Button submit;

    public void updatePost(Post post){
        loadpost = post;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewFragment newInstance(String param1, String param2) {
        ReviewFragment fragment = new ReviewFragment();
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
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);




        description = view.findViewById(R.id.submitReview_description);
        ratingBar = view.findViewById(R.id.submitReview_ratingbar);
        submit = view.findViewById(R.id.submitReview_submitbtn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseRelation<ParseObject> relation = loadpost.getRelation("Reviews");

                if(ratingBar.getNumStars() == 0){
                    Toast.makeText(getContext(), "Rate the house!",Toast.LENGTH_SHORT).show();
                }else if(description.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"Describe the house!", Toast.LENGTH_SHORT).show();
                }else{
//                    ParseObject temp = ParseObject.create("Reviews");
//                    temp.put("Description",description.getText().toString());
//                    temp.put("author", ParseUser.getCurrentUser());
//                    temp.put("likesCount",0);
//                    temp.put("dislikesCount",0);
//                    temp.put("rating", (float)ratingBar.getNumStars());
//
//
//                    temp.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//
//                        }
//                    });

                    Reviews temp2 = new Reviews();
                    temp2.setAuthor(ParseUser.getCurrentUser());
                    temp2.setDescription(description.getText().toString());
                    temp2.setRating((float)ratingBar.getRating());
                    temp2.setdislikesCount(0);
                    temp2.setlikesCount(0);
                    temp2.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e!=null){
                                Log.e("ReviewFragment","Issue with saving posts..", e);
                                Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            relation.add(temp2);
                            loadpost.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Log.e("ReviewFragment","Issue with saving posts..", e);
                                        Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                }
                            });

                        }
                    });





                    Homefragment nextFrag= new Homefragment();

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, nextFrag)
                            .addToBackStack(null)
                            .commit();
                }

            }
        });

    }
}