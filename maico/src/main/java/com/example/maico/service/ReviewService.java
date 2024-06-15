package com.example.maico.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.maico.entity.Maiko;
import com.example.maico.entity.Review;
import com.example.maico.entity.User;
import com.example.maico.form.ReviewForm;
import com.example.maico.repository.MaikoRepository;
import com.example.maico.repository.ReviewRepository;
import com.example.maico.repository.UserRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MaikoRepository maikoRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, MaikoRepository maikoRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.maikoRepository = maikoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Review create(ReviewForm reviewForm) {
        Review review = new Review();
        Maiko maiko = maikoRepository.findById(reviewForm.getMaiko()).orElseThrow(() -> new IllegalArgumentException("Invalid Maiko ID: " + reviewForm.getMaiko()));
        User user = userRepository.findById(reviewForm.getUser()).orElseThrow(() -> new IllegalArgumentException("Invalid User ID: " + reviewForm.getUser()));

        review.setMaiko(maiko);
        review.setUser(user);
        review.setRating(reviewForm.getRating());
        review.setUserComment(reviewForm.getUserComment());
        return reviewRepository.save(review);
    }
}
