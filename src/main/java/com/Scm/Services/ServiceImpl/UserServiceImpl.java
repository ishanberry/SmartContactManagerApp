package com.Scm.Services.ServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Scm.Entity_Models.User;
import com.Scm.Helper.AppConstant;
import com.Scm.Helper.Helper;
import com.Scm.Helper.ResourceNotFoundException;
import com.Scm.Repository.UserRepo;
import com.Scm.Services.EmailService;
import com.Scm.Services.UserService;
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

     @Autowired
    private EmailService emailService;

    
    private Logger logger = LoggerFactory.getLogger(this.getClass());  //? 

    @Override
    public User saveUser(User user) {
        String userId=UUID.randomUUID().toString();
        user.setUserId(userId);
        //password encode 
        
        //use bcrypot encoder 
        //user.setpassword 
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //user setroles
        user.setRoleList(List.of(AppConstant.ROLE_USER));

        logger.info(user.getProvider().toString());

      
        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepo.save(user);
        String emailLink = Helper.getLinkForEmailVerificatiton(emailToken);
       
        emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
        return savedUser;

       


    }

    @Override
    public Optional<User> getUserById(String id) {
      return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
       User  user2= userRepo.findById(user.getUserId()).orElseThrow(()->new ResourceNotFoundException("user not found exception "));
      user2.setName(user.getName());
      user2.setEmail(user.getEmail());
      user2.setPassword(user.getPassword());
      user2.setAbout(user.getAbout());
      user2.setPhoneNumber(user.getPhoneNumber());
      user2.setProfilePic(user.getProfilePic());
      user2.setEnabled(user.isEnabled());
      //user2.setRole(user.getRole());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());

        //save the user in db 
        User save=userRepo.save(user2);
        return Optional.ofNullable(save);

    }

    @Override
    public void deleteUser(String id) {
        User  user2= userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user not found exception "));
        userRepo.delete(user2);
        
    
    }

    @Override
    public boolean isUserExist(String userId) {
        User  user2= userRepo.findById(userId).orElse(null);
        return user2!=null ? true:false; // ternary operator  

    }

    @Override
    public boolean isUserExistByEmail(String email) {
        User user = userRepo.findByEmail(email).orElse(null);
        return user!=null ? true:false; // why he ahs not taken EMail here in user check this 


    }

    @Override
    public List<User> getAllUser() {
     return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);

    }
}
