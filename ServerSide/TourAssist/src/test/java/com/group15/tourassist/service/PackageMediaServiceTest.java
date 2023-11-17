package com.group15.tourassist.service;

import com.group15.tourassist.repository.IPackageMediaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PackageMediaServiceTest {

    @InjectMocks
    private PackageMediaService packageMediaService;

    @Mock
    private IPackageMediaRepository packageMediaRepository;

    @Mock
    private IStorageService storageService;

    private List<String> cloudPaths = Arrays.asList("abc.jpg", "test_1.png");

    @Test
    void testSaveAllPackageMedia() throws IOException {
        // Arrange
        when(storageService.uploadImages(any())).thenReturn(cloudPaths);

        // Act
        packageMediaService.saveAllPackageMedia(new ArrayList<>(), 1L);

        // Assert
        verify(packageMediaRepository, times(2)).save(any());
    }
}