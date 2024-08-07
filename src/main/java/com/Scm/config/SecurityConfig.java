package com.Scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.Scm.Services.ServiceImpl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    // // user create  and login java code with inmemory  service 
    // @Bean
    // public UserDetailsService userDetailsService()
    // {   

    //      UserDetails user1 = User.withDefaultPasswordEncoder()
    //      .username("admin123")

    //      .password("admin123")
    //     //  .roles("ADMIN","USER")
    //      .build();

    //      UserDetails user2 = User.withDefaultPasswordEncoder()
    //      .username("user123")
    //      .password("1234")
    //     //  .roles("admin","user")
    //      .build();
    //      //no password encoder



    //     var inMemoryDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    //     return inMemoryDetailsManager;
    // }


    /// Doa authentication provider 
    // @Autowired
    // BCryptPasswordEncoder bCryptPasswordEncoder;
// check  this 
  
 // USE  AUTOWIRED OR USE CONSTRUCTOR INJECTION 

@Autowired 
    private SecurityCustomUserDetailService securityCustomUserDetailService;
 
    @Autowired 
    private OAuthAuthenticationSucessHandler handler;

    @Autowired 
    private AuthFailtureHandler authFailtureHandler;

    // configuration of authentication provider 
    @Bean
    public AuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            // user deytails service ka object 
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
            // pasword encoder ka obj 
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
    {
            // configyuratrion
             // allow the request to filter on some set of roles 
            // url or routes ko secure kiya hai which one will be private  and public 
             httpSecurity.authorizeHttpRequests(authorize-> {
                // authorize.requestMatchers("/home","/register","/services","/do-register").permitAll();
                authorize.requestMatchers("/user/**").authenticated();
                authorize.anyRequest().permitAll();
            });
                // from default login 
                //agar kuch change kjarna  hoga 
            //lambda 
            httpSecurity.formLogin(formLogin->
            {           // login processs meand konsa page login ke submit ke baad chalega 
                    formLogin.loginPage("/login"); // default login page 
                    formLogin.loginProcessingUrl("/authenticate");
                    formLogin.successForwardUrl("/user/profile");
                    // formLogin.failureForwardUrl("/login?error=true");
                    // formLogin.defaultSuccessUrl("/home");
                    formLogin.usernameParameter("email");
                    formLogin.passwordParameter("password");
            //     formLogin.failureHandler(new AuthenticationFailureHandler() {

            // @Override
            // public void onAuthenticationFailure(HttpServletRequest request,
            // HttpServletResponse response,
            // AuthenticationException exception) throws IOException, ServletException {
            // // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method"
            // 'onAuthenticationFailure'");

            formLogin.failureHandler(authFailtureHandler);
            // }
            });

            //byedeafult crsf enable  but make it enable as it a good pratise 
  httpSecurity.csrf(AbstractHttpConfigurer::disable);
          

            // oauth configuration 
            httpSecurity.oauth2Login(oauth->{
                oauth.loginPage("/login");
                oauth.successHandler(handler);
            });
    
            httpSecurity.logout(logoutForm -> {
                logoutForm.logoutUrl("/do-logout");
                logoutForm.logoutSuccessUrl("/login?logout=true");
            });


        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}



