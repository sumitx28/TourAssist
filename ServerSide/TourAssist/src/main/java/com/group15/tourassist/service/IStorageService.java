package com.group15.tourassist.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IStorageService {
    List<String> uploadImages(List<MultipartFile> images);
}
