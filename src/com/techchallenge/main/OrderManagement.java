package com.techchallenge.main;

import java.util.List;

import com.techchallenge.bean.InventorySource;
import com.techchallenge.bean.InventoryTarget;
import com.techchallenge.service.FulfillmentService;
import com.techchallenge.service.impl.FulfillmentServiceImpl;
import com.techchallenge.util.InventorySourceFileReader;
import com.techchallenge.util.InventoryTargetFileReader;

public class OrderManagement {

	public static void main(String[] args) {
		
		List<InventorySource> sourceList =  InventorySourceFileReader.parseFile();
		List<InventoryTarget> targetList =  InventoryTargetFileReader.parseFile();
		
		System.out.println(sourceList.size() + targetList.size());
		
		FulfillmentService sf = new FulfillmentServiceImpl();
		try {
			sf.getFulfillment(sourceList, targetList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
