package com.shopme.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.util.Util;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Util util;

	@Autowired
	private RoleRepository roleRepo;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		List<Role> roles =(List<Role>) roleRepo.findAll();
		if(roles.isEmpty()) {
			util.createAllRoles();
		}
		List<User> listUsers = userService.listAll();
		model.addAttribute("listUsers",listUsers);
		return "users";
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = userService.listRoles();
		User user = new User();
		user.setEnabled(true);
		
		model.addAttribute("pageTitle","Create New User");
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);		
		
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		System.out.println(user);
		userService.save(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully");
		
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable("id") Integer id,Model model, RedirectAttributes redirectAttributes) {
		try {	
			List<Role> listRoles = userService.listRoles();
			User user = userService.get(id);
			model.addAttribute("pageTitle","Edit User (ID: "+id+")");
			model.addAttribute("user",user);
			model.addAttribute("listRoles", listRoles);
			return "user_form";
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
		}
		
	}
	
	@GetMapping("/users/delete/{id}")
	public String delteUser(@PathVariable("id") Integer id,Model model, RedirectAttributes redirectAttributes) {
		try {	
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message","The user ID "+id+" has been deleted successfully");
		} catch (UserNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/users";
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
