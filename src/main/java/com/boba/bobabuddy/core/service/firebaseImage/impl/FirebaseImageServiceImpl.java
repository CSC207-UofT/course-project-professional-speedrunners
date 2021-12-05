package com.boba.bobabuddy.core.service.firebaseImage.impl;

import com.boba.bobabuddy.core.service.firebaseImage.IImageService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class FirebaseImageServiceImpl implements IImageService {
    private final String imageUrl = "https://firebasestorage.googleapis.com/v0/b/boba-buddy-904b9.appspot.com/o/%s?alt=media";

    @EventListener
    public void init(ApplicationReadyEvent event) throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase_config.json");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("boba-buddy-904b9.appspot.com")
                .build();

        FirebaseApp.initializeApp(options);
    }

    public String save(String imageUrl, String originalImageName) throws IOException {
        BufferedImage bufferedImage = convertUrlToImage(imageUrl);

        byte[] bytes = getByteArrays(bufferedImage, getExtension(originalImageName));
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = generateFileName(originalImageName);
        bucket.create(name, bytes);
        return name;
    }

    public String getImageUrl(String originalImageName){
        return String.format(imageUrl, originalImageName);
    }
}