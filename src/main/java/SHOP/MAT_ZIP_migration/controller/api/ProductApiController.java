package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestSaveProductAndItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestUpdateProductAndItemDto;
import SHOP.MAT_ZIP_migration.service.FileStore;
import SHOP.MAT_ZIP_migration.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductApiController {

    private final ProductService productService;
    private final FileStore fileStore;

    /**
     * multipart/form-data 전송문제로 @ModelAttribute 사용
     * 추후 이미지 파일을 S3로 전송하는 로직으로 변경해서 REST API를 유지할 것임
     */
    @PostMapping("/product")
    public ResponseDto<Integer> save(@Valid @ModelAttribute RequestSaveProductAndItemDto dto,
                                     @AuthenticationPrincipal PrincipalDetails principal) {
        Long productId = productService.saveProductAndItem(dto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1, productId);
    }

    @PutMapping("/product/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @Valid @ModelAttribute RequestUpdateProductAndItemDto dto) {
        Long productId = productService.updateProductAndItem(id, dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1, productId);
    }

    @DeleteMapping("/product/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/images/{filename}")
    public Resource viewImage(@PathVariable String filename) {
        return fileStore.getUrlResource(filename);
    }
}
