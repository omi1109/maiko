package com.example.maico.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.maico.entity.Maiko;
import com.example.maico.entity.Reservation;
import com.example.maico.entity.User;
import com.example.maico.form.ReservationRegisterForm;
import com.example.maico.repository.MaikoRepository;
import com.example.maico.repository.ReservationRepository;
import com.example.maico.repository.UserRepository;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;  
    private final MaikoRepository maikoRepository;  
    private final UserRepository userRepository;  
    
    public ReservationService(ReservationRepository reservationRepository, MaikoRepository maikoRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;  
        this.maikoRepository = maikoRepository;  
        this.userRepository = userRepository;  
    }    
    
    @Transactional
    public void create(ReservationRegisterForm reservationRegisterForm) { 
        Reservation reservation = new Reservation();
        Maiko maiko = maikoRepository.getReferenceById(reservationRegisterForm.getMaikoId());
        User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
        LocalDate checkinDate = LocalDate.parse(reservationRegisterForm.getCheckinDate());
        LocalDate checkoutDate = LocalDate.parse(reservationRegisterForm.getCheckoutDate());         
                
        reservation.setMaiko(maiko);
        reservation.setUser(user);
        reservation.setCheckinDate(checkinDate);
        reservation.setCheckoutDate(checkoutDate);
        reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
        reservation.setAmount(reservationRegisterForm.getAmount());
        
        reservationRepository.save(reservation);
    }    
   
	// 人数が定員以下かどうかをチェックする
    public boolean isWithinCapacity(Integer numberOfPeople, Integer capacity) {
        return numberOfPeople <= capacity;
    }
    
    // 料金を計算する
    public Integer calculateAmount(LocalDate checkinDate, LocalDate checkoutDate, Integer price) {
        long numberOfNights = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        int amount = price * (int)numberOfNights;
        return amount;
    }
	
}
