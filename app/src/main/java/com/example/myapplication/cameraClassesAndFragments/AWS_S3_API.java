package com.example.myapplication.cameraClassesAndFragments;

import android.content.ContentResolver;
import android.content.Context;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;


public class AWS_S3_API  {

    private String KEY;
    private String SECRET;
    private Context context;
    private TransferUtility transferUtility;
    private Uri fileUri;
    private Bitmap bitmap;
    private BasicAWSCredentials basicAWSCredentials;
    private AmazonS3Client amazonS3Client;

    // The constructor the AWS_S3_API
    public AWS_S3_API() {
        basicAWSCredentials = new BasicAWSCredentials(KEY, SECRET);
        amazonS3Client = new AmazonS3Client(basicAWSCredentials);
        transferUtility = TransferUtility.builder().context(context).awsConfiguration(AWSMobileClient.getInstance().getConfiguration()).s3Client(amazonS3Client).build();
    }

    public AmazonS3Client getAmazonS3Client() {
        return amazonS3Client;
    }

    public TransferUtility getTransferUtility() {
        return transferUtility;
    }

    private boolean validateInputFileName(String fileName) {

        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(context, "Enter file name!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void download(String bucket, String key, File file){
        if (fileUri != null){
            final String fileName = "";

            if (!validateInputFileName(fileName)){
                return ;
            }

            try {
                final File systemFile = File.createTempFile("images", getFileExtension(fileUri));
                TransferUtility transferUtility = TransferUtility.builder().context(context).awsConfiguration(AWSMobileClient.getInstance().getConfiguration()).s3Client(getAmazonS3Client()).build();
                TransferObserver transferObserver = getTransferUtility().download("jsaS3/" + fileName + "." + getFileExtension(fileUri), systemFile);
                transferObserver.setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        if (TransferState.COMPLETED == state){
                            Toast.makeText(context, "Download complete!", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        float percentDonef = ((float) bytesCurrent/(float) bytesTotal);
                        int percentDone = (int) percentDonef;
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        ex.printStackTrace();                               // Prints the error message
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

