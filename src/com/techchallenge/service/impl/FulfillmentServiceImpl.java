package com.techchallenge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.techchallenge.bean.FulfillmentResult;
import com.techchallenge.bean.InventorySource;
import com.techchallenge.bean.InventoryTarget;
import com.techchallenge.service.FulfillmentService;

public class FulfillmentServiceImpl implements FulfillmentService {

	List<InventorySource> sourceList = null;
	List<InventoryTarget> targetList = null;
	Map<String,Integer[]> sourceSkuDestination = null;
	Map<String,Integer> fulfillmentMap = null;
	Map<String,Integer> productAvailability = null;
	Map<String,String> mappedPairs = null;
	Map<String,Integer> sourceDestinationToBeFulfilled = null;
	boolean processActive = false;
	@Override
	public List<FulfillmentResult> getFulfillment(
			List<InventorySource> sList, List<InventoryTarget> tList) throws Exception {
		processActive = true;
		fulfillmentMap = new HashMap<String,Integer>();
		mappedPairs = new HashMap<String,String>();
		sourceList = sList;
		targetList = tList;
		refreshAvailability();
		refreshAllPairs();
		refreshSourceDestinationToBeFulfilled();

		findSingleSource();
		boolean it=true;
		while(it && targetList.size()>0){
			it = findMustFulfillSource();
		}
		while(processActive){
			boolean et=true;
			while(et && targetList.size()>0){
				et = fulfillMappedPairs();
			}
			fulfillTopSourceDestination();

		}
		List<FulfillmentResult> fulfillmentList = new ArrayList<FulfillmentResult>();
		for(String s:fulfillmentMap.keySet()){
			FulfillmentResult result = new FulfillmentResult();
			result.setLocation(s.split(",")[0]);
			result.setSku(s.split(",")[1]);
			result.setDestination(s.split(",")[2]);
			result.setAmount(fulfillmentMap.get(s));
			fulfillmentList.add(result);
		}
		return fulfillmentList;
	}
	private void refreshAllPairs(){
		Map<String,Integer> skuDestination = new HashMap<String,Integer>();
		for(InventoryTarget target:targetList){
			String s = target.getSku()+","+target.getDestination();
			if(skuDestination.containsKey(s)){
				skuDestination.put(s, (skuDestination.get(s) + target.getAmount()));
			}
			else{
				skuDestination.put(s, target.getAmount());
			}

		}
		sourceSkuDestination = new HashMap<String,Integer[]>();
		for(InventorySource source:sourceList){
			for(String s:skuDestination.keySet()){
				if(s.split(",")[0].equals(source.getSku())){
					String ss = source.getLocation()+","+s;
					if(sourceSkuDestination.containsKey(ss)){
						Integer available =  sourceSkuDestination.get(ss)[1] + source.getAmount();
						Integer []amount = new Integer[2];
						amount[0] = available;
						amount[1] = skuDestination.get(s);
						sourceSkuDestination.put(ss, amount);
					}
					else{
						Integer []amount = new Integer[2];
						amount[0] = source.getAmount();
						amount[1] = skuDestination.get(s);
						sourceSkuDestination.put(ss, amount);
					}
				}
			}
		}
	}
	private void findSingleSource() throws Exception{
		Map<String,Integer> uniqueDest = new HashMap<String,Integer>();
		for(String s:sourceSkuDestination.keySet()){
			String ss = s.split(",")[1]+","+s.split(",")[2];
			if(uniqueDest.containsKey(ss)){
				uniqueDest.put(ss, uniqueDest.get(ss)+1);
			}
			else{
				uniqueDest.put(ss, 1);
			}
		}
		for(String s:uniqueDest.keySet()){
			if(uniqueDest.get(s) == 1){
				for(String ss:sourceSkuDestination.keySet()){
					if(s.split(",")[0].equals(ss.split(",")[1]) && s.split(",")[1].equals(ss.split(",")[2])){
						if(sourceSkuDestination.get(ss)[1]<=sourceSkuDestination.get(ss)[0]){

							fulfillSkuDestination(ss);

						}
						else
						{
							throw new Exception("Insufficient Stock");
						}
					}
				}

			}

		}

	}

