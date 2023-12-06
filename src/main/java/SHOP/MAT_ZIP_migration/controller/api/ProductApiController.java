package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestItemDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.service.ItemService;
import SHOP.MAT_ZIP_migration.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseDto<Integer> save(@Valid @RequestBody RequestProductDto requestProductDto,
                                     @Valid @RequestBody List<RequestItemDto> requestItemDtos,
                                     @AuthenticationPrincipal PrincipalDetails principal) throws IOException {
        Long productId = productService.saveProductAndItem(requestProductDto, requestItemDtos, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1, productId);
    }

    @PutMapping("/product/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody RequestProductDto requestProductDto) throws IOException {
        productService.updateProduct(id, requestProductDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @DeleteMapping("/product/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
