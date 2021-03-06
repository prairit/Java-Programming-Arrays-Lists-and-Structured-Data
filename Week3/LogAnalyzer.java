import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.FileResource;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     private String dir; 


     public LogAnalyzer() {
    	 records = new ArrayList<LogEntry>();
    	 dir = "C:/Users/Prairit/Documents/Coursera/duke-java-2/data/logs/";
     }
        


     public void readFile(String fname) {
         FileResource fr = new FileResource(dir+fname);
         for (String line : fr.lines()) 
        	 records.add(WebLogParser.parseEntry(line));
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }


     public int countUniqueIPs() {
    	 
         ArrayList<String> unique = new ArrayList<String>();
    	 for (LogEntry le : records) {
    		 String ip = le.getIpAddress();
    		 if (!unique.contains(ip)) unique.add(ip);
    	 }
    	 
    	 return unique.size();
     }
     


     public void printAllHigherThanNum(int num) {
     	System.out.printf("Status>%d:\n",num);
    	 for (LogEntry le : records) {
    		 int status = le.getStatusCode();
    		 if (status > num) System.out.println(le);
    	 }
    	 
     }
     


     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
    	 
    	 ArrayList<String> ipsOneDay = new ArrayList<String>();
    	 
    	 for (LogEntry le : records) {
    		 String leTime = le.getAccessTime().toString();
    		 String leIP = le.getIpAddress();
    		 if(leTime.indexOf(someday)!=-1) 
    			 if (!ipsOneDay.contains(leIP)) ipsOneDay.add(leIP);
    	 }
    	 
    	 return ipsOneDay;
    	 
     }
     


     public int countUniqueIPsInRange(int low, int hi) {
    	 ArrayList<String> unique = new ArrayList<String>(); 
    	 for (LogEntry le : records) {
    		 String ip = le.getIpAddress();
    		 if (!unique.contains(ip)) {
    			 int stat = le.getStatusCode();
    			 if (stat>=low && stat<=hi) unique.add(ip);
    		 }
    	 }
    	 return unique.size();
    	 
     }
     


     public HashMap<String, Integer> countVisitsPerIP() {
    	 HashMap<String, Integer> map = new HashMap<String, Integer>();
    	 
    	 for (LogEntry le : records) {
    		 String ip = le.getIpAddress();
    		 if (!map.keySet().contains(ip)) map.put(ip, 1);
    		 else map.put(ip, map.get(ip)+1);
    	 }
    	 return map;
    	 
     }
     


     private HashMap<String, Integer> countVisitsPerIP(String day) {
    	 HashMap<String, Integer> map = new HashMap<String, Integer>();
    	 
    	 for (LogEntry le : records) {
    		 if (!getDay(le).equals(day)) continue;
    		 
    		 String ip = le.getIpAddress();
    		 if (!map.keySet().contains(ip)) map.put(ip, 1);
    		 else map.put(ip, map.get(ip)+1);
    	 }
    	 return map;
    	 
     }
     


     public int mostNumberVisitsByIP(HashMap<String, Integer> map) {
    	 int maxVisits = 0;
    	 for (int visits : map.values()) 
    		 if (visits > maxVisits) maxVisits = visits;

    	 
    	 return maxVisits;
    	 
     }


     private String getDay(LogEntry le) {
    	 String date = le.getAccessTime().toString();
    	 // Assuming that day is in the same place
    	 return date.substring(4, 10);
     }


     public HashMap<String, ArrayList<String>> iPsForDays() {
    	 HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
    	 
    	 for (LogEntry le : records) {
    		 String day = getDay(le);
    		 String ip = le.getIpAddress();
    		 
    		 if (!map.containsKey(day)) {  
    			 ArrayList<String> list = new ArrayList<String>();
    			 list.add(ip);
    			 map.put(day, list);
    		 }
    		 else {
    			 // ensure unique ips are on the list
    			 if (!map.get(day).contains(ip)) map.get(day).add(ip);
    		 }
    	 }
    	 return map;
     }


     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> map) {
    	 int maxSize = 0;
    	 String most = null;
    	 
    	 for (String day : map.keySet()) {
    		 int size = map.get(day).size();
    		 if (size>maxSize) {
    			 maxSize = size;
    			 most = day;
    		 }
    	 }
    	 return most;
     }
     


     public ArrayList<String> iPsWithMostVisitsOnDay(String day) {
    	 // output
    	 ArrayList<String> list = new ArrayList<String>();
    	 HashMap<String, Integer> visits = countVisitsPerIP(day);
    	 
    	 // find max visits count
    	 int maxCount = 0;
    	 for (int count : visits.values()) 
    		 if (count > maxCount) maxCount = count;
    	 
    	 // fill output list
    	 for (String ip : visits.keySet())
    		 if (visits.get(ip) == maxCount) list.add(ip);
    	 
    	 return list;
     }
     
}