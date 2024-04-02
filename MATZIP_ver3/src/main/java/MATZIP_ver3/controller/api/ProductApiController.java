package MATZIP_ver3.controller.api;

import MATZIP_ver3.config.auth.PrincipalDetails;
import MATZIP_ver3.dto.ResponseDto;
import MATZIP_ver3.dto.product.RequestSaveProductAndItemDto;
import MATZIP_ver3.dto.product.RequestUpdateProductAndItemDto;
import MATZIP_ver3.service.FileStore;
import MATZIP_ver3.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "04.Product")
public class ProductApiController {

    private final ProductService productService;
    private final FileStore fileStore;

    /**
     * multipart/form-data 전송문제로 @ModelAttribute 사용
     * 추후 이미지 파일을 S3로 전송하는 로직으로 변경해서 REST API를 유지할 것임
     */
    @PostMapping("/product")
    @Operation(summary = "상품글 등록", description = "상품 등록")
    public ResponseDto<Integer> save(@Valid @ModelAttribute RequestSaveProductAndItemDto dto,
                                     @AuthenticationPrincipal PrincipalDetails principal) {
        Long productId = productService.saveProductAndItem(dto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1, productId);
    }

    @PutMapping("/product/{id}")
    @Operation(summary = "상품글 변경", description = "상품 변경")
    public ResponseDto<Integer> update(@PathVariable Long id, @Valid @ModelAttribute RequestUpdateProductAndItemDto dto) {
        Long productId = productService.updateProductAndItem(id, dto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1, productId);
    }

    @DeleteMapping("/product/{id}")
    @Operation(summary = "상품글 삭제", description = "상품 삭제")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    @GetMapping("/images/{filename}")
    @Operation(summary = "상품 이미지 조회", description = "이미지 조회")
    public Resource viewImage(@PathVariable String filename) {
        return fileStore.getUrlResource(filename);
    }
}
