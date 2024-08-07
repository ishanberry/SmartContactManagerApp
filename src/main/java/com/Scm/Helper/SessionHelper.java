package com.Scm.Helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
    public static void removeMessage() {
                      
        try {
            System.out.println("Removing message from session helper ");

            // requestcontextholder me se get attrubutes ek serrequest attruvete me change kiya jisse ham get reuest and we can get session 
        HttpSession session=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("message");
            
        }
         catch (Exception e) {
           System.out.println("error in session helper "+e);
            e.printStackTrace();
      
    }
    }
}
