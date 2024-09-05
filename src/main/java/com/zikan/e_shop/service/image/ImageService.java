package com.zikan.e_shop.service.image;

import com.zikan.e_shop.dto.ImageDto;
import com.zikan.e_shop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImageById (Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages (List<MultipartFile> files, Long productId);

    void updateImage(MultipartFile file, Long imageId);

}
