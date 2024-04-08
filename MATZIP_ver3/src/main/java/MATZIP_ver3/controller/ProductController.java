package MATZIP_ver3.controller;

import MATZIP_ver3.config.auth.PrincipalDetails;
import MATZIP_ver3.dto.product.ResponseProductDto;
import MATZIP_ver3.service.ProductService;
import MATZIP_ver3.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

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
    public String viewProduct(@PathVariable Long id, @AuthenticationPrincipal PrincipalDetails principal, Model model) {
        ResponseProductDto productDetails = productService.getProductDetails(id);
        boolean hasPurchased = false;
        if (principal != null) {
            hasPurchased = reviewService.hasPurchasedProduct(principal.getMember(), id);
        }
        model.addAttribute("products", productDetails);
        model.addAttribute("hasPurchased", hasPurchased);
        return "product/detail";
    }
}
