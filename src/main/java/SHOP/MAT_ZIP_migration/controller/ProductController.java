package SHOP.MAT_ZIP_migration.controller;

import SHOP.MAT_ZIP_migration.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/saveForm")
    public String saveForm() {
        return "product/saveForm";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute(productService.findByProduct(id));
        return "product/updateForm";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute(productService.findByProduct(id));
        return "product/detail";
    }

}
