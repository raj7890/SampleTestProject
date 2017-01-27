package com.functional.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonObject;

public class json {

	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		// TODO Auto-generated method stub
	
		        
		    	 JSONParser parser = new JSONParser();

		       
	             Object obj = parser.parse(new FileReader("D:\\SampleTestProject\\SampleTestProject\\test.json"));

	             JSONObject jsonObject = (JSONObject) obj;
	          

	             String name = (String) jsonObject.get("name");
	             System.out.println(name);

	             long age = (Long) jsonObject.get("age");
	             System.out.println(age);
	             
	             jsonObject.put("name", "Raju");
	             System.out.println(jsonObject);
	             
	            
		    }

}