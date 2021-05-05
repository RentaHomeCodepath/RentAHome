package com.example.rentahome.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.rentahome.Post;
import com.example.rentahome.R;
import com.example.rentahome.databinding.FragmentComposeBinding;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static com.parse.Parse.getApplicationContext;

public class ComposeFragment extends Fragment {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 43;
    public static final int PICK_PHOTO_CODE = 21;

    private File photoFile;
    FragmentComposeBinding fragmentComposeBinding;
    private String photoFileName = "photo.jpg";
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
                uploadImage();
            }
        });
        fragmentComposeBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = fragmentComposeBinding.etAddress.getText().toString();
                String price = fragmentComposeBinding.etPrice.getText().toString();
                String description = fragmentComposeBinding.etDescription.getText().toString();
                if(address.isEmpty()){
                    Toast.makeText(getContext(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(price.isEmpty()){
                    Toast.makeText(getContext(), "Price cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(description.isEmpty()){
                    Toast.makeText(getContext(), "Description cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                ParseUser currentUser = ParseUser.getCurrentUser();
                if(photoFile == null || fragmentComposeBinding.ivPostImage.getDrawable()==null){
                    Toast.makeText(getContext(),"There is no image", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(address, price, description, currentUser, photoFile);
            }
        });
    }

    private void uploadImage() {
        // create Intent to upload a picture and return control to the calling application
        // Edit action for MediaStore
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        //Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.example.rentahome.fileprovider", photoFile);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image pick intent to pick photo from external folder
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }
    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Anrdoid on device
            if(Build.VERSION.SDK_INT > 27) {
                // newer version
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContext().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                //support older
                image = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(),photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory..");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                Uri photoUri = data.getData();
                // by this point we have the camera photo on disk
                //Bitmap selectedImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                //Load the image located at photoUri into selectedImage
                Bitmap selectedImage = loadFromUri(photoUri);
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                fragmentComposeBinding.ivPostImage.setImageBitmap(selectedImage);

            } else { // Result was a failure
                Toast.makeText(getContext(), "Image not found", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void savePost(String address, String price, String description, ParseUser currentUser, File photoFile) {
        Post post = new Post();
        post.setAddress(address);
        //parse String price to int..
        int parsed_price = Integer.parseInt(price);
        post.setPrice(parsed_price);
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if(e!=null){
                    Log.e(TAG,"Issue with saving posts..", e);
                    Toast.makeText(getContext(), "Error while saving", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i(TAG,"Saved successfully!");
                fragmentComposeBinding.etDescription.setText("");
                fragmentComposeBinding.ivPostImage.setImageResource(0);
            }
        });
    }
}
