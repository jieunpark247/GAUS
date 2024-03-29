package com.glab.app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PathController implements Initializable{
	AdbController controller = new AdbController();
	Preferences pref;
	@FXML TextField path_adbFld;
	@FXML TextField path_shotFld;
	@FXML TextField path_logFld;
	@FXML Button path_savaBtn;
	@FXML Button path_closeBtn;
	
	@Override
    public void initialize(URL arg0, ResourceBundle arg1) {	//컨트롤러 로딩 될때 마다
		System.out.println("path setting loading....");

        pref = Preferences.userNodeForPackage(this.getClass());
        String pre = pref.get("set", null);		//기존에 셋팅된 값 있는지 체크
        
        if(pre == null) {		
        	path_adbFld.setText("C:/GAUS/platform-tools-latest-windows/platform-tools");
    		path_shotFld.setText("C:/GAUS/screenshot");
    		path_logFld.setText("C:/GAUS/log");
    		System.out.println("adb 경로 ::::: " + pref.get("adbPath", null));

        }else if(pre.equalsIgnoreCase("1")){
        	path_adbFld.setText(pref.get("adbPath", null));
    		path_shotFld.setText(pref.get("shotPath", null));
    		path_logFld.setText(pref.get("logPath", null));
    	//	System.out.println("adb : " + pref.get("adbPath", null));
    		System.out.println("adb 경로 ::::: " + pref.get("adbPath", null));
        }
	}
	
	@FXML
    private void saveAction(Event e) {
		String adbPath = path_adbFld.getText().toString();
		String shotPath = path_shotFld.getText().toString();
		String logPath = path_logFld.getText().toString();
		
		adbPath = adbPath.replace("\\", "/");
		shotPath = shotPath.replace("\\", "/");
		logPath = logPath.replace("\\", "/");
		
        pref = Preferences.userNodeForPackage(this.getClass());
        pref.put("set","1");
        pref.put("adbPath",adbPath);
        pref.put("shotPath",shotPath);
        pref.put("logPath",logPath);
        System.out.println("save pref");
        
        Stage stage = (Stage) path_closeBtn.getScene().getWindow();
		stage.close();		//창닫기
	}
	
	@FXML
    private void closeAction(Event e) {
		 Stage stage = (Stage) path_closeBtn.getScene().getWindow();
		 stage.close();
	}
}
