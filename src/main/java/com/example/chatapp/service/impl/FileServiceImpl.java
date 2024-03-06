package com.example.chatapp.service.impl;

import com.example.chatapp.common.constants.ErrorConstant;
import com.example.chatapp.common.exception.CommonException;
import com.example.chatapp.service.FileService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FileServiceImpl implements FileService {
    private final Logger log = LoggerFactory.getLogger(FileService.class);

    @Value("${minio.default-bucket}")
    private String defaultBucket;

    private MinioClient minioClient;

    public FileServiceImpl(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public String uploadFile(String folder, MultipartFile data) {
        String path = folder + '/' + UUID.randomUUID() + "_" + data.getOriginalFilename();
        try {
            minioClient.putObject(PutObjectArgs
                    .builder()
                    .bucket(defaultBucket)
                    .object(path)
                    .stream(data.getInputStream(), data.getSize(), -1)
                    .build());
            return path;
        } catch (Exception e) {
            log.error("Error while upload file. Message: " + e.getMessage());
            throw CommonException.create(HttpStatus.BAD_REQUEST).code(ErrorConstant.UPLOAD_FILE_FAILED);
        }
    }

    @Override
    public String generateUrl(String path)  {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs
                            .builder()
                            .method(Method.GET)
                            .bucket(defaultBucket)
                            .object(path)
                            .expiry(5, TimeUnit.DAYS)
                            .build());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            log.error(e.getMessage());
        }
        return "";
    }

    @Override
    public void deleteFile(String path) {

    }
}
