package com.shopme.admin.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.category.CategoryNotFoundException;
import com.shopme.admin.category.repository.CategoryRepository;
import com.shopme.common.entity.Category;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> listAll() {
        List<Category> rootCategories = categoryRepo.findRootCategories();
        return listHierarchicalCategories(rootCategories);
    }

    private List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyCategory(rootCategory));
            getHierarchicalCatgory(rootCategory, 1, hierarchicalCategories);
        }

        return hierarchicalCategories;
    }

    private void getHierarchicalCatgory(Category category, int depth, List<Category> hierarchicalCategories) {
        if (category == null) {
            return;
        }
        for (Category cat : category.getChildren()) {
            String dash = "";
            for (int i = 0; i < depth; i++) {
                dash += "---";
            }
            Category copCategory = Category.copyCategory(cat);
            copCategory.setName(dash + cat.getName());
            hierarchicalCategories.add(copCategory);
            getHierarchicalCatgory(cat, depth + 1, hierarchicalCategories);
        }
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public Category getCategory(int categoryId) throws CategoryNotFoundException{
        try {
            return categoryRepo.findById(categoryId).get();
        } catch (NoSuchElementException e) {
            throw new CategoryNotFoundException("Category with id "+categoryId+" not found !");
        }
    }
}
