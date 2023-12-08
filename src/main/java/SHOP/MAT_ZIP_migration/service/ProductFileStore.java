package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.ProductImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Primary
@Component
public class ProductFileStore implements FileStore<ProductImage>{

    @Value("${file.dir}")
    private String fileDir;

    @Override
    public List<ProductImage> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
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

    private ProductImage storeProductFile(MultipartFile multipartFile) throws IOException {
        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

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

