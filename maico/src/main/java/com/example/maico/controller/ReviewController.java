package com.example.maico.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.maico.entity.Maiko;
import com.example.maico.entity.User;
import com.example.maico.form.ReviewForm;
import com.example.maico.repository.MaikoRepository;
import com.example.maico.repository.UserRepository;
import com.example.maico.service.ReviewService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/maikos")
public class ReviewController {

    private final MaikoRepository maikoRepository;
    private final ReviewService reviewService;
    private final UserRepository userRepository;
    

    public ReviewController(MaikoRepository maikoRepository, ReviewService reviewService, UserRepository userRepository) {
        this.maikoRepository = maikoRepository;
        this.reviewService = reviewService;
        this.userRepository = userRepository;
       
    }

    @GetMapping("/{id}/reviews/reviewInput")
    public String showReviewForm(@PathVariable(name = "id") Integer id, Model model) {
        Maiko maiko = maikoRepository.getReferenceById(id);
        User user = userRepository.getReferenceById(id);
        model.addAttribute("maiko", maiko);
        model.addAttribute("user", user);
        model.addAttribute("reviewForm", new ReviewForm());
        return "reviews/reviewInput";
    }

    @PostMapping("/reviews")
    public String submitReview(@Valid ReviewForm reviewForm, BindingResult result, RedirectAttributes attributes) {
//        if (result.hasErrors()) {
//            return "reviews/reviewInput";
//        }
        System.out.println("reviewForm->"+reviewForm);
        reviewService.create(reviewForm);
        attributes.addFlashAttribute("message", "レビューが送信されました！");
        return "redirect:/maikos/" + reviewForm.getMaiko();
    }

}
