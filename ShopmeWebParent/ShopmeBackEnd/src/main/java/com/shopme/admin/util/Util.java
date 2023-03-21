package com.shopme.admin.util;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shopme.admin.user.repository.RoleRepository;
import com.shopme.common.entity.Role;

@Component
public class Util {
	@Autowired
    private RoleRepository roleRepo;

    public void createAllRoles() {
    	Role roleAdmin = new Role("Admin", "Manage Everything");
        Role roleSalesPerson = new Role("Salesperson",
                "Manage product price, customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor",
                "Manage categories, brands, products, articles and menus");
        Role roleShipper = new Role("Shipper", "View products, view orders, and update order status");
        Role roleAssistant = new Role("Assistant", "Manage Questions and reviews");

        roleRepo.saveAll(List.of(roleAdmin,roleSalesPerson, roleEditor, roleShipper,
                roleAssistant));
    }
}
