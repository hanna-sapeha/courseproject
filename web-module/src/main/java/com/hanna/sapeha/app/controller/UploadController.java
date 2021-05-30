package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UploadController {


    private UploadService uploadService;

    @GetMapping("/upload")
    public String uploadFilePage(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        uploadService.uploadFile(file);


        return "redirect:/upload";
    }


    @PostMapping ("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws IOException {
        uploadService.uploadFile(file);

        uploadService.uploadFile(file.getBytes());


        return "redirect:/upload";
    }
}
