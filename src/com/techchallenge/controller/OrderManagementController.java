package com.techchallenge.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.techchallenge.bean.FulfillmentResult;
import com.techchallenge.bean.InventorySource;
import com.techchallenge.bean.InventoryTarget;
import com.techchallenge.service.FulfillmentService;
import com.techchallenge.service.impl.FulfillmentServiceImpl;
import com.techchallenge.util.InventorySourceFileReader;
import com.techchallenge.util.InventoryTargetFileReader;

@Controller
public class OrderManagementController {
	
	@RequestMapping(value ="/",method=RequestMethod.GET)
	public ModelAndView adminLogin() {

		ModelAndView model = new ModelAndView();
		model.setViewName("ordermanagement");

		return model;

	}
	@RequestMapping(value ="/",method=RequestMethod.POST)
	public ModelAndView fulfillOrder(@RequestParam("sourcefile") MultipartFile sourcefile,
			@RequestParam("targetfile") MultipartFile targetfile) {

		List<InventorySource> sourceList =  InventorySourceFileReader.parseFile(sourcefile);
		List<InventoryTarget> targetList =  InventoryTargetFileReader.parseFile(targetfile);
		FulfillmentService sf = new FulfillmentServiceImpl();
		List<FulfillmentResult> result = new ArrayList<FulfillmentResult>();
		try {
			result = sf.getFulfillment(sourceList, targetList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(FulfillmentResult r :result){
			System.out.println(r.getLocation()+r.getDestination()+r.getSku()+r.getAmount());
		}
		
		ModelAndView model = new ModelAndView();
		model.setViewName("fulfillmentresult");
		model.addObject("result", result);

		return model;

	}
	
}
