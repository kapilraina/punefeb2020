package com.ms.bootcamp.productmicroservice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {
	
	@RequestMapping("/minion/{minionname}")
	public String getMinion(Model model, @PathVariable(name = "minionname") String minionname) {
		//log.debug("web-request :" + minionname);
		model.addAttribute("minion", minionname);
		return "minion";
	}

}
