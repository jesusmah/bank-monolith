package com.ibm.caseteam.monolith.bank.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Object;

import com.ibm.caseteam.monolith.bank.services.ProductService;
import com.ibm.caseteam.monolith.bank.model.Product;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.boot.json.BasicJsonParser;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class BankController {

  private static final Log log = LogFactory.getLog(BankController.class);

  private ProductService productService;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  public void setProductService(ProductService productService) {
    this.productService = productService;
  }

  @RequestMapping("/")
  public String getBankIndex(Model model){
    System.out.println("Returning index page");
    return "index";
  }
  @RequestMapping("/products")
  public String getBankProducts(Model model){

    Iterable<Product> productList = productService.listAllProducts();
    model.addAttribute("products", productService.listAllProducts());
    for(Product s : productList){
      log.info(s.toString());
    }
    //log.info(String.join(";",productList));
    System.out.println("Returning products:");
    return "products";
  }

}
