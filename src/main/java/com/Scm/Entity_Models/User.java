package com.Scm.Entity_Models;



import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// you can use @DATA --getter and setter 

@Entity(name = "user")
@Table(name = "users")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class User implements UserDetails{

 @Id
//@GeneratedValue(strategy = GenerationType.IDENTITY)// unique  private Long userId;

private String userId;

@Column(name = "user_name",nullable = false)

private String name ;

@Column(unique = true ,nullable=false)
private String email;



private String password;


@Column(length = 1000)
private String about;

@Column(length = 1000)
private String profilePic;

//information 

// user enabled or not 
 @Column(unique = true )
private String phoneNumber;


// user enabled or not  in page 
private boolean enabled=false; 
//batega ki yeh user enabled hai nehi abb spirng secuirty track karega 
private boolean emailVerified=false; 

private boolean phoneVerified=false; 

// user has Signup means ham provider rakenege  like normal way register and google auth ,fb ,twitter,linkdein,github
@Enumerated(value=EnumType.STRING)
private Providers provider=Providers.SELF; // ENUMS  name constant datatype we wnat to declare 

private String providerUserId;
// add more field needed 

 // MAPPING OF THE USERS TO CONTACT  means ek users ke paas mulitple contacts 
// biredectional mapping 
 @OneToMany(mappedBy = "user" ,cascade=CascadeType.ALL,fetch=FetchType.LAZY ,orphanRemoval=true)
     private  List<Contact> contactList = new ArrayList<>();

@ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

  

    
private String emailToken;


 @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // list of roles[USER,ADMIN]
        // Collection of SimpGrantedAuthority[roles{ADMIN,USER}]
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }

    
 // for this project:
    // email id hai wahi hamare username@Override
public String getUsername() {
   return this.email;
}

@Override
    public boolean isAccountNonExpired() {
        return true;
        // non  expired field can be mapped also
    }
    

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }


    @Override
    public String getPassword() {
        return this.password;
    }
}
