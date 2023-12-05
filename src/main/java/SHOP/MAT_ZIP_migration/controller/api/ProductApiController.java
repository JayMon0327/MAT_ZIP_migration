package SHOP.MAT_ZIP_migration.controller.api;

import SHOP.MAT_ZIP_migration.config.auth.PrincipalDetails;
import SHOP.MAT_ZIP_migration.domain.Product;
import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.product.RequestProductDto;
import SHOP.MAT_ZIP_migration.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseDto<Integer> save(@RequestBody RequestProductDto requestProductDto, @AuthenticationPrincipal PrincipalDetails principal) {
        productService.save(requestProductDto, principal.getMember());
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PutMapping("/product/{id}")
    public ResponseDto<Integer> update(@PathVariable Long id, @RequestBody RequestProductDto requestProductDto) {
        productService.update(id, requestProductDto);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @DeleteMapping("/product/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable Long id) {
        productService.delete(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
