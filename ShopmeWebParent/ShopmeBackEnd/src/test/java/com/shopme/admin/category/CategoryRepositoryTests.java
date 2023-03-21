package com.shopme.admin.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
    
    @Autowired
    private CategoryRepository categoryRepo;

    // @Test
    // public void testCreateRootCategory(){
    //     Category category = Category.builder().name("Electronics").alias("Electronics").image("default.png").build();
    //     Category savedCategory = categoryRepo.save(category);

    //     assertThat(savedCategory.getId()).isGreaterThan(0);
    // }

    // @Test
    // public void testCreateSubCategory(){
    //     Category parent = Category.builder().id(5).build();
    //     Category cameras = new Category("Memory",parent);
        

    //     Category savedSubCategory = categoryRepo.save(cameras);
    //     assertThat(savedSubCategory.getId()).isGreaterThan(0);
    // }

    @Test
    public void testGetCategory(){
        Category category = categoryRepo.findById(1).get();

        Set<Category> children = category.getChildren();
        for(Category subCategory : children){
            System.out.println(subCategory.getName());
        }
        
        assertThat(children.size()).isGreaterThan(0);
    }
}
