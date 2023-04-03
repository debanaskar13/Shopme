package com.shopme.admin.category.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopme.admin.category.repository.CategoryRepository;
import com.shopme.common.entity.Category;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepo;

    public List<Category> listAll(){
        return categoryRepo.findAll();
    }

    public List<Category> listCategoriesUsedInForm(){
        List<Category> categories = categoryRepo.findAll();

        List<Category> hierarchicalCategories = new ArrayList<>();
		for (Category category : categories) {
			if (category.getParent() == null) {
                hierarchicalCategories.add(category);
				getHierarchicalCatgory(category, 1,hierarchicalCategories);
			}
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
               dash+="---";
            }
            cat.setName(dash+cat.getName());
            hierarchicalCategories.add(cat);
            getHierarchicalCatgory(cat, depth + 1,hierarchicalCategories);
        }
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
    }
}
