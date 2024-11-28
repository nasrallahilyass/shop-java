package com.code.shop.service.image;

import com.code.shop.dto.ImageDto;
import com.code.shop.exceptions.NotFoundException;
import com.code.shop.model.Image;
import com.code.shop.model.Product;
import com.code.shop.repository.ImageRepository;
import com.code.shop.service.product.IProductService;
import com.code.shop.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor

public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Image with id " + id + " not found"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).
                ifPresentOrElse(imageRepository::delete,
                        () -> {
                            throw new NotFoundException("Image not found");
                        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> savedImageDto = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImageBlob(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildUri = "api/v1/images/image/download/";
                String downloadUri = buildUri + image.getId();
                image.setDownloadUri(downloadUri);
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUri(buildUri + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUri());

                savedImageDto.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDto;
    }


    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Optional<Image> imageOptional = imageRepository.findById(imageId);
        try {
            if (imageOptional.isPresent()) {
                Image imageToUpdate = imageOptional.get();
                imageToUpdate.setFileName(file.getOriginalFilename());
                imageToUpdate.setFileType(file.getContentType());
                imageToUpdate.setImageBlob(new SerialBlob(file.getBytes()));
                imageRepository.save(imageToUpdate);
            } else {
                throw new NotFoundException("Image not found");
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());

        }
    }
}
