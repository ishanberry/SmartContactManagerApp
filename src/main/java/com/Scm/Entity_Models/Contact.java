package com.Scm.Entity_Models;


import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact{
    @Id
    private String  id;
    private String name ;
    private String email ;
    private String phoneNumber ;
    private String address;
    private String picture;
   @Column(length = 10000)
    private String description ;

    private boolean favorite =false; // to chek whether the string is favorite or not 
    private String websiteLink;
    private String linkedInLink;
    private String cloudinaryImagePublicId;
    
    // private List<String> socialLinks = new ArrayList<>();
   
    // agar hame contact milgaya  to user nikal sakte hai 
    @ManyToOne 
    @JsonIgnore
    private  User user;
   
      @OneToMany(mappedBy = "contact" ,cascade=CascadeType.ALL,fetch=FetchType.EAGER ,orphanRemoval=true)
    private List<SocialLink> socialLinks = new ArrayList<>();

    
}
