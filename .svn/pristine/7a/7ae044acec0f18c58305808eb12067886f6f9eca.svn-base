package com.glab.app.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileService {
	
    public void writerAnswer(String filename, String msg) {

    	String path ="C:\\Users\\"+System.getProperty ( "user.name" )+"\\Desktop\\";
        File file = new File(path+filename);	//test.txt
        FileWriter writer = null;
        BufferedWriter bWriter = null;
        BufferedWriter firstBW = null;
        
        try {
        	if(!file.exists()) {
        		System.out.println("파일 생성");
        		firstBW = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "euc-kr"));
        		firstBW.write("결과값");		//제목 생성
        		firstBW.newLine();
        		firstBW.flush();
        	}
            bWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true), "euc-kr"));
            
            msg = appendDQ(msg);	//csv 파일에서 ,구분 안되게 ""로 문자열 묶기

            bWriter.write(msg);
            bWriter.newLine();
            bWriter.flush();

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
            	if(firstBW != null) firstBW.close();
                if(bWriter != null) bWriter.close();
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static String appendDQ(String str) {
        return "\"" + str + "\"";
    }
}