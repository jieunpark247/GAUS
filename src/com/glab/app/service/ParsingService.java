package com.glab.app.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParsingService {
	
	/**
	 * 검색어를 파싱
	 * @param args
	 * @throws Exception
	 */
    public ArrayList<String> parsingKeyword(String t) throws Exception {
        System.out.println("@@keyword reading start.@@  >> ");
        ArrayList<String> result = new ArrayList<String>();    
        String inputValue = t;
      
        //get html
        String url = "http://rank.ezme.net/diff";
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
       Elements elems = doc.select(".mdl-data-table");
       Element elemtbody = elems.select("tbody").get(0);
       int curTime = Integer.parseInt((elemtbody.select("tr.bg_info").first()).select("td").first().text().replace("시", ""));//가장 최근 시간
      

       //get input value
       List<Integer> list = queryString(curTime, inputValue);


       //parseHTML
       Set<String> realTimeSet = new HashSet<String>();
       for(int i=0;i<list.size();i++){
    	   if(i==0) {
	           realTimeSet= parseHTML(curTime, list.get(i), elemtbody, realTimeSet);
	           System.out.println("parseHTML : " + realTimeSet.size() + ", " + realTimeSet.toString());
    	   }else {
               realTimeSet= removeAll(curTime, list.get(i), elemtbody, realTimeSet);
	           System.out.println("removeAll : " + realTimeSet.size() + ", " + realTimeSet.toString());
    	   }
       }


       Iterator<String> iterator = realTimeSet.iterator();
       for(int i=0;i<realTimeSet.size();i++) {
			result.add(iterator.next());
		}

    //   result= realTimeSet;
        return result;

    }
    
    
    
    //input value 처리 function
    public static List<Integer> queryString(int curTime, String inputValue){
        
        List<Integer> list = new ArrayList<Integer>();
        
        if(inputValue == null || inputValue == ""){
            return list;
        }
        
        
        String[] sql = inputValue.split(" ");
        
        for(int i=0; i< sql.length; i++){
            if(curTime < Integer.parseInt(sql[i])){
                continue;
            }else{
                list.add(Integer.parseInt(sql[i]));
            }
        }
        
        return list;
    }
    
    //웹 크롤링 function
    public static Set<String> parseHTML(int curTime, int inputTime, Element elemtbody, Set<String> realTimeSet){
        Element elemtr = null;

        
        int timetable = (curTime - inputTime) * 10 ;
       int j = 0;
       for(int i=0 ; i<10 ; i++){
           j = i+timetable;
           elemtr = elemtbody.select("tr").get(j);
           if(j % 10 == 0){
               System.out.println(">> "+ elemtr.select("td").get(0).text());
             
               realTimeSet.add(elemtr.select("td").get(2).text());
               realTimeSet.add(elemtr.select("td").get(3).text());
               realTimeSet.add(elemtr.select("td").get(4).text());
               
           }else{
      		
                realTimeSet.add(elemtr.select("td").get(1).text());
                realTimeSet.add(elemtr.select("td").get(2).text());
                realTimeSet.add(elemtr.select("td").get(3).text());
           }
       }
        
        return realTimeSet;
    }
    
    
    //웹 크롤링 function
    public static Set<String> removeAll(int curTime, int inputTime, Element elemtbody, Set<String> realTimeSet){
    	Element elemtr = null;
    	
    	
    	int timetable = (curTime - inputTime) * 10 ;
    	int j = 0;
    	for(int i=0 ; i<10 ; i++){
    		j = i+timetable;
    		elemtr = elemtbody.select("tr").get(j);
    		if(j % 10 == 0){
    			System.out.println(">> " +elemtr.select("td").get(0).text());
    		
    			realTimeSet.remove(elemtr.select("td").get(2).text());
    			realTimeSet.remove(elemtr.select("td").get(3).text());
    			realTimeSet.remove(elemtr.select("td").get(4).text());
    			
    		}else{
    			realTimeSet.remove(elemtr.select("td").get(1).text());
    			realTimeSet.remove(elemtr.select("td").get(2).text());
    			realTimeSet.remove(elemtr.select("td").get(3).text());
    		}
    	}
    	
    	return realTimeSet;
    }

}
