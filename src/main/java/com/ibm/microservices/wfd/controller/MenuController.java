package com.ibm.microservices.wfd.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class MenuController {

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("/")
  public String getMenuForIndex(Model model){

    //TODO Update to return value for Object of some sort
    String menuString = getMenu();

    //Extract Appetizer Options
    List<String> appetizerOptions = new ArrayList<String>();
    appetizerOptions.add("Cake");
    appetizerOptions.add("Brownies");
    appetizerOptions.add("Champagne");
    //TODO Replace with options from service call response
    model.addAttribute("appetizerOptions", appetizerOptions);

    //Extract Entree Options
    List<String> entreeOptions = new ArrayList<String>();
    //TODO Apply options from service call response
    model.addAttribute("entreeOptions", entreeOptions);

    //Extract Dessert Options'
    List<String> dessertOptions = new ArrayList<String>();
    //TODO Apply options from service call response
    model.addAttribute("dessertOptions", dessertOptions);

    return "index";
  }

  private String getMenu() {
    String menuString = this.restTemplate.getForObject("http://menu-service/menu", String.class);
    System.out.println(menuString);

    /*
    menuString is in the format of

    {
      "entrees": {
        "order": 2,
        "menu": [
          "Steak",
          "Chicken",
          "Fish"
        ],
        "type": "dinner"
      },
      "desserts": {
        "order": 3,
        "menu": [
          "Chocolate Cake",
          "Banoffee",
          "Ice Cream"
        ],
        "type": "dessert"
      },
      "appetizers": {
        "order": 1,
        "menu": [
          "Hummus",
          "Crab Cakes",
          "Mozzerella Sticks"
        ],
        "type": "appetizer"
      }
    }

    */

    //Parse menuString JSON and return as Object

    return menuString;
  }

}
