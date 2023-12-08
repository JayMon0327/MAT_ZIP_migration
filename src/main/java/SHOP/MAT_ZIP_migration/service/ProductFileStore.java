package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.ProductImage;
import SHOP.MAT_ZIP_migration.exception.CustomErrorCode;
import SHOP.MAT_ZIP_migration.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Primary
@Component
public class ProductFileStore implements FileStore<ProductImage>{

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<ProductImage> storeFiles(List<MultipartFile> multipartFiles){
        List<ProductImage> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeProductFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    @Override
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Override
    public Resource getUrlResource(String fullPath) {
        try {
            return new UrlResource(fullPath);
        } catch (MalformedURLException e) {
            throw new CustomException(CustomErrorCode.FILE_URL_ERROR);
        }
    }

    private ProductImage storeProductFile(MultipartFile multipartFile){
        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            throw new CustomException(CustomErrorCode.FILE_PROCESSING_ERROR);
        }

        ProductImage productImage = new ProductImage();
        productImage.addFile(uploadFileName, storeFileName, getFullPath(storeFileName));
        return productImage;
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

