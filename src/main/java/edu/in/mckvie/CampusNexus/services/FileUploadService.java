package edu.in.mckvie.CampusNexus.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public  String uploadFile(MultipartFile file);
}
