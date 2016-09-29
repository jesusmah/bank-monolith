package com.ibm.microservices.wfd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
// import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@Controller
// @EnableDiscoveryClient
public class MenuController {

    @RequestMapping("/menu")
    public String menu(@RequestParam(value="name", required=false, defaultValue="menu") String name, Model model) {
       //  model.addAttribute("name", name);
		
		String menuString = getMenu();
		System.out.println(menuString);
		// menuToHtml(menuString);
		
        return menuString;
    }

	String getMenu() {
		
		RestTemplate restTemplate = new RestTemplate();
		
		// private List<String> menuJson = new ArrayList<String>();
		String menuString = restTemplate.getForObject("http://menu-service/menu", String.class);
		
		return menuString;
			
	}
	
}
