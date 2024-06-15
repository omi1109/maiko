package com.example.maico.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.maico.entity.Maiko;
import com.example.maico.entity.Review;
import com.example.maico.entity.User;
import com.example.maico.form.ReservationInputForm;
import com.example.maico.form.ReviewForm;
import com.example.maico.repository.MaikoRepository;
import com.example.maico.repository.ReviewRepository;
import com.example.maico.repository.UserRepository;

@Controller
@RequestMapping("/maikos")
public class MaikoController {
	private final MaikoRepository maikoRepository;
	 private final UserRepository userRepository;
	    private final ReviewRepository reviewRepository;
	public MaikoController(MaikoRepository maikoRepository,UserRepository userRepository,ReviewRepository reviewRepository) {
		this.maikoRepository = maikoRepository;
		this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
		
	}

	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name = "area", required = false) String area,
			@RequestParam(name = "price", required = false) Integer price,
			@RequestParam(name = "order", required = false) String order,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Page<Maiko> maikoPage;
		if (keyword != null && !keyword.isEmpty()) {
			if (order != null && order.equals("priceAsc")) {
				maikoPage = maikoRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%",
						"%" + keyword + "%", pageable);
			} else {
				maikoPage = maikoRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%",
						"%" + keyword + "%", pageable);
			}
		} else if (area != null && !area.isEmpty()) {
			if (order != null && order.equals("priceAsc")) {
				maikoPage = maikoRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
			} else {
				maikoPage = maikoRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
			}
		} else if (price != null) {
			if (order != null && order.equals("priceAsc")) {
				maikoPage = maikoRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
			} else {
				maikoPage = maikoRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
			}
		} else {
			if (order != null && order.equals("priceAsc")) {
				maikoPage = maikoRepository.findAllByOrderByPriceAsc(pageable);
			} else {
				maikoPage = maikoRepository.findAllByOrderByCreatedAtDesc(pageable);
			}
		}
		model.addAttribute("maikoPage", maikoPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("area", area);
		model.addAttribute("price", price);
		model.addAttribute("order", order);

		return "maikos/index";
	}
	@GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Maiko maiko = maikoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid Maiko ID: " + id));
        List<Review> newReviews = reviewRepository.findTop6ByMaikoOrderByCreatedAtDesc(maiko);
        model.addAttribute("maiko", maiko);         
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        model.addAttribute("reviews", newReviews);
        model.addAttribute("reviewForm", new ReviewForm());

        // ユーザー情報もモデルに追加する
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + id)); // 実際のユーザー取得ロジックを適用
        model.addAttribute("user", user);

        return "maikos/show";
    }
	   
	
}
