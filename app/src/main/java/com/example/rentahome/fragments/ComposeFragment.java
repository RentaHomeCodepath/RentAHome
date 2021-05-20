package com.example.rentahome.fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.rentahome.ImageFilePath;
import com.example.rentahome.Post;
import com.example.rentahome.R;
import com.example.rentahome.Reviews;
import com.example.rentahome.databinding.FragmentComposeBinding;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.parse.Parse.getApplicationContext;

public class ComposeFragment extends Fragment {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 43;
    public static final int PICK_PHOTO_CODE = 21;

    public File photoFile;
    FragmentComposeBinding fragmentComposeBinding;
    public static final String TAG = "ComposeFragment";

    public ComposeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentComposeBinding = FragmentComposeBinding.bind(view);
        fragmentComposeBinding.btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        fragmentComposeBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = fragmentComposeBinding.etAddress.getText().toString();
                String price = fragmentComposeBinding.etPrice.getText().toString();
                String description = fragmentComposeBinding.etDescription.getText().toString();
                if (address.isEmpty()) {
                    Toast.makeText(getContext(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (price.isEmpty()) {
                    Toast.makeText(getContext(), "Price cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (description.isEmpty()) {
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || fragmentComposeBinding.ivPostImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "There is no image", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(address, price, description, currentUser, photoFile);
            }
        });
    }

    private void imagePicker() {
        Intent intent = new Intent(getContext(), FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowVideos(false)
                .enableImageCapture(true)
                .setMaxSelection(1)
                .setSkipZeroSizeFiles(true)
                .build());
        startActivityForResult(intent, 101);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            if (requestCode == 1) {
                imagePicker();
            }
        } else {
            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode == RESULT_OK && data != null){

            ArrayList<MediaFile> mediaFiles  = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            String path = mediaFiles.get(0).getPath();

            switch(requestCode){
                case 101:
                    String s = "Image Path : "+ path;
                    photoFile = new File(path);
                    //Toast.makeText(getContext(),s, Toast.LENGTH_SHORT).show();
                    break;
            }
            Uri imgUri = Uri.parse(path);
            fragmentComposeBinding.ivPostImage.setImageURI(imgUri);

        }

    }


    private void savePost(String address, String price, String description, ParseUser currentUser,File file) {
        //Post post = new Post();


        ParseObject post = ParseObject.create("Post");
//        File file = new File(realPath);
        ParseFile photo = new ParseFile(file);
        photo.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                // Handle success or failure here ...
                post.put("image",photo);
                post.put("description",description);
                post.put("address",address);
                post.put("user",currentUser);
                post.put("price", Integer.parseInt(price) );

                post.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        // Handle success or failure here ...
                        if(e!=null){
                            Log.e(TAG,"Issue with saving posts..", e);
                            Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.i(TAG,"Saved successfully!");
                        fragmentComposeBinding.etDescription.setText("");
                        fragmentComposeBinding.etAddress.setText("");
                        fragmentComposeBinding.etPrice.setText("");
                        fragmentComposeBinding.ivPostImage.setImageResource(0);
                    }
                });
            }
        });



    }
}
