package com.example.myapplication.cameraClassesAndFragments;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.util.IOUtils;
import com.example.myapplication.IOTPublishAndSubscribe.MQTTHandler;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Camera1Fragment extends Fragment {

    private ViewPager2 viewPager2;
    private Bitmap bitmap;

    // Creates the variables to enable navigation and picture display
    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageView fridgeSnapshot;
    private final String KEY = "AKIAZR45JFDBI6WHY6AC";
    private final String SECRET = "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";

    private AmazonS3 amazonS3Client;
    private BasicAWSCredentials basicAWSCredentials;

    private Uri fileUri = new Intent().getData();
    private EditText editText;
    //track Choosing Image Intent
    private static final int CHOOSING_IMAGE_REQUEST = 1234;

    private MQTTHandler mqttHandler;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        try {
            mqttHandler = MQTTHandler.getInstance(getContext());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return inflater.inflate(R.layout.fragment_first11, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fridgeSnapshot = getView().findViewById(R.id.fridgeSnapshot);

        backButton = getView().findViewById(R.id.cameraBackButton);
        backButton.setOnClickListener(v->{
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.cameraPlaceholder, new Camera4Fragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();

            getActivity().getFragmentManager().popBackStack();
        });


        forwardButton = getView().findViewById(R.id.cameraForwardButton);
        forwardButton.setOnClickListener(v -> {
            // Begin the transaction
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.cameraPlaceholder, new Camera2Fragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });


        //cognitoCredentialsProvider.getCr
        //fridgeSnapshot = getView().findViewById(R.id.fridgeSnapshot);

        AWSMobileClient.getInstance().initialize(getContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                basicAWSCredentials = new BasicAWSCredentials(KEY, SECRET);
                amazonS3Client = new AmazonS3Client(basicAWSCredentials);
                //URL url = new URL("https://assets.website-files.com/589215a57e34325c3af796b3/5ab96e94cd72e62700584a06_rmr-blog-bird-oasis.jpg");
                //fridgeSnapshot.setImageBitmap(BitmapFactory.decodeStream((InputStream) new java.net.URL(url.toString()).openStream()));
                //showChoosingFile();
                mqttHandler.take_picture();
                downloadFile();

            }

            @Override
            public void onError(Exception e) {

            }
        });


        /*
        List<CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(new CarouselItem(getBitmap()));
        carouselItems.add(new CarouselItem(R.drawable.fg_dr));
        carouselItems.add(new CarouselItem(R.drawable.fz));
        carouselItems.add(new CarouselItem(R.drawable.fz_dr));

        viewPager2.setAdapter(new CarouselView(carouselItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(4);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);

            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);*/
        //fridgeSnapshot.setImageResource(R.drawable.orange_juice_glass);
        //downloadFile();

    }

    private void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    private Bitmap getBitmap(){
        System.out.println("Entered get bit map");
        return bitmap;
    }

    private void showChoosingFile() {
        Intent intent = new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSING_IMAGE_REQUEST);
    }

    private void downloadFile() {
        System.err.println("Entered download file function");
        System.err.println("Entered fileUri");
        final String filename = "fridge.jpg";

            //if (!validateInputFileName(filename)){
            //    System.err.println("Invalidated");
             //   return;
           // }

            try {
                System.err.println("Entered try");
                final File localFile = File.createTempFile("images", ".jpg", getContext().getCacheDir());
                System.out.println("Directory path is: " + localFile.getAbsolutePath());
                System.err.println("Created local file");
                //TransferUtility transferUtility2 = new TransferUtility()
                TransferUtility transferUtility = TransferUtility.builder().s3Client(amazonS3Client).context(getContext()).build();
                System.err.println("Observing download transfer utility: " + transferUtility.toString());
                TransferObserver downloadObserver = transferUtility.download("smart-fridge-project", "images/" + filename, localFile);
                System.out.println("The bucket name is: " + downloadObserver.getBucket());
                System.err.println("Observing download observer: ");
                downloadObserver.setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        //editText.setText(filename + "." + getFileExtension(fileUri));
                        Bitmap bmp = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        fridgeSnapshot.setImageBitmap(bmp);
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                        int percentDone = (int) percentDonef;

                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (bitmap != null) {
            bitmap.recycle();
        }

        if (requestCode == CHOOSING_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInputFileName(String fileName) {

        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(getContext(), "Enter file name!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void createFile(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            IOUtils.copy(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}