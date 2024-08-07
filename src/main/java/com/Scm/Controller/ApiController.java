package com.Scm.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Scm.Entity_Models.Contact;
import com.Scm.Services.ContactService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api")

public class ApiController {
    
  @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{contactId}")
    public Contact getContact(@PathVariable String contactId)
{

    return contactService.getById(contactId);
}


}
