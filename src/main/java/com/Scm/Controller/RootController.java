package com.Scm.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Scm.Entity_Models.User;
import com.Scm.Helper.Helper;
import com.Scm.Services.UserService;

@ControllerAdvice
public class RootController {
     // check for this annotation 

      @Autowired
    private UserService userService;
    
   
    private Logger logger  = LoggerFactory.getLogger(RootController.class);
    
    @ModelAttribute
public  void addLoggedInUserInformation(Model  model, Authentication authentication)
{   
    // this is common controller which will give informsation regarding the loggedinuser 
    //database se data ko fetch 
    //get the user from  db 
    if (authentication==null)
    {
    return ;
    }
    // is user is not authenticated  koi user hai authenttication me 

    System.out.println("adding the loggein user information to the model ");
    String username = Helper.getEmailOfLoggedInUser(authentication);

    logger.info("user logged in: {}",username);

    User user=userService.getUserByEmail(username);
       
           System.out.println(user);

            System.out.println(user.getName());
            System.out.println(user.getEmail());
            model.addAttribute("loggedInUser", user);
        
        
   
}





}
