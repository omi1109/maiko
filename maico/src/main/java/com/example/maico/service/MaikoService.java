package com.example.maico.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.maico.entity.Maiko;
import com.example.maico.form.MaikoEditForm;
import com.example.maico.form.MaikoRegisterForm;
import com.example.maico.repository.MaikoRepository;



@Service
public class MaikoService {
   private final MaikoRepository maikoRepository;
   
   public MaikoService(MaikoRepository maikoRepository) {
	   this.maikoRepository = maikoRepository;
   }
   
   @Transactional
   public void create(MaikoRegisterForm maikoRegisterForm) {
	   Maiko maiko = new Maiko();
	   MultipartFile imageFile = maikoRegisterForm.getImageFile();
	   
	   if (!imageFile.isEmpty()) {
		   String imageName = imageFile.getOriginalFilename();
		   String hashedImageName = generateNewFileName(imageName);
		   Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
		   copyImageFile(imageFile, filePath);
		   maiko.setImageName(hashedImageName);
	   }
	   maiko.setName(maikoRegisterForm.getName());
	   maiko.setDescription(maikoRegisterForm.getDescription());
	   maiko.setPrice(maikoRegisterForm.getPrice());
	   maiko.setCapacity(maikoRegisterForm.getCapacity());
	   maiko.setPostalCode(maikoRegisterForm.getPostalCode());
	   maiko.setAddress(maikoRegisterForm.getAddress());
	   maiko.setPhoneNumber(maikoRegisterForm.getPhoneNumber());
	   maikoRepository.save(maiko);
   }
   
   @Transactional
   public void update(MaikoEditForm maikoEditForm) {
	   Maiko maiko = maikoRepository.getReferenceById(maikoEditForm.getId());
	   MultipartFile imageFile = maikoEditForm.getImageFile();
	   
	   if (!imageFile.isEmpty()) {
		   String imageName = imageFile.getOriginalFilename();
		   String hashedImageName = generateNewFileName(imageName);
		   Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
		   copyImageFile(imageFile, filePath);
		   maiko.setImageName(hashedImageName);
	   }
	   maiko.setName(maikoEditForm.getName());
	   maiko.setDescription(maikoEditForm.getDescription());
	   maiko.setPrice(maikoEditForm.getPrice());
	   maiko.setCapacity(maikoEditForm.getCapacity());
	   maiko.setPostalCode(maikoEditForm.getPostalCode());
	   maiko.setAddress(maikoEditForm.getAddress());
	   maiko.setPhoneNumber(maikoEditForm.getAddress());
	   maikoRepository.save(maiko);
   }
   
   public String generateNewFileName(String fileName) {
	   String[] fileNames = fileName.split("\\.");
	   for (int i = 0; i < fileNames.length - 1; i++) {
		   fileNames[i] = UUID.randomUUID().toString();
	   }
	   String hasedFileName = String.join(".", fileNames);
	   return hasedFileName; 
   }
   
   public void copyImageFile(MultipartFile imageFile, Path filePath) {
	   try {
		   Files.copy(imageFile.getInputStream(), filePath);
	   } catch (IOException e) {
		   e.printStackTrace();
	   }
   }
   
}
