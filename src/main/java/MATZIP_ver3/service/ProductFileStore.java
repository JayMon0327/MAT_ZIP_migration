package MATZIP_ver3.service;

import MATZIP_ver3.domain.ProductImage;
import MATZIP_ver3.exception.CustomErrorCode;
import MATZIP_ver3.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ProductFileStore implements FileStore<ProductImage> {

    private static final String pathHeader = "file:";
    private static final String NameCriterion = ".";
    private static final int NameRange = 1;

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<ProductImage> storeFiles(List<MultipartFile> multipartFiles) {
        List<ProductImage> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeProductFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    @Override
    public Resource getUrlResource(String filename) {
        try {
            return new UrlResource(pathHeader + getFullPath(filename));
        } catch (MalformedURLException e) {
            throw new CustomException(CustomErrorCode.FILE_URL_ERROR);
        }
    }

    private String getFullPath(String filename) {
        return fileDir + filename;
    }

    private ProductImage storeProductFile(MultipartFile multipartFile) {
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
        return uuid + NameCriterion + ext;
    }

    private String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(NameCriterion);
        return originalFilename.substring(pos + NameRange);
    }
}

