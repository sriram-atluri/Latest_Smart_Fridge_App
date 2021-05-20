package com.example.myapplication.cameraClassesAndFragments;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.IOUtils;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Camera2Fragment extends Fragment {

    private ImageButton backButton;
    private ImageButton forwardButton;
    private ImageView fridgeDoorShot;
    private final String KEY = "AKIAZR45JFDBI6WHY6AC";
    private final String SECRET = "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";

    private AmazonS3 amazonS3Client;
    private BasicAWSCredentials credentials;

    private Uri fileUri;
    private Bitmap bitmap;
    private EditText editText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera2, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        backButton = getView().findViewById(R.id.cameraBackButton);
        backButton.setOnClickListener(v -> {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
            ft.replace(R.id.cameraPlaceholder, new Camera1Fragment());
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
            ft.replace(R.id.cameraPlaceholder, new Camera3Fragment());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
            ft.commit();
        });

        fridgeDoorShot = getView().findViewById(R.id.fridgeDoorSnapshot);
        fridgeDoorShot.setImageResource(R.drawable.fg_dr);
    }
        /*
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(First4Fragment.this)
                        .navigate(R.id.action_First4Fragment_to_Second4Fragment);
            }
        });

        AWSMobileClient.getInstance().initialize(getContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                credentials = new BasicAWSCredentials(KEY, SECRET);
                amazonS3Client = new AmazonS3Client(credentials);
                //URL url = new URL("https://assets.website-files.com/589215a57e34325c3af796b3/5ab96e94cd72e62700584a06_rmr-blog-bird-oasis.jpg");
                //fridgeSnapshot.setImageBitmap(BitmapFactory.decodeStream((InputStream) new java.net.URL(url.toString()).openStream()));
                //showChoosingFile();
                //downloadFile();

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    private void downloadFile(){
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
                    fridgeDoorShot.setImageBitmap(bmp);
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
    }*/
}
