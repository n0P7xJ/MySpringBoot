package com.student.myspringboot.controller;

import com.student.myspringboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public String list(Model model) {
        model.addAttribute("products", productService.findAll());
        return "products/list";
    }
}
