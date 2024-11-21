package com.cg.casestudy.services;

import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseService {

    private final StorageClient storageClient;

    @Autowired
    public FirebaseService(StorageClient storageClient) {
        this.storageClient = storageClient;
    }

    public String uploadImageToFireBase(MultipartFile file) {
        String fileName = "casestudym4/"+ UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            var blob = storageClient.bucket().create(fileName, file.getInputStream(), file.getContentType());
            blob.createAcl(com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(), com.google.cloud.storage.Acl.Role.READER));
            return blob.getMediaLink();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean deleteImageFromFireBase(String fileUrl) {
        try {
            // Extract the file name from the URL
            String fileName = fileUrl.split("/o/")[1].split("\\?")[0].replace("%2F", "/");
            return storageClient.bucket().get(fileName).delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
