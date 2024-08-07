package com.Scm.Services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Scm.Entity_Models.Contact;
import com.Scm.Entity_Models.User;


public interface ContactService {

    //save conctact

    Contact save(Contact contact);
    

    // update contact
    Contact update(Contact contact);

    //get contact 
    List<Contact> getAll();
    //get conctact by id 
    Contact getById(String id);


    //delete contact
    void delete(String id);

  
    // search contact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);
    //get contact by userid 
    List<Contact> getByUserId(String userId);

    Page<Contact> getByUser(User user ,int Page,int size,String sortField ,String sortDirection);

}
