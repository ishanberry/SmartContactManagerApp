package com.Scm.Controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Scm.Entity_Models.User;
import com.Scm.Helper.Helper;
import com.Scm.Services.UserService;


@Controller
@RequestMapping("/user")
// protected route 
public class UserController {
    

    private UserService userService;
    
    private Logger logger  = LoggerFactory.getLogger(UserController.class);
   
    // user dahsboard 

@RequestMapping("/dashboard")
public String userDashboard() {
    System.out.println("user dashboard controller working  ");
    return "user/dashboard";
}

// USERPORFILE
@RequestMapping("/profile")
public String userProfile(Model model,Authentication authentication) {
    
   
     

    
    System.out.println("user profile controller working  ");
   
   
    return "user/userProfile";
}



//user add contact page 


// user view contact


//user can delete






}

