package com.n3o.recipeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping({"","/","/index","index.html"})
    public String getIndexPage(){
        System.out.println("1234");
        System.out.println("xyz lmn");
        return "index";
    }
}
