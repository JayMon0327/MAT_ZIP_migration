package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/seller/product")
public class ProductSellerController {

    private final ProductService productService;

    @GetMapping("/saveForm")
    public String saveForm() {
        return "product/saveForm";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id) {
        return "product/updateForm";
    }

}
