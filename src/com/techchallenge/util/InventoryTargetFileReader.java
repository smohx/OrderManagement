package com.techchallenge.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.techchallenge.bean.InventoryTarget;

public class InventoryTargetFileReader {
	
	public static List<InventoryTarget> parseFile(MultipartFile targetfile) throws Exception{
		
		String csvFile = "input/InventoryTarget.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<InventoryTarget> targetList = new ArrayList<InventoryTarget>();

        try {

            br = new BufferedReader(new InputStreamReader(targetfile.getInputStream()));
            while ((line = br.readLine()) != null) {
            	
            	if(line.trim().charAt(0) == '#'){
            		continue;
            	}
            	
                String[] columns = line.split(cvsSplitBy);
                InventoryTarget target = new InventoryTarget();
                if(columns.length < 3){
                	throw new Exception("Error while parsing the file");
                }
                if(null != columns[0] && !columns[0].trim().isEmpty()){
                	target.setDestination(columns[0].trim());
                }
                else
                {
                	throw new Exception("Error while parsing the file");
                }
                if(null != columns[1] && !columns[1].trim().isEmpty()){
                	target.setSku(columns[1].trim());
                }
                else
                {
                	throw new Exception("Error while parsing the file");
                }
                if(null != columns[2] && !columns[2].trim().isEmpty()){
                	target.setAmount(Integer.parseInt(columns[2].trim()));
                }
                else
                {
                	throw new Exception("Error while parsing the file");
                }
                
                targetList.add(target);
            }

        } catch (FileNotFoundException e) {
        	throw new Exception("Error while parsing the file");
        } catch (IOException e) {
        	throw new Exception("Error while parsing the file");
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	throw new Exception("Error while parsing the file");
                }
            }
        }
        return targetList;
	}
}
