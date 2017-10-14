package com.techchallenge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderManagementController {
	
	@RequestMapping(value ={"/"})
	public ModelAndView adminLogin() {

		ModelAndView model = new ModelAndView();
		model.setViewName("ordermanagement");

		return model;

	}
}
