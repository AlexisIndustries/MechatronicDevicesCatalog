package org.example.mechatronic.controller;

import lombok.AllArgsConstructor;
import org.example.mechatronic.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class BasicController {
    CategoryRepository repository;
    @GetMapping("/")
    public String index(){
        return "redirect:/categories";
    }

}
