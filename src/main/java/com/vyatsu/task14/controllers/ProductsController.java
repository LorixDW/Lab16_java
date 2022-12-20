package com.vyatsu.task14.controllers;

import com.vyatsu.task14.entities.Filter;
import com.vyatsu.task14.entities.Product;
import com.vyatsu.task14.entities.StrParam;
import com.vyatsu.task14.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductsController {
    private ProductsService productsService;
    private final StrParam filterMessage = new StrParam();
    private final Filter filter = new Filter();
    private final StrParam AddMessage = new StrParam();
    private Pattern pattern = Pattern.compile("");
    private final ArrayList<Long> Isredact = new ArrayList<>();
    private boolean showFilter = false;
    private int curent_page = 0;
    private int page_rows = 5;
    @Autowired
    public void setProductsService(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public String showProductsList(Model model) {
        Product product = new Product();
        Product product1 = new Product();
        StrParam str = new StrParam();
        List<Product> pl = productsService.getFilterProduct(filter);
        List<Integer> pages = new ArrayList<>();
        List<List<Product>> ppl = new ArrayList<>();
        for (int i = 0; i <= pl.size() / page_rows; i++){
            ppl.add(new ArrayList<>());
            pages.add(i);
            if(i == pl.size() / page_rows){
                for(int j = 0; j < pl.size() - i * page_rows; j++){
                    ppl.get(i)
                            .add(pl.get(i * page_rows + j));
                }
                break;
            }
            for (int j = 0; j < page_rows; j++){
                ppl.get(i)
                        .add(pl.get(i * page_rows + j));
            }
        }
        if(ppl.get(ppl.size() - 1).size() == 0)
        {
            pages.remove(ppl.size() - 1);
        }
        model.addAttribute("isRedact", Isredact);
        model.addAttribute("filter", filter);
        model.addAttribute("addMess", AddMessage);
        model.addAttribute("val", str);
        model.addAttribute("products", ppl.get(curent_page));
        model.addAttribute("product", product);
        model.addAttribute("product1", product1);
        model.addAttribute("showFilter", showFilter);
        model.addAttribute("current_page", curent_page);
        model.addAttribute("pages", pages);
        return "products";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "product")Product product) {
        if(productsService.getAllProducts().stream().filter(p -> p.getId() == product.getId()).collect(Collectors.toList()).size() == 0){
            productsService.add(product);
            AddMessage.setValue("");
        }
        else{
            AddMessage.setValue("Указанный id уже занят!!!");
        }
        return "redirect:/products";
    }

    @GetMapping("/show/{id}")
    public String showOneProduct(Model model, @PathVariable(value = "id") Long id) {
        Product product = productsService.getById(id);
        model.addAttribute("product", product);
        return "product-page";
    }
//    @PostMapping("/drop")
    @GetMapping("/drop/{id}")
    public  String dropProduct(Model model, @PathVariable(value = "id") Long id){
        Product product = productsService.getAllProducts().stream()
                .filter(p -> p.getId() == id).findFirst().orElse(null);
        if(product != null) {
            productsService.remove(product);
        }
        return "redirect:/products";
    }
    @PostMapping ("/filter")
    public String FilterList( @ModelAttribute(value = "filter") Filter f){
        pattern = Pattern.compile(f.getPattern());
        filter.setPattern(f.getPattern());
        filter.setPriceF(f.getPriceF());
        filter.setMax(f.getMax());
        filter.setMin(f.getMin());
        curent_page = 0;
        return "redirect:/products";
    }
    @GetMapping("/edit/{id}")
    public  String editProduct(Model model, @PathVariable(value = "id") Long id){
        Isredact.add(id);
        return "redirect:/products";
    }
    @PostMapping("/edit")
    public  String editProduct(@ModelAttribute(value = "product1") Product product){
        productsService.update(product);
        Isredact.remove(product.getId());
        return "redirect:/products";
    }
    @GetMapping("/filter")
    public  String showFilter(Model model){
        showFilter = !showFilter;
        return "redirect:/products";
    }
    @GetMapping("/page/{id}")
    public  String viewPage(Model model, @PathVariable(value = "id") int id){
        curent_page = id;
        return "redirect:/products";
    }
}
