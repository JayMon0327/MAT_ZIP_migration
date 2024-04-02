package MATZIP_ver3.controller;

import MATZIP_ver3.dto.product.ResponseProductDto;
import MATZIP_ver3.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


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
