package com.group15.tourassist.repository;

import com.group15.tourassist.entity.PackageMedia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IPackageMediaRepositoryTest {

    @Mock
    private IPackageMediaRepository packageMediaRepository;

    private PackageMedia packageMediaOne;

    private Instant august20th2023UTC;

    @BeforeEach
    public void setup() {
        august20th2023UTC = Instant.parse("2023-08-20T00:00:00Z");
        packageMediaOne = new PackageMedia(1l, 1l, "/package_media/1.jpg", "tour image", august20th2023UTC);
    }

    /**
     * This tests the save functionality of IPackageMediaRepository by mocking the repository object.
     */
    @Test
    public void testSavePackageMedia() {
        // Arrange
        when(packageMediaRepository.save(packageMediaOne)).thenReturn(packageMediaOne);

        //Act
        PackageMedia savedPackageMedia = packageMediaRepository.save(packageMediaOne);

        // Assert
        assertNotNull(savedPackageMedia);
        assertEquals(packageMediaOne, savedPackageMedia);
    }

}