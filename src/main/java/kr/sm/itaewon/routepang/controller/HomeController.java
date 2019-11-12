package kr.sm.itaewon.routepang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @RequestMapping("/home")
    public String home(Model model){
        System.out.println("home req");
        model.addAttribute("msg", "hello world");
        return "home";
    }



}
