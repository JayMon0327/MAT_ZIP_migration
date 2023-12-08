package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.ProductImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStore<T> {
    List<T> storeFiles(List<MultipartFile> multipartFiles) throws IOException;
    String getFullPath(String filename);
}
