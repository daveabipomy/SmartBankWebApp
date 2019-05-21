package com.example.springsecurity.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {
    String uploadImage(MultipartFile file);
    String uploadVideo(MultipartFile file);
}
