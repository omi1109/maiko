package com.example.maico.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maico.entity.Maiko;
import com.example.maico.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Integer>{
	public List<Review> findTop6ByMaikoOrderByCreatedAtDesc(Maiko maiko);

		

}
