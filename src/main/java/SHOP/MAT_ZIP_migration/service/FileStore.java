package SHOP.MAT_ZIP_migration.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStore<T> {
    List<T> storeFiles(List<MultipartFile> multipartFiles);

    Resource getUrlResource(String fullPath);
}
