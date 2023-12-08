package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.ReviewImage;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ReviewFileStore implements FileStore<ReviewImage>{

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<ReviewImage> storeFiles(List<MultipartFile> multipartFiles){
        List<ReviewImage> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    @Override
    public String getFullPath(String filename) {
        return null;
    }

    @Override
    public Resource getUrlResource(String fullPath) {
        return null;
    }

    private ReviewImage storeFile(MultipartFile multipartFile) {
        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new CustomException(CustomErrorCode.FILE_PROCESSING_ERROR);
        }

        ReviewImage reviewImage = new ReviewImage();
        reviewImage.addFile(uploadFileName, storeFileName, getFullPath(storeFileName));
        return reviewImage;
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extracted(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
