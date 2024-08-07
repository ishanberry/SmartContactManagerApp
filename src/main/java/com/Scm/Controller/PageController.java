package com.Scm.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Scm.Entity_Models.User;
import com.Scm.Entity_Models.User.UserBuilder;
import com.Scm.Helper.Message;
import com.Scm.Helper.Messagetype;
import com.Scm.Services.UserService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.forms.UserForms;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class PageController {

    private static final String MessageType = null;
    @Autowired
    UserService  userService ;

     @GetMapping("/")
    public String index() {
        return "redirect:/home";   // redirect to home route or request handler mapping 
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        // sending data to view
        System.out.println("this is home page test ");
        model.addAttribute("youtubechannel", "coding ishan");
        model.addAttribute("portfoliowebsite  ", "https://ishandevportfolio.vercel.app/");
        return "home";
    }

    // about route

    @RequestMapping("/about")
    public String aboutPage(Model model) {
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "about";
    }

    // services

    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("services page loading");
        return "services";
    }
    
     // contact page

     @GetMapping("/contact")
     public String contact() {
        System.out.println("contact handler working ");
         return "contact";
     }

    // this is showing login page
    @GetMapping("/login")
     public String login() {
         return "login";
     }
 

         // registration page
     @GetMapping("/register")
     public String register(Model model) 
     {
        System.out.println("register handler working ");
        //  for handling the register page content 
        // thorugh moidel attribute we can send the manullay data from controller to user 
        UserForms userForm = new UserForms();
        // userforms.setName("ishan");
        // userforms.setAbout("this is about ");
         model.addAttribute("userForm", userForm);
         return "register";
     }

     // processing register form 

     @PostMapping("/do-register")
    public String processRegister(@Valid  @ModelAttribute("userForm") UserForms userForms ,BindingResult rBindingResult
    
    
    ,HttpSession httpSession) // @model atribuete check and revise 
            { 
        System.out.println("processing register form ");
        System.out.println(userForms);

        //fetch data
        //valideate 

        if (rBindingResult.hasErrors()) {
            return "register";
        }




        // to do for the project 
        //save 
        // message =register sucessfully
        //redirect to login page 
          
        // User user = User.builder()
        // .name(userForms.getName())
        // .email(userForms.getEmail())
        // .password(userForms.getPassword())
        // .about(userForms.getAbout())
        // .phoneNumber(userForms.getPhoneNumber())
        // .profilePic(
        // "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75")
        // .build();

        // no hard code data 

        // use default construcotr to use deafult values 
        User user = new User();
        user.setName(userForms.getName());
        user.setEmail(userForms.getEmail());
        user.setPassword(userForms.getPassword());
        user.setAbout(userForms.getAbout());
        user.setPhoneNumber(userForms.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://as2.ftcdn.net/v2/jpg/00/87/28/19/1000_F_87281963_29bnkFXa6RQnJYWeRfrSpieagNxw1Rru.jpg");
        


        User savedUser = userService.saveUser(user);
        System.out.println(" user saved "+ " "+savedUser);

        // message for the session we are using but we can use other technique  //resposne 
        Message message = Message.builder().content("Registration Successful").type(Messagetype.green).build();
        httpSession.setAttribute("message",message);




        return "redirect:/register";
    }


}
