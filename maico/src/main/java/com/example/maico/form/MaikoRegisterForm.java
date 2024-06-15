package com.example.maico.form;

 import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
 
 @Data
public class MaikoRegisterForm {
     @NotBlank(message = "舞妓名を入力しとぉくれやす。")
     private String name;
         
     private MultipartFile imageFile;
     
     @NotBlank(message = "お座敷遊びを入力しとぉくれやす。")
     private String description;   
     
     @NotNull(message = "お花代を入力しとぉくれやす。")
     @Min(value = 1, message = "お花代は1円以上やないとあきまへん。")
     private Integer price;  
     
     @NotNull(message = "最大人数を入力しとぉくれやす。")
     @Min(value = 1, message = "人数は1人以上やないとあきまへん。")
     private Integer capacity;     
     
     @NotBlank(message = "郵便番号を入力しとぉくれやす。")
     private String postalCode;
     
     @NotBlank(message = "住所を入力しとぉくれやす。")
     private String address;
     
     @NotBlank(message = "電話番号を入力しとぉくれやす。")
     private String phoneNumber;
}
