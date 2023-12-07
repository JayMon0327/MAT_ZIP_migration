package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.dto.ResponseDto;
import SHOP.MAT_ZIP_migration.dto.product.ProductAndItemDto;
import SHOP.MAT_ZIP_migration.dto.product.ResponseProductDto;
import SHOP.MAT_ZIP_migration.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.IOException;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/saveForm")
    public String saveForm() {
        return "product/saveForm";
    }

    @GetMapping("/product/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model) {
        ResponseProductDto productDetails = productService.getProductDetails(id);
        model.addAttribute("products", productDetails);
        return "product/updateForm";
    }

    @GetMapping("product/{id}")
    public String viewProduct(@PathVariable Long id, Model model) {
        ResponseProductDto productDetails = productService.getProductDetails(id);
        model.addAttribute("products", productDetails);
        return "product/detail";
    }
}
