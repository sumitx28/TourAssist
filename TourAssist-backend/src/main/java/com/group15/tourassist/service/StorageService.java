package com.group15.tourassist.service;

import com.cloudinary.Cloudinary;
import com.group15.tourassist.core.utils.ConstantUtils;
import com.group15.tourassist.service.impl.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StorageService implements IStorageService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * @param images List of package images
     * @return file paths
     */
    @Override
    public List<String> uploadImages(List<MultipartFile> images) throws IOException {
        List<String> paths = new ArrayList<>();

        for(MultipartFile image : images) {
            Map metaData = this.cloudinary.uploader().upload(image.getBytes(), Map.of());
            paths.add((String) metaData.get(ConstantUtils.KEY_URL));
        }
        return paths;
    }
}
