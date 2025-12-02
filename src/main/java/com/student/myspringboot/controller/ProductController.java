package com.student.myspringboot.controller;

import com.student.myspringboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("products", productService.searchProducts(search));
        model.addAttribute("search", search);
        return "products/list";
    }
}
