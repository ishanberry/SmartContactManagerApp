package com.Scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Scm.Entity_Models.User;
import com.Scm.Helper.Message;
import com.Scm.Helper.Messagetype;
import com.Scm.Repository.UserRepo;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserRepo userRepo;

    // this is not production grade code for project  purpose only 


    // verify  email 
        @GetMapping("/verify-email")
        public String verifyEmail(@RequestParam("token") String token ,HttpSession session )
        {
            User user= userRepo.findByEmailToken(token).orElse(null);

            if (user!=null)
            {
                //user fetch hua haib : process karna hai 

                if (user.getEmailToken().equals(token))
                    { // db se check kar raha hai 
                    // this condition will check if the usre email token  user token 
                        // if true then update the user email token to null

                        user.setEmailVerified(true);
                        user.setEnabled(true);
                        userRepo.save(user);
                        session.setAttribute("message", Message.builder()
                        .type(Messagetype.green)
                        .content("You email is verified. Now you can login  ")
                        .build());
                       
                        return "sucess_page";



                    }
                    session.setAttribute("message", Message.builder()
                    .type(Messagetype.red)
                    .content("Email not verified ! Token is not associated with user .")
                    .build());
            return "error_page";

                
            }


            session.setAttribute("message", Message.builder()
            .type(Messagetype.red)
            .content("Email not verified ! Token is not associated with user .")
            .build());
                
                return "error_page";
        }


}