	private void refreshAvailability(){
		productAvailability = new HashMap<String,Integer>();
		for(InventorySource source: sourceList){
			if(productAvailability.containsKey(source.getSku())){
				productAvailability.put(source.getSku(), productAvailability.get(source.getSku())+source.getAmount());
			}
			else{
				productAvailability.put(source.getSku(), source.getAmount());
			}
		}

	}
	private void fulfillSkuDestination(String ss){
		fulfillmentMap.put(ss, sourceSkuDestination.get(ss)[1]<sourceSkuDestination.get(ss)[0]?sourceSkuDestination.get(ss)[1]:sourceSkuDestination.get(ss)[0]);
		InventoryTarget toRemove = null;
		for(InventoryTarget target:targetList){
			if(target.getSku().equals(ss.split(",")[1]) && target.getDestination().equals(ss.split(",")[2]) ){
				toRemove = target;
			}
		}
		if(null != toRemove){
			targetList.remove(toRemove);
		}
		InventorySource toRemoveS = null;
		for(InventorySource source:sourceList){
			if(source.getSku().equals(ss.split(",")[1]) && source.getLocation().equals(ss.split(",")[0]) ){
				source.setAmount(source.getAmount()-sourceSkuDestination.get(ss)[1]);
			}
			if(source.getAmount() == 0){
				toRemoveS = source;
			}
		}
		if(null != toRemoveS){
			sourceList.remove(toRemoveS);
		}
		if(targetList.size() == 0){
			processActive = false;
		}
		refreshAvailability();
		refreshAllPairs();
		refreshSourceDestinationToBeFulfilled();
	}

	private boolean findMustFulfillSource(){
		for(String s:sourceSkuDestination.keySet()){
			String sku = s.split(",")[1];
			if(sourceSkuDestination.get(s)[1]> (productAvailability.get(sku)- sourceSkuDestination.get(s)[0])){
				fulfillSkuDestination(s);
				return true;
			}
		}
		return false;
	}
	private boolean fulfillMappedPairs(){
		for(String s:sourceSkuDestination.keySet()){
			if(null != mappedPairs.get(s.split(",")[0]) && mappedPairs.get(s.split(",")[0]).equals(s.split(",")[2])){
				fulfillSkuDestination(s);
				return true;
			}
		}
		return false;		
	}

	private void refreshSourceDestinationToBeFulfilled(){
		for(String s:sourceSkuDestination.keySet()){
			String ss = s.split(",")[0]+","+s.split(",")[2];
			Map<String,Integer> sourceDestinationToBeFulfilled = new HashMap<String,Integer>();
			if(sourceDestinationToBeFulfilled.containsKey(ss)){
				sourceDestinationToBeFulfilled.put(ss, sourceDestinationToBeFulfilled.get(ss)+1);
			}
			else{
				sourceDestinationToBeFulfilled.put(ss, 1);
			}

		}
	}
	private void fulfillTopSourceDestination(){
		
		String sourceDestToBeFulfilled = null;
		int currTop = 0;
		for(String s:sourceDestinationToBeFulfilled.keySet()){
			if(sourceDestinationToBeFulfilled.get(s)>currTop){
				currTop = sourceDestinationToBeFulfilled.get(s);
				sourceDestToBeFulfilled = s;
			}
		}
		List<String> toBeFulfilled = new ArrayList<String>();
		for(String s:sourceSkuDestination.keySet()){
			if(s.split(",")[0].equals(sourceDestToBeFulfilled.split(",")[0]) && s.split(",")[2].equals(sourceDestToBeFulfilled.split(",")[1])){
				toBeFulfilled.add(s);
			}
			
		}
		for(String s:toBeFulfilled){
			if(sourceSkuDestination.containsKey(s)){
				fulfillSkuDestination(s);
			}
		}
		
	}

}
