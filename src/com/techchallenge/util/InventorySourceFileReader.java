package com.techchallenge.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.techchallenge.bean.InventorySource;

public class InventorySourceFileReader {
	
	public static List<InventorySource> parseFile(MultipartFile sourcefile) throws Exception{
		
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<InventorySource> sourceList = new ArrayList<InventorySource>();

        try {

            br = new BufferedReader(new InputStreamReader(sourcefile.getInputStream()));
            while ((line = br.readLine()) != null) {
            	
            	if(line.trim().charAt(0) == '#'){
            		continue;
            	}
            	
                String[] columns = line.split(cvsSplitBy);
                InventorySource source = new InventorySource();
                if(columns.length < 3){
                	throw new Exception("Error while parsing the file");
                }
                if(null != columns[0] && !columns[0].trim().isEmpty()){
                	source.setLocation(columns[0].trim());
                }
                else
                {
                	throw new Exception("Error while parsing the file");
                }
                if(null != columns[1] && !columns[1].trim().isEmpty()){
                	source.setSku(columns[1].trim());
                }
                else
                {
                	throw new Exception("Error while parsing the file");
                }
                if(null != columns[2] && !columns[2].trim().isEmpty()){
                	source.setAmount(Integer.parseInt(columns[2].trim()));
                }
                else
                {
                	throw new Exception("Error while parsing the file");
                }
                
                sourceList.add(source);
            }

        } catch (FileNotFoundException e) {
            throw new Exception("Error while parsing the file");
        } catch (IOException e) {
        	throw new Exception("Error while parsing the file");
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	throw new Exception("Error while parsing the file");
		} finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	throw new Exception("Error while parsing the file");
                }
            }
        }
        return sourceList;
	}
}
