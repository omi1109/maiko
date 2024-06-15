package com.example.maico.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaikoEditForm {
    @NotNull
    private Integer id;    
    
    @NotBlank(message = "舞妓名を入力しとぉくれやす。")
    private String name;
        
    private MultipartFile imageFile;
    
    @NotBlank(message = "特技を入力しとぉくれやす。")
    private String description;   
    
    @NotNull(message = "お花代を入力しとぉくれやす。")
    @Min(value = 1, message = "宿泊料金は1円以上やないとあきまへん。")
    private Integer price; 
    
    @NotNull(message = "最大接客人数を入力しとぉくれやす。")
    @Min(value = 1, message = "定員は1人以上やないとあきまへん。")
    private Integer capacity;       
    
    @NotBlank(message = "郵便番号を入力しとぉくれやす。")
    private String postalCode;
    
    @NotBlank(message = "住所を入力しとぉくれやす。")
    private String address;
    
    @NotBlank(message = "電話番号を入力しとぉくれやす。")
    private String phoneNumber;
}
