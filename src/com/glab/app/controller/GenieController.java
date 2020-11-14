package com.glab.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GenieController implements Initializable{
	
	AdbController controller = new AdbController();
	String adbPath = null;
	String selectedIP = null;
	Preferences pref;

	@FXML private Button genie_gTestBtn;
	@FXML private Button genie_devModeBtn;
	@FXML private ComboBox<String> genie_comboApp;
	@FXML private TextField genie_appIdFld;
	@FXML private TextField genie_intentFld;
	@FXML private Button genie_intentBtn;
	@FXML private TextField genie_ttsFld;
	@FXML private Button genie_ttsBtn;
	@FXML private TextField genie_inputFld;
	@FXML private Button genie_inputBtn;
	@FXML private Button genie_delBtn;
	@FXML private Button genie_upBtn;
	@FXML private Button genie_leftBtn;
	@FXML private Button genie_downBtn;
	@FXML private Button genie_rigntBtn;
	@FXML private Button genie_pressBtn;
	@FXML private Button genie_backBtn;
	@FXML private Button genie_kill;
	@FXML private Button genie_devModeoffBtn;
	@FXML private Button micBtn;
	@FXML private Button genie_goBtn;
	@FXML private Button genie_mcConfig;
	@FXML private Button genie_exit;
	
	private ObservableList<String> appList = FXCollections.observableArrayList("101: 팟캐스트 (E5000799)", "102 : 핑크퐁 (E5001176)", "103 : 번역하기(E5001154)", "104 : 파고다생활영어 (E5000788)",
    		"105 : 구구단연습 (E5001339)", "106 :수도맞추기 (E5001340)", "107 : 스피드연산 (E5001341)", "108 : 나라맞추기 (E5001342)", 
    		"109 :우리말퀴즈 (E5001343)", "110 : 숨은단어찾기 (E5001360)", "111 : 넌센스퀴즈 (E5001344)", "112: 단어연상퀴즈 (E5001345)",
    		"113 : 라디오 (E5001346)", "116 : 뉴스브리핑 (E5001347)", "118: 지니운세 (E5000779)", "119 : 야나두영어 (E5001155)", 
    		"120 : 대교동화 (E5001335)", "121 :박명수를이겨라 (E5001628)", "122 : MangoPlate (E5001337)", "123 : SAYPEN(E5001959)",
    		"124 : SERI CEO (E5001972)", "125 : 명상 (E5001338)", "126 : 핑크퐁동화극장 (E5001349)", "127 : 핑크퐁 중국어 (E5001348)", 
    		"128 : 도레미 게임 (E5002242)", "129: 토리코리 세계여행 (E5002241)", "130 : 순간포착 게임 (E5002240)", "131 : 공룡메카드(E5002239)", 
    		"132 : 성경 (E5002238)", "133 : 불교 (E5002237)", "134 : 만개의 레시피(E5002236)", "135 : 핑크퐁 따라말하기 (E5002309)", 
    		"136 : 동화 오디오북 (E5002391)", "137 :소리동화 (E5001219)", "139 : 홈 트레이닝 (E5002488)",
  			"140 : 루틴 (E5002495)", " 팟캐스트 (E5000799)", " 요리 레시피(E5001997) ","145 : 천주교 (E5002937)","g157 : 멀티캠퍼스 (K5003141)","g161 :지니야 놀자(K5004027)",  			
  			"g162 : 생일축하인(K5004028)","직접입력");
 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		System.out.println("genie loading ...");
		//path setting 값 받아오기
        pref = Preferences.userNodeForPackage(this.getClass());
        adbPath = pref.get("adbPath", null);
        controller.setAdbPath(adbPath);
       
		genie_comboApp.setItems(appList); // appId 리스트 등록
		genie_comboApp.getSelectionModel().selectLast();
		
		//appid 초기화
		genie_comboApp.setOnAction((event) -> {
		    String selectedApp = genie_comboApp.getSelectionModel().getSelectedItem().toString();
		    if(selectedApp.equalsIgnoreCase("직접입력")) {
		    	genie_appIdFld.setDisable(false);
		    }else {
		    	genie_appIdFld.setDisable(true);
		    }
		});
		genie_appIdFld.setDisable(false);
		

		
	}
	
	@FXML
	private void gtestAction(ActionEvent event) {
		
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
	    
		String result = null;
		String appId = "E5000634";
		String intent = "callTitle";

		result = controller.devMode();
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}

		result = controller.cmsInput(appId);
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}

		result = controller.cmsCall();
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}

		result = controller.callIntent(appId + "," + intent);
	}
	
	/*
	 * 경고창(alert) 실행
	 */
	void alertOpen(String v1,String v2,String v3) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(v1);
		alert.setHeaderText(v2);
		alert.setContentText(v3);
		alert.showAndWait();
	}
	
	/*
	 * 앱id , 인텐트 입력
	 */
	private void intent(int flag){
        selectedIP = pref.get("selectedIP", null);
        controller.setOneIP(selectedIP);
        
		String appId = " ";
		String intent = " ";
		
		if(genie_comboApp.getSelectionModel().getSelectedItem().toString() == "직접입력"){
			if(genie_appIdFld.getText().toString().contains("K")){
				appId = genie_appIdFld.getText().toString();
			}
			
		}else{
			String selectedApp =  genie_comboApp.getSelectionModel().getSelectedItem().toString();
			String[] word = selectedApp.split("\\(");			//104 : 파고다생활영어 (E5000788) 에서 (로 자르기
			appId = word[1].substring(0,word[1].length()-1);	//문자열 맨 뒷 글자 자르기
		}
		
		intent = genie_intentFld.getText().toString(); 
		System.out.println("appId , intent >> "+appId+" , "+intent);
		controller.exit();
		controller.devModeOff();
		try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace();}
		controller.devMode();
		try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}
		
		//String selectedAppString =  genie_comboApp.getSelectionModel().getSelectedItem().toString();
		if(appId.contains("K")){
			try {
				controller.keyEvent("downBtn");
			//	try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		appId = appId.substring(1,appId.length());
		controller.cmsInput(appId);
	//	try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}
		controller.cmsCall();
		if(flag == 1){
			//try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace();}
			controller.callIntent(appId+","+intent);			
		}
	}
	
	/*
	 * 발화 입력
	 */
	private void tts(){
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
	        
		String result = null;
    	String input = null;
    	
    	input = genie_ttsFld.getText().toString();
    	input = input.replace(" ", "\\ ");

    	result = controller.tts(input);
    	System.out.println(result);
	}
	
	/*
	 * 입력값
	 */
	private void input(){
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
	      
		String result = null;
    	String input = null;
    	
    	input = genie_inputFld.getText().toString(); 
    	result = controller.textInput(input);
    	System.out.println(result);
	}
	
	private void goAppId(){
		selectedIP = pref.get("selectedIP", null);
		controller.goApp(selectedIP);
	}

	
	@FXML
	private void devModeAction(ActionEvent event) {
		selectedIP = pref.get("selectedIP", null);
        controller.setOneIP(selectedIP);
        
		System.out.println(genie_devModeBtn);
		System.out.println(event);
		String result = null;
		result = controller.devMode();
		System.out.println("test"+result);
	}
	
	@FXML
	private void devModeOffAction(ActionEvent event) {
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		
		System.out.println(genie_devModeBtn);
		System.out.println("--->");
		System.out.println(event);
		String result = null;
		result = controller.devModeOff();
		System.out.println("devModeOff"+result);
	}
	
	
	@FXML
    private void intentAction(ActionEvent event){
			goAppId();
		 //intent(1);
    }
	
	

	@FXML
    private void ttsAction(ActionEvent event){
    	tts();
    }
	
	@FXML
    private void inputAction(ActionEvent event){
		input();
    }
	
	@FXML
    private void delAction(ActionEvent event){
		
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
	    
		String result = null;
    	result = controller.textClear();
    	System.out.println(result);
    }
	
	@FXML
    private void upAction(ActionEvent event) throws UnknownHostException{
		
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	result = controller.keyEvent("upBtn");
    	System.out.println(result);
    }
	
	@FXML
    private void leftAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	result = controller.keyEvent("leftBtn");
    	System.out.println(result);
    }
	
	@FXML
    private void downAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	result = controller.keyEvent("downBtn");
    	System.out.println(result);
    }
	
	@FXML
    private void rightAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	result = controller.keyEvent("rightBtn");
    	System.out.println(result);
    }
	
	@FXML
    private void pressAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	result = controller.keyEvent("pressBtn");
    	System.out.println(result);
    }
	
	@FXML
    private void backAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	result = controller.keyEvent("backBtn");
    	System.out.println(result);
    }
	@FXML
	private void killAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
		result = controller.devKill();
		System.out.println(result);
	}
	
	@FXML
	private void micAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
		String result = null;
    	String input = null;
    	
    	input = genie_ttsFld.getText().toString();
    	input = input.replace(" ", "\\ ");

		result = controller.micttsCall(input);
		System.out.println(result);
	}
	
	@FXML
	private void goAction(ActionEvent event) throws UnknownHostException{
	    intent(0);
	}
	
	@FXML
	private void mcConfigAction(ActionEvent event) throws UnknownHostException{
		selectedIP = pref.get("selectedIP", null);
	    controller.setOneIP(selectedIP);
	    
		String result = null;
		controller.mcConfig();
		System.out.println(result);
	}
	
	@FXML
	private void exitAction(ActionEvent event) throws UnknownHostException{
		String result = null;
		controller.exit();
		System.out.println(result);
	}

}
