package testPackage;
import java.util.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;
import java.io.*;
import java.text.ParseException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVWriter;
class fileConversion{
	public static void main(String args[]) throws ParseException, org.json.simple.parser.ParseException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter:\n1:JSON to CSV convertion\n2:CSV to JSON convertion");
		int ch = Integer.parseInt(sc.nextLine());
		
		switch(ch) {
		case 1:
			JSONParser parser = new JSONParser();
			System.out.println("Enter JSON file path: ");
			String JSONFilePath = (String)sc.nextLine();
			
			System.out.println("Enter path where you want to save your csv file with filename:\n(eg - 'c:\\new folder\\sample.csv'): ");
			String CSVfilePath = (String)sc.nextLine();
			File file = new File(CSVfilePath);
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			String[] header = { "album", "year", "US_peak_chart_post" };
	        writer.writeNext(header);
	        
			try {
				JSONArray a = (JSONArray) parser.parse(new FileReader(JSONFilePath));
	            for(Object o:a) {
	            	JSONObject obj = (JSONObject)o;
	            	String year = (String) String.valueOf(obj.get("year"));
	            	String US_peak_chart_post = (String) String.valueOf(obj.get("US_peak_chart_post"));
	            	String album = (String) obj.get("album");
	            	String[] data = {album,year,US_peak_chart_post};
	            	writer.writeNext(data);
	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			System.out.println("Json to csv file converted successfully...");
			writer.close();
			break;
		case 2:
			System.out.println("Enter CSV file path: ");
			String CSVFilePath = (String)sc.nextLine();
			
			System.out.println("Enter path where you want to save your json file with filename:\n(eg - 'c:\\new folder\\sample.json'): ");
			String JsonFileSavePath = (String)sc.nextLine();
			
			File csvfile = new File(CSVFilePath);
			try {
				CsvSchema csv = CsvSchema.emptySchema().withHeader();
				CsvMapper csvMapper = new CsvMapper();
				MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(csvfile);
				List<Map<?, ?>> list = mappingIterator.readAll();
//		        System.out.println(list);
		        
		        File jsonFile = new File(JsonFileSavePath);
		        FileWriter outputfile2 = new FileWriter(jsonFile);
		        outputfile2.write(list.toString());
		        outputfile2.close();
		        System.out.println("Csv to json file converted successfully...");
			}catch(Exception e) {
		         e.printStackTrace();
		      }
			break;
		default:
			System.out.println("Invalid choice");
		}
	}
}