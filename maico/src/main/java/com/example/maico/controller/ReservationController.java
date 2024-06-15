package com.example.maico.controller;


import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.maico.entity.Maiko;
import com.example.maico.entity.Reservation;
import com.example.maico.entity.User;
import com.example.maico.form.ReservationInputForm;
import com.example.maico.form.ReservationRegisterForm;
import com.example.maico.repository.MaikoRepository;
import com.example.maico.repository.ReservationRepository;
import com.example.maico.security.UserDetailsImpl;
import com.example.maico.service.ReservationService;

@Controller
public class ReservationController {
 private final ReservationRepository reservationRepository;
 private final MaikoRepository maikoRepository;
 private final ReservationService reservationService;
 
 public ReservationController(ReservationRepository reservationRepository, MaikoRepository maikoRepository,ReservationService reservationService) {
	 this.reservationRepository = reservationRepository;
	 this.maikoRepository = maikoRepository;
	 this.reservationService = reservationService;
 }
 
 @GetMapping("/reservations")
 public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
	 User user = userDetailsImpl.getUser();
	 Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
	 
	 model.addAttribute("reservationPage", reservationPage);
	 
	 return "reservations/index";
 }
 
 @GetMapping("/maikos/{id}/reservations/input")
 public String input(@PathVariable(name = "id") Integer id,
                     @ModelAttribute @Validated ReservationInputForm reservationInputForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes,
                     Model model)
 {   
     Maiko maiko = maikoRepository.getReferenceById(id);
     Integer numberOfPeople = reservationInputForm.getNumberOfPeople();   
     Integer capacity = maiko.getCapacity();
     
     if (numberOfPeople != null) {
         if (!reservationService.isWithinCapacity(numberOfPeople, capacity)) {
             FieldError fieldError = new FieldError(bindingResult.getObjectName(), "numberOfPeople", "人数が定員を超えています。");
             bindingResult.addError(fieldError);                
         }            
     }         
     
     if (bindingResult.hasErrors()) {            
         model.addAttribute("maiko", maiko);            
         model.addAttribute("errorMessage", "内容に不備があります。"); 
         return "maikos/show";
     }
     
     redirectAttributes.addFlashAttribute("reservationInputForm", reservationInputForm);           
     
     return "redirect:/maikos/{id}/reservations/confirm";
 }
 
 @GetMapping("/maikos/{id}/reservations/confirm")
 public String confirm(@PathVariable(name = "id") Integer id,
                       @ModelAttribute ReservationInputForm reservationInputForm,
                       @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,                          
                       Model model) 
 {        
     Maiko maiko = maikoRepository.getReferenceById(id);
     User user = userDetailsImpl.getUser(); 
             
     
     LocalDate checkinDate = reservationInputForm.getCheckinDate();
     LocalDate checkoutDate = reservationInputForm.getCheckoutDate();

     // 料金を計算する
     Integer price = maiko.getPrice();        
     Integer amount = reservationService.calculateAmount(checkinDate, checkoutDate, price);
     
     ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(maiko.getId(), user.getId(), checkinDate.toString(), checkoutDate.toString(), reservationInputForm.getNumberOfPeople(), amount);
     
     model.addAttribute("maiko", maiko);  
     model.addAttribute("reservationRegisterForm", reservationRegisterForm);       
     
     return "reservations/confirm";
 }
 
 @PostMapping("/maikos/{id}/reservations/create")
 public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {                
     reservationService.create(reservationRegisterForm);        
     
     return "redirect:/reservations?reserved";
 }
   
}
