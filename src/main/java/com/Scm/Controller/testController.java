package com.Scm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
public class testController {

    @RequestMapping(value = "/index")
    public String home(Model model){
        System.out.println("this is home page test ");
        model.addAttribute("youtubechannel", "coding ishan");
        model.addAttribute("portfoliowebsite  ", "https://ishandevportfolio.vercel.app/");
        
        
        return "index";
    }
}
