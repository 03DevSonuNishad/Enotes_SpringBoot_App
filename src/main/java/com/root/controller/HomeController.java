package com.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.root.entity.User;
import com.root.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String indexPage() {

		return "index";
	}

	@GetMapping("/register")
	public String registerPage() {

		return "register";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session) {

		boolean f = userService.existEmailCheck(user.getEmail());

		if (f) {

			session.setAttribute("msg", "Email Already Exists..!");

		} else {
			User newUser = userService.saveUser(user);

			if (newUser != null) {
				session.setAttribute("msg", "Register Successfully..!");
			} else {
				session.setAttribute("msg", "something wrong on server..!");
			}
		}

		return "redirect:/register";
	}

	@GetMapping("/signin")
	public String loginPage() {

		return "login";
	}

	
}
