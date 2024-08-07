package com.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Data // include getters and setter 
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString


public class UserForms {

    @NotBlank(message = "username is required ")
    @Size(min = 3,message = "minimum  3 character requried")
    private String name;

    @Email(message = "invalid email address ")
    @NotBlank(message="Email is required ")
    // or use regurlar expression
    private String email;
    
    @NotBlank(message="password is required ")
    @Size(min=6,message ="min 6 character password " )
    private String password;
    
    @NotBlank(message = "about is required ")
    private String about;
    @Size(min=8,max=12,message="invalid phone number ")    
    private String phoneNumber;


}
