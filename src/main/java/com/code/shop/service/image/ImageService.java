package com.code.shop.service.image;

import com.code.shop.dto.ImageDto;
import com.code.shop.exceptions.NotFoundException;
import com.code.shop.model.Image;
import com.code.shop.model.Product;
import com.code.shop.repository.ImageRepository;
import com.code.shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public List<ImageDto> getAllImages() {
        List<ImageDto> imageDtos = new ArrayList<>();
        List<Image> images = imageRepository.findAll();
        for (Image image : images) {
            ImageDto imageDto = new ImageDto();
            imageDto.setId(image.getId());
            imageDto.setFileName(image.getFileName());
            imageDto.setDownloadUrl(image.getDownloadUri());
            imageDtos.add(imageDto);
        }
        return imageDtos;
    }

    @Override
    public Image getImageById(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image with id " + id + " not found"));
    }

    @Override
    public void deleteImageById(String id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        () -> {
                            throw new NotFoundException("Image not found");
                        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, String productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImageData(file.getBytes());  // Store the image bytes in MongoDB
                image.setProduct(product);

                // Set the download URL (assuming it's a relative URL)
                String buildUri = "/api/v1/images/image/download/";
                String downloadUri = buildUri + image.getId();
                image.setDownloadUri(downloadUri);

                // Save the image to MongoDB
                Image savedImage = imageRepository.save(image);

                // Set the download URI after the image is saved
                savedImage.setDownloadUri(buildUri + savedImage.getId());
                imageRepository.save(savedImage);

                // Update the product's images list
                if (product.getImages() == null) {
                    product.setImages(new ArrayList<>());
                }
                product.getImages().add(savedImage);
                productService.updateProductImages(product.getId(), product.getImages());

                // Convert to DTO and add to list
                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUri());

                savedImageDto.add(imageDto);

            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }

    @Override
    public void updateImage(MultipartFile file, String imageId) {
        Image imageToUpdate = imageRepository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Image not found"));

        try {
            imageToUpdate.setFileName(file.getOriginalFilename());
            imageToUpdate.setFileType(file.getContentType());
            imageToUpdate.setImageData(file.getBytes());  // Update the image bytes in MongoDB
            imageRepository.save(imageToUpdate);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
