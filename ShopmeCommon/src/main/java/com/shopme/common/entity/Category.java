package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 64, nullable = false, unique = true)
    private String alias;

    @Column(length = 128, nullable = false)
    private String image;

    private boolean enabled;
    
    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent",fetch = FetchType.EAGER)
    private Set<Category> children = new HashSet<>();

    public Category(String name){
        this.name=name;
        this.alias=name;
        this.image="default.png";
    }

    public Category(String name,Category parent){
        this(name);
        this.parent = parent;
    }

	public Category(int id) {
		this.id = id;
	}

    public static Category copyCategory(Category category){
        Category cat = new Category();
        cat.id = category.getId();
        cat.name = category.getName();
        cat.alias = category.getAlias();
        cat.image = category.getImage();
        cat.enabled = category.isEnabled();
        cat.parent = category.getParent();
        cat.children = category.getChildren();
        return cat;
    }

    @Transient
    public String getImagePath(){
        return ( this.image==null || this.id==null || this.image.equals("default.png")) ? "/images/image-thumbnail.png" :  ("/category-images/"+this.id+"/"+this.image);
    }
}
