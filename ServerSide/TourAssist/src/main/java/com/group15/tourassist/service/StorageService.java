package com.group15.tourassist.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class StorageService implements IStorageService{

    /**
     * @param images List of package images
     * @return file paths
     */
    @Override
    public List<String> uploadImages(List<MultipartFile> images) {
        return null;
    }
}
