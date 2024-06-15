package com.example.maico.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.maico.entity.Maiko;
import com.example.maico.form.MaikoEditForm;
import com.example.maico.form.MaikoRegisterForm;
import com.example.maico.repository.MaikoRepository;
import com.example.maico.service.MaikoService;

@Controller
@RequestMapping("/admin/maikos")
public class AdminMaikoController {
	private final MaikoRepository maikoRepository;
	private final MaikoService maikoService;

	public AdminMaikoController(MaikoRepository maikoRepository, MaikoService maikoService) {
		this.maikoRepository = maikoRepository;
		this.maikoService = maikoService;
	}

	@GetMapping
	public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword) {
		Page<Maiko> maikoPage;
		
		if (keyword != null && !keyword.isEmpty()) {
			maikoPage = maikoRepository.findByNameLike("%" + keyword + "%",pageable);
		} else {
			maikoPage = maikoRepository.findAll(pageable);
		}
       
		
		model.addAttribute("maikoPage",maikoPage);
		model.addAttribute("keyword", keyword);
		return "admin/maikos/index";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable(name = "id") Integer id, Model model) {
		Maiko maiko = maikoRepository.getReferenceById(id);
		
		model.addAttribute("maiko", maiko);
		
		return "admin/maikos/show";
	}
	
	@GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("maikoRegisterForm", new MaikoRegisterForm());
        return "admin/maikos/register";
    }
	
	@PostMapping("/create")
	public String create(@ModelAttribute @Validated MaikoRegisterForm maikoRegisterForm,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "admin/maikos/register";
		}
		
		maikoService.create(maikoRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "舞妓さんを登録しました。");
		return "redirect:/admin/maikos";
	}
	@GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        Maiko maiko = maikoRepository.getReferenceById(id);
        String imageName = maiko.getImageName();
        MaikoEditForm maikoEditForm = new MaikoEditForm(maiko.getId(), maiko.getName(), null, maiko.getDescription(), maiko.getPrice(), maiko.getCapacity(), maiko.getPostalCode(), maiko.getAddress(), maiko.getPhoneNumber());
        
        model.addAttribute("imageName", imageName);
        model.addAttribute("maikoEditForm", maikoEditForm);
        
        return "admin/maikos/edit";
    }
	@PostMapping("/{id}/update")
	public String update(@ModelAttribute @Validated MaikoEditForm maikoEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			return "admin/maikos/edit";
		}
		
		maikoService.update(maikoEditForm);
		redirectAttributes.addFlashAttribute("successMessage", "舞妓情報を編集しました。");
		return "redirect:/admin/maikos";
	}
	@PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
        maikoRepository.deleteById(id);
                
        redirectAttributes.addFlashAttribute("successMessage", "舞妓さんを削除しました。");
        
        return "redirect:/admin/maikos";
    } 
}
