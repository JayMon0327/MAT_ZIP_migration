package SHOP.MAT_ZIP_migration.service;

import SHOP.MAT_ZIP_migration.domain.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public List<Image> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<Image> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    private Image storeFile(MultipartFile multipartFile) throws IOException {
        String uploadFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(uploadFileName);
        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        Image image = new Image();
        image.addFile(uploadFileName,storeFileName, getFullPath(storeFileName));
        return image;
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

    public String getFullPath(String filename) {
        return fileDir + filename;
    }
}

