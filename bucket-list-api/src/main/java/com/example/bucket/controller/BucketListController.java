package com.example.bucket.controller;


import com.example.bucket.domain.Bucket;
import com.example.bucket.service.ListService;

import com.example.login.domain.Users;
import com.example.login.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.net.URISyntaxException;

@Controller
public class BucketListController {

    @Autowired
    private ListService listService;
    @Autowired
    private CustomUserDetailsService userDetailsService;


    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userDetailsService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome ");
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");

        System.out.println(listService.list(user.getEmail()));

        return modelAndView;
    }

    @GetMapping("/list/{listId}")
    public Bucket bucket(@PathVariable("listId") int id){

        Bucket bucket = listService.findBucket(id);

        return bucket;
    }

    @PostMapping(path="/list")
    public String create(Bucket resource) throws URISyntaxException {
        Bucket bucket = Bucket.builder().idx(resource.getIdx()).title(resource.getTitle()).detail(resource.getDetail()).complete(false).build();

        listService.addBucket(bucket);

       // URI location = new URI("/list/"+resource.getIdx());

        return "redirect:/list";
    }

    @GetMapping("list/check/{id}")
    public String check(@PathVariable("id") int id){
        
        listService.checkBucket(id);

        return "/list";
    }
}
