package com.hanna.sapeha.app.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    void uploadFile(MultipartFile file);

    void uploadFile(byte[] file) throws IOException;
}
