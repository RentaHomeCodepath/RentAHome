package com.example.rentahome.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentahome.Post;
import com.example.rentahome.PostsAdapter;
import com.example.rentahome.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.security.AlgorithmConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Homefragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Homefragment extends Fragment {

    public static final String TAG = "Homefragment";
    private RecyclerView rvPosts; //recyclerView for Home fragment.
    private PostsAdapter adapter;
    protected List<Post> allPosts;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Homefragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Homefragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Homefragment newInstance(String param1, String param2) {
        Homefragment fragment = new Homefragment();
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
    // This event is triggered soon after onCreateView().
    // Any view setup should occur here. E.g., view Lookups and attaching view listeners.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homefragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        rvPosts = getView().findViewById(R.id.rvPosts);

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);

        //1. Create layout for row in List

        //2. Create adapter

        //3. Link back to Back4APP
        rvPosts.setAdapter(adapter);

        //4. Set adapter on recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        //5. Set layout manager on recycler view
        queryPosts();

    }
    protected void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e!=null) {


                    Log.e(TAG, "Issue with getting post", e);
                    return;
                }
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription());

                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();



            }
        });


    }



}