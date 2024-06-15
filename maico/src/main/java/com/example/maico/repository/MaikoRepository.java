package com.example.maico.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.maico.entity.Maiko;


public interface MaikoRepository extends JpaRepository<Maiko, Integer>{
	public Page<Maiko> findByNameLike(String keyword, Pageable pageable);
	
	public Page<Maiko> findByNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable);
	public Page<Maiko> findByAddressLike(String area, Pageable pageable);
	public Page<Maiko> findByPriceLessThanEqual(Integer price, Pageable pageable);
	public Page<Maiko> findByNameLikeOrAddressLikeOrderByCreatedAtDesc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<Maiko> findByNameLikeOrAddressLikeOrderByPriceAsc(String nameKeyword, String addressKeyword, Pageable pageable);  
    public Page<Maiko> findByAddressLikeOrderByCreatedAtDesc(String area, Pageable pageable);
    public Page<Maiko> findByAddressLikeOrderByPriceAsc(String area, Pageable pageable);
    public Page<Maiko> findByPriceLessThanEqualOrderByCreatedAtDesc(Integer price, Pageable pageable);
    public Page<Maiko> findByPriceLessThanEqualOrderByPriceAsc(Integer price, Pageable pageable); 
    public Page<Maiko> findAllByOrderByCreatedAtDesc(Pageable pageable);
    public Page<Maiko> findAllByOrderByPriceAsc(Pageable pageableMaiko);
    
    public List<Maiko> findTop10ByOrderByCreatedAtDesc();
}

