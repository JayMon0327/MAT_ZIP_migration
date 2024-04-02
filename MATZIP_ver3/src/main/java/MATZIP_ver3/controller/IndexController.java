package MATZIP_ver3.controller;

import MATZIP_ver3.domain.Product;
import MATZIP_ver3.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final ProductService productService;

    @GetMapping({"/", ""})
    public String index(Model model, @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable) {
        Page<Product> products = productService.productAll(pageable);
        model.addAttribute("products", products);
        return "index";
    }
}
