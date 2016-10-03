package com.ibm.microservices.wfd;

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

@Controller
public class MenuController {

  @Autowired
  private RestTemplate restTemplate;

  @RequestMapping("/")
  public String getMenuForIndex(Model model){

    String menuString = getMenu();

	BasicJsonParser jsonParser = new BasicJsonParser();
	Map<String, Object> jsonMap = jsonParser.parseMap(menuString);
	
	List<String> appetizerOptions = (List<String>)jsonMap.get("appetizers.menu");
	model.addAttribute("appetizerOptions", appetizerOptions);
	
	List<String> entreeOptions = (List<String>)jsonMap.get("entrees.menu");
	model.addAttribute("entreeOptions", entreeOptions);
	
	List<String> dessertOptions = (List<String>)jsonMap.get("desserts.menu");
	model.addAttribute("dessertOptions", dessertOptions);
  
/*	
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
*/
    return "index";
  }

  private String getMenu() {
    String menuString = this.restTemplate.getForObject("http://menu-service/menu", String.class);
    System.out.println(menuString);
    return menuString;
  }

}
