package com.code.shop.service.image;

import com.code.shop.dto.ImageDto;
import com.code.shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    List<ImageDto> getAllImages();

    Image getImageById(String id);  // Change id type to String for MongoDB

    void deleteImageById(String id);  // Change id type to String for MongoDB

    List<ImageDto> saveImages(List<MultipartFile> file, String productId);  // No change for productId

    void updateImage(MultipartFile file, String imageId);  // Change imageId type to String for MongoDB
}
