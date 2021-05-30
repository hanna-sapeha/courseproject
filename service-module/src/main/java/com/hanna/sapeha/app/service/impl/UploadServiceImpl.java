package com.hanna.sapeha.app.service.impl;

import com.hanna.sapeha.app.service.UploadService;
import com.hanna.sapeha.app.service.model.DocumentJsonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private ObjectMapper objectMapper;


    @Override
    public void uploadFile(MultipartFile file) {
//лучше работать с байтами
    }

    @Override
    public void uploadFile(byte[] file) throws IOException {
        List<DocumentJsonDTO> values = objectMapper.readValue(file, new TypeReference<>(){});




    }
}
