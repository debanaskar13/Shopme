package com.shopme.admin.category.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.category.CategoryNotFoundException;
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
        model.addAttribute("hierarchicalCategories", categoryService.listAll());
        model.addAttribute("pageTitle", "Create New Category");
        model.addAttribute("category", new Category());
        return "categories/category_form";
    }

    @PostMapping("/save")
    public String saveCategory(Category category,@RequestParam("fileImage") MultipartFile multipartFile,RedirectAttributes redirectAttributes) throws IOException{
        if(!multipartFile.isEmpty()){
            String fileName =StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);
            
            Category savedCategory = categoryService.save(category);
            String uploadDir = "../category-images/"+savedCategory.getId();
            
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }else{
            categoryService.save(category);
        }
        redirectAttributes.addFlashAttribute("message","The Category has been saved successfully");
        return "redirect:/categories";
    }

    @GetMapping("/edit/{categoryId}")
    public String getCategoryEditPage(@PathVariable("categoryId") int categoryId,Model model,RedirectAttributes redirectAttributes){
        try {
            Category category = categoryService.getCategory(categoryId);
            model.addAttribute("category", category);
            model.addAttribute("hierarchicalCategories", categoryService.listAll());
            model.addAttribute("pageTitle", "Edit Category ID ("+categoryId+")");
            return "categories/category_form";
        } catch (CategoryNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",e.getMessage());
            return "redirect:/categories";
        }
    }
}
