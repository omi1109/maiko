package com.example.maico.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.maico.entity.Maiko;
import com.example.maico.repository.MaikoRepository;

@Controller
public class HomeController {
	private final MaikoRepository maikoRepository;
	
	public HomeController(MaikoRepository maikoRepository) {
		this.maikoRepository = maikoRepository;
	}
@GetMapping("/")
public String index(Model model) {
	List<Maiko> newMaikos = maikoRepository.findTop10ByOrderByCreatedAtDesc();
	model.addAttribute("newMaikos", newMaikos);
	return "index";
}
}
