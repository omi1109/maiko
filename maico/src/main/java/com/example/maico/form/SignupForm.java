package com.example.maico.form;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupForm {
@NotBlank(message = "氏名を入力しとぉくれやす。")
private String name;

@NotBlank(message = "フリガナを入力しとぉくれやす。")
private String furigana;

@NotBlank(message = "郵便番号をしとぉくれやす。")
private String postalCode;

@NotBlank(message = "住所を入力しとぉくれやす。")
private String address;

@NotBlank(message = "電話番号をしとぉくれやす。")
private String phoneNumber;

@NotBlank(message = "メールアドレスを入力しとぉくれやす。")
@Email(message = "メールアドレスが正しい形式ではありまへん。")
private String email;

@NotBlank(message = "パスワードを入力しとぉくれやす。")
@Length(min = 8, message="パスワードは8文字以上じゃないとあきまへん。")
private String password;

@NotBlank(message = "パスワード（確認用）を入力しとぉくれやす。")
private String passwordConfirmation;

}
