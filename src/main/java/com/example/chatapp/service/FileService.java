package com.example.chatapp.service;

import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileService {
    String uploadFile(String folder, MultipartFile data);
    String generateUrl(String path);
    void deleteFile(String path);
}
