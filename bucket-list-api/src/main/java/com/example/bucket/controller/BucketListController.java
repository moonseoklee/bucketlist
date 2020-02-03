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
        modelAndView.addObject("bucketList",listService.list(user.getEmail()));
        modelAndView.setViewName("dashboard");



        return modelAndView;
    }

    @GetMapping("/dashboard/{listId}")
    public ModelAndView bucket(@PathVariable("listId") int id){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = userDetailsService.findUserByEmail(auth.getName());

        Bucket bucket = listService.findBucket(listService.list(user.getEmail()),id);
        modelAndView.addObject("bucket", bucket);
        modelAndView.setViewName("detail");

        return modelAndView;
}
    @RequestMapping(value = "/list", method = {RequestMethod.POST,RequestMethod.GET})
    public String create(Bucket resource) throws URISyntaxException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Bucket bucket = Bucket.builder().user(auth.getName()).idx(resource.getIdx()).title(resource.getTitle()).detail(resource.getDetail()).complete(false).build();

        listService.addBucket(bucket);

       // URI location = new URI("/list/"+resource.getIdx());

        return "redirect:/dashboard";
    }

    @GetMapping("dashboard/check/{id}")
    public String check(@PathVariable("id") int id){
        
        listService.checkBucket(id);

        return "redirect:/dashboard";
    }
}
