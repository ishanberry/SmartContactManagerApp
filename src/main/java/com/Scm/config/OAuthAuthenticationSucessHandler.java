package com.Scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.Scm.Entity_Models.Providers;
import com.Scm.Entity_Models.User;
import com.Scm.Helper.AppConstant;
import com.Scm.Repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSucessHandler  implements AuthenticationSuccessHandler{

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSucessHandler.class);

    @Autowired
    
    private UserRepo userRepo;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
         
    //     logger.info("onAuthenticationSuccess: User authenticated");
    //     // response.sendRedirect("/user/profile");
    logger.info("OAuthAuthenicationSuccessHandler");

                     // identify the provider

                     var oauth2AuthenicationToken=(OAuth2AuthenticationToken)authentication;

                    String authorizedClientRegisterationId=oauth2AuthenicationToken.getAuthorizedClientRegistrationId();
                    // here we are getting the preovider id we use for many purpose 
                    logger.info(authorizedClientRegisterationId);

                       var oauthuser=(DefaultOAuth2User) authentication.getPrincipal();
                       oauthuser.getAttributes().forEach((key, value) -> {
                        logger.info(key + " : " + value);
                    });

                    User user = new User();
                    user.setUserId(UUID.randomUUID().toString());
                    user.setRoleList(List.of(AppConstant.ROLE_USER));
                    user.setEmailVerified(true);
                    user.setEnabled(true);
                    user.setPassword("dummy");


                    if (authorizedClientRegisterationId.equalsIgnoreCase("google")) {

                        // google
                        // google attributes
            user.setEmail(oauthuser.getAttribute("email").toString());
            user.setProfilePic(oauthuser.getAttribute("picture").toString());
            user.setName(oauthuser.getAttribute("name").toString());
            user.setProviderUserId(oauthuser.getName());
            user.setProvider(Providers.GOOGLE);
            user.setAbout("This account is created using google.");




                     
            
                    }
                    else if(authorizedClientRegisterationId.equalsIgnoreCase("github")){

                // github
                // github attribute
            String email = oauthuser.getAttribute("email") != null ? oauthuser.getAttribute("email").toString()
               :oauthuser.getAttribute("login").toString() + "@gmail.com";
        String picture = oauthuser.getAttribute("avatar_url").toString();
        String name = oauthuser.getAttribute("login").toString();
        String providerUserId = oauthuser.getName();

        user.setEmail(email);
        user.setProfilePic(picture);
        user.setName(name);
        user.setProviderUserId(providerUserId);
        user.setProvider(Providers.GITHUB);
        user.setAbout("This account is created using github");





                    }
                    else if(authorizedClientRegisterationId.equalsIgnoreCase("linkdein"))
                    {

                    }
                    else{
                        logger.info("OAuthAuthenicationSuccessHandler: Unknown provider");
                    }
                 // save the user
        // facebook
        // facebook attributes
        // linkedin






    

        //  DefaultOAuth2User user= (DefaultOAuth2User)authentication.getPrincipal();

        //     logger.info(user.getName());
        //     user.getAttributes().forEach((key, value) -> {
        //         logger.info("{}=>{}",key,value);
        //     });
        //     logger.info(user.getAuthorities().toString());
        //     //    //  data datavbse save 
        //     String email = user.getAttribute("email").toString();
        //   String name = user.getAttribute("name").toString();
        //  String picture = user.getAttribute("picture").toString();
        //      // create user and save in database
        
        //   User user1 = new User();
        //  user1.setEmail(email);
        //  user1.setName(name);
        //  user1.setProfilePic(picture);
        //  user1.setPassword("password");
        //  user1.setUserId(UUID.randomUUID().toString());
        //  user1.setProvider(Providers.GOOGLE);
        //  user1.setEnabled(true);
          
        //   user1.setEmailVerified(true);
        //   user1.setProviderUserId(user.getName());
        //   user1.setRoleList(List.of(AppConstant.ROLE_USER));
        //   user1.setAbout("This account is created using google..");
          
        //   User user2 = userRepo.findByEmail(email).orElse(null);
        //   if (user2 == null) {
          
        //  userRepo.save(user1);
        //  logger.info("User saved:" + email);
        //  }

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);
        if (user2 == null) {
            userRepo.save(user);
            System.out.println("user saved:" + user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
    
}
