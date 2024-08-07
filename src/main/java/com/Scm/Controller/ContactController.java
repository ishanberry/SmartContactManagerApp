package com.Scm.Controller;

import java.util.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.Scm.Entity_Models.Contact;
import com.Scm.Entity_Models.User;
import com.Scm.Helper.AppConstant;
import com.Scm.Helper.Helper;
import com.Scm.Helper.Message;
import com.Scm.Helper.Messagetype;
import com.Scm.Services.ContactService;
import com.Scm.Services.ImageService;
import com.Scm.Services.UserService;
import com.forms.ContactForms;
import com.forms.ContactSearchForm;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;
    // @RequestMapping("/add")
    // // add contact page: handler
    // public String addContactView(Model model) {
    //     ContactForms contactForm = new ContactForms();

    //     contactForm.setFavorite(true);
    //     model.addAttribute("contactForm", contactForm);
    //     return "user/add_contact";
    // }
    @RequestMapping("/add")
    // add contact page: handler
    public String addContactView(Model model) {
        ContactForms contactForm = new ContactForms();

        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }




    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact( @Valid @ModelAttribute("contactForm") ContactForms contactForms, BindingResult result,
    Authentication authentication ,HttpSession session
    ) {

       

         // process the form data

        // 1 validate form

        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(Messagetype.red)
                    .build());
                    return "user/add_contact";
                
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        //from --contact
            
        User user=userService.getUserByEmail(username);
    // image process

        // uplod karne ka code

  String filename = UUID.randomUUID().toString();

  String fileURL = imageService.uploadImage(contactForms.getContactImage(), filename);

  
            logger.info("file information {}",contactForms.getContactImage().getOriginalFilename());
            Contact contact = new Contact();
        contact.setName(contactForms.getName());
        contact.setFavorite(contactForms.isFavorite());
        contact.setEmail(contactForms.getEmail());
        contact.setPhoneNumber(contactForms.getPhoneNumber());
        contact.setAddress(contactForms.getAddress());
        contact.setDescription(contactForms.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForms.getLindeinLink());
        contact.setWebsiteLink(contactForms.getWebsiteLink());
        contact.setPicture(fileURL);
        contact.setCloudinaryImagePublicId(filename);
        contactService.save(contact);
        System.out.println(contactForms);
         // 3 set picuture url 

       
        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(Messagetype.green)
                        .build());

        
        // contact.setPicture(fileURL);
        // contact.setCloudinaryImagePublicId(filename);

      


       

        // process the form data 
       



     return "redirect:/user/contacts/add";

    }

// view contacts
@RequestMapping
public String viewContacts(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
        @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
        Authentication authentication) {

    // load all the user contacts
    String username = Helper.getEmailOfLoggedInUser(authentication);

    User user = userService.getUserByEmail(username);

    Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
            
    model.addAttribute("pageContact", pageContact);
    model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

    model.addAttribute("contactSearchForm", new ContactSearchForm());

    return "user/contacts";
}


 // search handler

 @RequestMapping("/search")
 public String searchHandler(

          @ModelAttribute ContactSearchForm contactSearchForm,
         @RequestParam(value = "size", defaultValue = AppConstant.PAGE_SIZE + "") int size,
         @RequestParam(value = "page", defaultValue = "0") int page,
         @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
         @RequestParam(value = "direction", defaultValue = "asc") String direction,
         Model model,
         Authentication authentication) {

     logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

     var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

     Page<Contact> pageContact = null;
     if (contactSearchForm.getField().equalsIgnoreCase("name")) {
         pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                 user);
     } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
         pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                 user);
     } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
         pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                 direction, user);
     }

     logger.info("pageContact {}", pageContact);

     model.addAttribute("contactSearchForm", contactSearchForm);

     model.addAttribute("pageContact", pageContact);

     model.addAttribute("pageSize", AppConstant.PAGE_SIZE);

     return "user/search";
 }

// delete controller

// delete contact 

// detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
        Message.builder()
                .content("Contact is Deleted successfully !! ")
                .type(Messagetype.green)
                .build()

);


        return "redirect:/user/contacts";
    }

   // update contact form view
   @GetMapping("/view/{contactId}")
   public String updateContactFormView(
           @PathVariable("contactId") String contactId,
           Model model) {

       var contact = contactService.getById(contactId);
       ContactForms contactForm = new ContactForms();
       contactForm.setName(contact.getName());
       contactForm.setEmail(contact.getEmail());
       contactForm.setPhoneNumber(contact.getPhoneNumber());
       contactForm.setAddress(contact.getAddress());
       contactForm.setDescription(contact.getDescription());
       contactForm.setFavorite(contact.isFavorite());
       contactForm.setWebsiteLink(contact.getWebsiteLink());
       contactForm.setLindeinLink(contact.getLinkedInLink());
       contactForm.setPicture(contact.getPicture());
       ;
       model.addAttribute("contactForm", contactForm);
       model.addAttribute("contactId", contactId);

       return "user/update_contact_view";
   }

            @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
            public String updateContact(@PathVariable("contactId") String contactId,
                    @Valid @ModelAttribute("contactForm") ContactForms contactForm,
                    BindingResult bindingResult,
                    Model model) {
                        // getting the old conatct and update it 
                        
                        
                        if (bindingResult.hasErrors()) {
                            return "user/update_contact_view";
                                }
                        

                var con = contactService.getById(contactId);
                con.setId(contactId);
                con.setName(contactForm.getName());
                con.setEmail(contactForm.getEmail());
                con.setPhoneNumber(contactForm.getPhoneNumber());
                con.setAddress(contactForm.getAddress());
                con.setDescription(contactForm.getDescription());
                con.setFavorite(contactForm.isFavorite());
                con.setWebsiteLink(contactForm.getWebsiteLink());
                con.setLinkedInLink(contactForm.getLindeinLink());
        
                // process image:
        
                if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
                    logger.info("file is not empty");
                    String fileName = UUID.randomUUID().toString();
                    String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
                    con.setCloudinaryImagePublicId(fileName);
                    con.setPicture(imageUrl);
                    contactForm.setPicture(imageUrl);
        
                } else {
                    logger.info("file is empty");
                }
        
                var updateCon = contactService.update(con);
                logger.info("updated contact {}", updateCon);
        
                model.addAttribute("message", Message.builder().content("Contact Updated !!").type(Messagetype.green).build());
        
                return "redirect:/user/contacts/view/" + contactId;
            }
        

 


}