package com.glab.app.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LogController implements Initializable{
	AdbController controller = new AdbController();
	Preferences pref;
	String adbPath = null;
	String logPath = null;
	
	@FXML Button log_logBtn;
	@FXML Button log_logDelBtn;
	@FXML Button log_logBuddyBtn;
	@FXML Button log_logBuddyOpnBtn;
	@FXML Button log_start;
	@FXML TabPane tablog;
	@FXML
	private Tab genie_tab;
	@FXML
	private Tab buddy_tab;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		System.out.println("log loading ...");
		//path setting 값 받아오기
        pref = Preferences.userNodeForPackage(this.getClass());
        adbPath = pref.get("adbPath", null);
        controller.setAdbPath(adbPath);
        logPath = pref.get("logPath", " ");
        System.out.println("==<"+logPath);
        String dev = pref.get("device_list", null);
        
        SingleSelectionModel<Tab> selectionModel = tablog.getSelectionModel(); //init >> tab 초기 선택 
        if(dev.equals("buddy")) {
        	System.out.println(pref.get("device_list", null) + "====>  buddy");
        	genie_tab.setDisable(true);
        	buddy_tab.setDisable(false);
        	selectionModel.select(buddy_tab);
        }else{
        	System.out.println(pref.get("device_list", null) + "====>  genie");
        	genie_tab.setDisable(false);
        	buddy_tab.setDisable(true);
        	selectionModel.select(genie_tab);
        }

	}
	
	@FXML private void logAction(ActionEvent event) {
		String result = controller.logPrint(logPath);
		result = controller.logOpen(logPath);
	}
	
	@FXML private void logDelAction(ActionEvent event) {
		String result = controller.logDel();
	}
	
	/*
	 * 버디 로그 보기
	 */
	@FXML private void logBuddyAction(ActionEvent event) { 
		controller.adbPush(pref.get("adbPath", null));
		System.out.println("log adb push >>>>>>>>>");
		controller.logBuddy("buddy");
	}
	
	/*
	 * 버디 로그 열기
	 */
	@FXML private void logBuddyOpnAction(ActionEvent event) { 
		
		controller.logBuddyOpen(logPath);
	}
	
	@FXML private void logBuddyStartAction(ActionEvent event) { 
		controller.logBuddyOpen(logPath);
	}
}
