package com.techchallenge.service;

import java.util.List;

import com.techchallenge.bean.FulfillmentResult;
import com.techchallenge.bean.InventorySource;
import com.techchallenge.bean.InventoryTarget;

public interface FulfillmentService {
	
	public List<FulfillmentResult> getFulfillment(List<InventorySource> sourceList, List<InventoryTarget> targetList) throws Exception;

}
