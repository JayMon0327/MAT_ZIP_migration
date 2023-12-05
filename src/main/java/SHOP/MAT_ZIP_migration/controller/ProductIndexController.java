package SHOP.MAT_ZIP_migration.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductIndexController {

    @GetMapping({"/",""})
    public String index(Model model, @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)
    Pageable pageable) {
        return "index";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id) {
        return "product/detail";
    }

}
