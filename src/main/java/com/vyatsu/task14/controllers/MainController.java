package com.vyatsu.task14.controllers;

import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private ProductsService productsService;
    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }
    @GetMapping("/logout")
    public String logout(){
        return "index";
    }
    @GetMapping("/login")
    public String login(){
        return "redirect:/products";
    }
    @GetMapping
    public String index(Model model) {
        List<Product> mostSeen = productsService.GetThreeMostSeen();
        model.addAttribute("mostSeen", mostSeen);
        return "index";
    }
//
//    @GetMapping("/secured")
//    public String secured() {
//        return "index";
//    }
//
//    @GetMapping("/form")
//    public String showForm() {
//        return "simple-form";
//    }
//
//    @PostMapping("/form")
//    public String saveForm(@RequestParam(value = "name") String name, @RequestParam(value = "email") String email) {
//        System.out.println(name);
//        System.out.println(email);
//        return "redirect:/index";
//    }
//
//    @GetMapping("/index")
//    public String doSomething() {
//        return "index";
//    }
//
//    @GetMapping("/hello")
//    public String helloRequest(Model model, @RequestParam(value = "name") String name) {
//        model.addAttribute("name", name);
//        return "hello";
//    }

/*
    @GetMapping("/hello/{name}")
    public String helloRequest(Model model, @PathVariable(value = "name") String name) {
        model.addAttribute("name", name);
        return "hello";
    }
*/

//    @GetMapping("/hello")
//    public String helloRequest(Model model) {
//        model.addAttribute("name", "Bob");
//        return "hello";
//    }

//    @GetMapping("/addcat")
//    public String showAddCatForm(Model model) {
//        Cat cat = new Cat(1L, null, null);
//        model.addAttribute("cat", cat);
//        return "cat-form";
//    }
//
//    @PostMapping("/addcat")
//    public String showAddCatForm(@ModelAttribute(value = "cat") Cat cat) {
//        System.out.println(cat.getId() + " " + cat.getName() + " " + cat.getColor());
//        return "redirect:/index";
//    }
}
