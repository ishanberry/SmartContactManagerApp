package com.Scm.Helper;

import java.security.Principal;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    
public static String getEmailOfLoggedInUser(Authentication authentication)
{
    // logic for 
    // agarr email in [password ] se login kiya hai to email kaise nikelenge 
    
    if (authentication instanceof OAuth2AuthenticationToken)
    
    {    var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            
            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

        // agar principal obj belong to oauth2aiuthentcation obj 
        // sign with google
        if (clientId.equalsIgnoreCase("google")) {

            // sign with google
            System.out.println("Getting email from google");
            username = oauth2User.getAttribute("email").toString();

        
        }
         // gogoglr hai to uska email id ka logic nekalencka tairka 
         else if(clientId.equalsIgnoreCase("github"))
         {
     
              // sign with github
              System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                        : oauth2User.getAttribute("login").toString() + "@gmail.com";

             
         }
         //sign in with facebook 
       return username;
       

    
}

else{
    System.out.println("Getting data from local database");
    return authentication.getName();
}



}
public static String getLinkForEmailVerificatiton(String emailToken) {

    String link = "https://smartcontactmanagerapp-production.up.railway.app/auth/verify-email?token=" + emailToken;

    return link;

}

}
 // sign with facebook

