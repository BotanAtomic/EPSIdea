package org.epsi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    @RequestMapping("/louis/")
    public String refresh(Model model) {
        model.addAttribute("name","LOUIS");
        model.addAttribute("content","ALLAH AKBAR");
        return "index";
    }

}
