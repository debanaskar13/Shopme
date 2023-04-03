package com.shopme.admin.category.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shopme.admin.category.service.CategoryService;
import com.shopme.admin.util.FileUploadUtil;
import com.shopme.common.entity.Category;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String listAll(Model model){
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories", listCategories);
        return "categories/categories";
    }

    @GetMapping("/new")
    public String createNewCategory(Model model){
        model.addAttribute("hierarchicalCategories", categoryService.listCategoriesUsedInForm());
        model.addAttribute("pageTitle", "Create New Category");
        model.addAttribute("category", new Category());
        return "categories/category_form";
    }

    @PostMapping("/save")
    public String saveCategory(Category category,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException{
        String fileName =StringUtils.cleanPath(multipartFile.getOriginalFilename());
        category.setImage(fileName);

        Category savedCategory = categoryService.save(category);
        String uploadDir = "../category-images/"+savedCategory.getId();

        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return "redirect:/categories";
    }
}
