package com.group15.tourassist.service;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {

    @InjectMocks
    private StorageService storageService;

    @Mock
    private Cloudinary cloudinary;

    private List<MultipartFile> images = new ArrayList<>();


    @Test
    void testUploadImages() throws IOException {
         // Act
        List<String> paths = storageService.uploadImages(images);

        // Assert
        assertEquals(0, paths.size());
    }
}