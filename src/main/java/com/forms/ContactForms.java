package com.forms;

import org.springframework.web.multipart.MultipartFile;

import com.Scm.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ContactForms 
{   
    @NotBlank(message = "Name is required")    
    private String name;

      @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address [ example@gmail.com ]")
    private String email;

     @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number")
    //regular  regex can be used 
    private String phoneNumber;
    
    
    @NotBlank(message = "Address is required")
    private String address;
    
    private String description ;

    private boolean favorite;

    private String websiteLink;

    private String LindeinLink;

    // annotation create karenge jo file validate
    // size
    // resolution

    @ValidFile(message = "Invalid File")
    private MultipartFile contactImage;

    private String picture;
}
