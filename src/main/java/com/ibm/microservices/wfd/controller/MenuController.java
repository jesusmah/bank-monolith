package com.ibm.microservices.wfd.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.lang.Object;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.boot.json.BasicJsonParser;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Controller
public class MenuController {

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("/")
  @HystrixCommand(fallbackMethod="getDefaultMenu", groupKey="WhatsForDinnerUI", commandKey="GetMenuForUI")
  public String getMenuForIndex(Model model){

    String menuString = this.restTemplate.getForObject("http://menu-service/menu", String.class);
    System.out.println(menuString);

    BasicJsonParser jsonParser = new BasicJsonParser();
    Map<String, Object> jsonMap = jsonParser.parseMap(menuString);

    List<String> appetizerOptions = (List<String>)((Map<String, Object>)jsonMap.get("appetizers")).get("menu");
    model.addAttribute("appetizerOptions", appetizerOptions);

    List<String> entreeOptions = (List<String>)((Map<String, Object>)jsonMap.get("entrees")).get("menu");
    model.addAttribute("entreeOptions", entreeOptions);

    List<String> dessertOptions = (List<String>)((Map<String, Object>)jsonMap.get("desserts")).get("menu");
    model.addAttribute("dessertOptions", dessertOptions);

    return "index";
  }

  public String getDefaultMenu(Model model){

    List<String> appetizerList = new ArrayList<String>();
                 appetizerList.add("Chips");
                 appetizerList.add("Salsa");
                 appetizerList.add("Bruschetta");

    model.addAttribute("appetizerOptions", appetizerList);

    List<String> entreeList = new ArrayList<String>();
                 entreeList.add("Hamburger");
                 entreeList.add("Hot Dog");
                 entreeList.add("Spaghetti");

    model.addAttribute("entreeOptions", entreeList);

    List<String> dessertList = new ArrayList<String>();
                 dessertList.add("Cookies");
                 dessertList.add("Candy");
                 dessertList.add("Cake");

    model.addAttribute("dessertOptions", dessertList);

    return "index";
  }

}
