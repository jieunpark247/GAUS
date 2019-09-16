/**
 * 명칭
 * Fld : Field
 * Btn : Button
 */
package com.glab.app.controller;

import java.awt.TextArea;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController implements Initializable {
	Preferences pref;
	AdbController controller = new AdbController();
	ArrayList<String> packageList = null;
	@FXML
	ListView<String> packList;
	String devInfoName;
	String devlistName;
	TextArea iptext; 
	// 이벤트 실행 컴포넌트
	@FXML
	private Button gnb_connectBtn;
	@FXML
	private Button gnb_disConnectBtn;
	@FXML
	private Button gnb_pathBtn;
	@FXML
	private TextField gnb_ipFld;
	@FXML
	private ChoiceBox<String> gnb_choiceDev;
	@FXML
	private Label snb_devList;
	@FXML
	private Label snb_devInfo;
	@FXML
	private Label madeby;
	@FXML
	private Tab genieTab;
	@FXML
	private Tab packageTab;
	@FXML
	private Tab screenShotTab;
	@FXML
	private Tab logTab;
	@FXML
	private Tab rpaTab;
	@FXML
	private Tab adbTab;
	@FXML
	private Pane geniePane;
	@FXML
	private Pane packagePane;
	@FXML
	private Pane screenShotPane;
	@FXML
	private Pane logPane;
	@FXML
	private Pane rpaPane;
	@FXML
	private Pane adbPane;
	@FXML
	private Button verInfomation;
	@FXML
	private Button refreshBtn;

	private ObservableList<String> devList = FXCollections.observableArrayList("기가지니", "LTE", "Buddy");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		System.out.println("main initialize...");
		// path setting 값 받아오기
		pref = Preferences.userNodeForPackage(this.getClass());
		String adb = pref.get("adbPath", null);

		if (adb != null) {
			controller.setAdbPath(adb);
			System.out.println("adb path set : " + adb);

			gnb_choiceDev.setItems(devList);
			gnb_choiceDev.getSelectionModel().selectFirst();
			packList.setItems(FXCollections.observableArrayList());
			getDeviceList();
		} 

	}
	
	/**
	 * 경고창
	 */
	private void showAlert(String val, String spResult) {
		if (spResult.equals("unable")) {
		 	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("FAIL");
			alert.setHeaderText("FAIL!");
			alert.setContentText(val);
			alert.showAndWait();
		}else if (spResult.equals("devices")) {
			 	Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("디바이스 다중연결");
				alert.setHeaderText("디바이스 1개만 연결해주세요");
				alert.setContentText(val + "해제 요청");
				ButtonType buttonGenie = new ButtonType("기가지니");
				ButtonType buttonUSB = new ButtonType("LTE & BUDDY(USB)");
				alert.getButtonTypes().setAll(buttonGenie, buttonUSB);
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonGenie){
					controller.disConnect();
					getDeviceList();
					showAlert("disConnect ", "");
				} else if (result.get() == buttonUSB) {
					getDeviceList();
				} else {
					getDeviceList();
				}
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
			System.out.println(spResult);
			alert.setTitle("SUCCESS");
			alert.setHeaderText(val + "!");
			alert.setContentText(val + "성공");
			alert.showAndWait();
		}
	}
	
	@FXML
	private void choiceDev(ActionEvent event){
		System.out.println(gnb_choiceDev.getValue());
		if((String)gnb_choiceDev.getValue()== "LTE") { //lte
			screenShotTab.setDisable(true);
			 pref.put("device_list", "lte");	
		}else if((String)gnb_choiceDev.getValue()=="Buddy") { // buddy
			screenShotTab.setDisable(true);
			packageTab.setDisable(true);
			pref.put("device_list", "buddy");
		//	controller.adbPush(pref.get("adbPath", null));
		}else { // g1 ,g2
			screenShotTab.setDisable(false);
			packageTab.setDisable(false);
			 pref.put("device_list", "genie");
		}
	}
	
	
	@FXML
	private void connectAction() {
		String ip = null;
		ip = gnb_ipFld.getText().toString();
		System.out.println(ip);
		//String reg2= "^(\\d{1,3})[.]?(\\d{1,3})[.]?(\\d{1,3})[.]?(\\d{1,5})[:](\\d{0,9})$";
		String reg= "^(\\d{1,3})[.]?(\\d{1,3})[.]?(\\d{1,3})[.]?(\\d{1,5})";
		if(ip.matches(reg)) { // 정규표현식에 부합한다면?
			String result = controller.adbConnect("" + "," + ip); //연결
			String[] spResult= null;
	//		getDeviceList();
			System.out.println(">>>>>>>>>>>devCount : " +devCount());
			if(devCount()==1) {
				//String result = controller.adbConnect("" + "," + ip);
				spResult = result.split("\\s");
				System.out.println("alert>>"+spResult[0]);
				showAlert("Connect ", spResult[0]);
				getDeviceList();
			}else if(devCount()==0) {
				result = controller.adbConnect("" + "," + ip+":5555");
				spResult = result.split("\\s");
				System.out.println("alert>>"+spResult[0]);
				showAlert("Connect ", spResult[0]);
				getDeviceList();
			}else if(devCount()==2) {
				showAlert("디바이스", "devices");
			}
			
		}else{
			 showAlert("IP를 정확히 입력해주세요. ", "unable");
		}

	}

	/**
	 * GiGA Genie adb disconnect 수행
	 */
	@FXML
	private void disConnectAction(ActionEvent event) {
		String result = controller.disConnect();
		getDeviceList();
		showAlert("disConnect ", "");
	}

	@FXML
    private void pathSetAction() {
    	 try {
             Parent sub = FXMLLoader.load(getClass().getResource("/com/glab/app/view/pathSettingView.fxml")); // 서브페이지
             Stage stage = new Stage();
             stage.setTitle("Path Setting");
             stage.setScene(new Scene(sub,600,400));
             stage.show();
             
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

	/*
	 * 디바이스 개수
	 */
	public int devCount(){
		String devList = controller.adbDevices();
		String word[] = devList.split("attached");
		devList = word[1];
		int devCount=0;
		String devL[] = devList.split("\n"); //엔터 기준
		for(int i =0; i<devL.length;i++) {
			if(devL[i].contains("device")){ //디바이스 device 가 있다면
				devCount++;
			}
		}
		return devCount;
	}
	
	public void getDeviceList() {
		String devList = controller.adbDevices();
		System.out.println("dev 목록 >> " + devList);
		
		if(devList.contains("offline")){ //기기에 오프라인글자가 있다면 
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Connect Fail");
			alert.setHeaderText("offline 입니다.");
			alert.setContentText("기가지니 전원을 껐다 켜주세요.");
			alert.showAndWait();
        }else {
			String word[] = devList.split("attached");
			devList = word[1];
			String devListAll = devList.replace("device", ""); 
			String devListAr[] = devListAll.split("\\s");			
			if(devListAr.length == 0) {
				snb_devList.setText("");
			}else {
				snb_devList.setText("※ " +devListAr[1]);
			}
			if(devCount() >= 2) { //디바이스 개수가 2개이상일 때
				showAlert("디바이스", "devices");
			}
			
			if (devList.contains("device")) {
				getDeviceInfo();
			} else {
				snb_devInfo.setText("");
			}
        }
	}
		

	public void getDeviceInfo() {
		String text = null;
		String devInfo = controller.modelInfo();
		String devList = controller.adbDevices();
		System.out.println("dev 정보 >> " + devInfo);
		/*
		 * if(devList.contains("offline")){ //기기에 오프라인글자가 있다면 Alert alert = new
		 * Alert(AlertType.WARNING); alert.setTitle("Connect Fail");
		 * alert.setHeaderText("offline 입니다.");
		 * alert.setContentText("기가지니 전원을 껐다 켜주세요."); alert.showAndWait(); }
		 */
		String[] spDevInfo = devInfo.split("\\s");

		if (devInfo.contains("CT1100")) {
			text = "※ 기가지니1 ( " + spDevInfo[0] + ")";
			gnb_choiceDev.setValue("기가지니");
			packageList = getPackage();
		} else if (devInfo.contains("CT1101")) {
			text = "※ 기가지니2 ( " + spDevInfo[0] + ")";
			packageList = getPackage();
			gnb_choiceDev.setValue("기가지니");
		} else if (devInfo.contains("GPR100")) {
			text = "※ LTE ( " + spDevInfo[0] + ")";
			packageList = getPackage();
			gnb_choiceDev.setValue("LTE");
		} else if(devList.contains("0123456789ABCDEF")){
			gnb_choiceDev.setValue("Buddy");
			text = "※ BUDDY";
		}else {
			text = spDevInfo[0];
		}
		
		snb_devInfo.setText(text);
	}

	public ArrayList<String> getPackage() {
		ArrayList<String> pack = null;
		pack = controller.loadPackage();
		ObservableList<String> observableList = FXCollections.observableList(pack);
		packList.setItems(observableList);
		return pack;
	}

	// tab 변경 처리 함수
	@FXML
	private void genieTabAction(Event e) {
		if (genieTab.isSelected()) {
			System.out.println("genie");
			try {
				Parent tapCont = FXMLLoader.load(getClass().getResource("/com/glab/app/view/genieView.fxml"));
				// genieTab.setContent(tapCont);
				geniePane.getChildren().clear();
				geniePane.getChildren().add(tapCont);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@FXML
	private void packageTabAction(Event e) {
		if (packageTab.isSelected()) {
			System.out.println("packageTab");
			try {
				Parent tapCont = FXMLLoader.load(getClass().getResource("/com/glab/app/view/packageView.fxml"));

				packagePane.getChildren().clear();
				packagePane.getChildren().add(tapCont);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@FXML
	private void screenShotTabAction(Event e) {
		if (screenShotTab.isSelected()) {
			System.out.println("screenShotTab");
			try {
				Parent tapCont = FXMLLoader.load(getClass().getResource("/com/glab/app/view/screenShotView.fxml"));
				screenShotPane.getChildren().clear();
				screenShotPane.getChildren().add(tapCont);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@FXML
	private void logTabAction(Event e) {
		if (logTab.isSelected()) {
			System.out.println("logTab");
			try {
				Parent tapCont = FXMLLoader.load(getClass().getResource("/com/glab/app/view/logView.fxml"));
				logPane.getChildren().clear();
				logPane.getChildren().add(tapCont);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	@FXML
	private void rpaTabAction(Event e) {
		if (rpaTab.isSelected()) {
			System.out.println("rpaTab");
			try {
				Parent tapCont = FXMLLoader.load(getClass().getResource("/com/glab/app/view/rpaView.fxml"));
				rpaPane.getChildren().clear();
				rpaPane.getChildren().add(tapCont);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	@FXML
	private void adbTabAction(Event e) {
		if (adbTab.isSelected()) {
			System.out.println("adbWrite");
			try {
				Parent tapCont = FXMLLoader.load(getClass().getResource("/com/glab/app/view/adbWriteView.fxml"));
				adbPane.getChildren().clear();
				adbPane.getChildren().add(tapCont);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
	
	@FXML
	private void versionInfo(Event ev) {
		try {
			Parent sub = FXMLLoader.load(getClass().getResource("/com/glab/app/view/verPopUpView.fxml")); // 서브페이지 불러오고
			Stage stage = new Stage();
			stage.setTitle("Version Infomation");
			stage.setScene(new Scene(sub, 600, 600));
			stage.show();

		} catch (IOException event) {
			event.printStackTrace();
		}
		
	}
	@FXML
	private void refreshAction(Event ev) {
		getDeviceList();
	}
}