package com.example.maico.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

 @Data 
public class ReviewForm {
	 
	 @NotNull
	private Integer maiko;
	 @NotNull
	private Integer user;
	 @NotNull
	    @Min(1)
	    @Max(5)
	private int rating;
	
	@NotBlank(message = "レビューを入力してください。")
	private String userComment;
}
