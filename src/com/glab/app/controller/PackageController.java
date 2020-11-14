package com.glab.app.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.glab.app.model.DataList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class PackageController implements Initializable {

	AdbController controller = new AdbController();
	String selectedIP = null;
	@FXML
	private ListView<String> package_list;
	@FXML
	private TextField searchval;
	@FXML
	private Button searchall;
	@FXML
	private Button search;
	Preferences pref =  Preferences.userNodeForPackage(this.getClass());
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		System.out.println("package loading ...");
		String adb = pref.get("adbPath", null);
		System.out.println(adb);
		controller.setAdbPath(adb); // 하드코딩
		
	
		
		
		package_list.setItems(FXCollections.observableArrayList()); // 초기화
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/glab/app/view/main_View.fxml"));
			Parent root = loader.load();
			ListView<String> temp = (ListView) loader.getNamespace().get("packList");
			package_list.setItems(temp.getItems());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	public void handleListClick(MouseEvent arg0) {		
		selectedIP = pref.get("selectedIP", null);
		controller.setOneIP(selectedIP);
		
		System.out.println("clicked on " + package_list.getSelectionModel().getSelectedItem());
		String packName = package_list.getSelectionModel().getSelectedItem();
			if(packName != null) {
			ArrayList<String> info = controller.dumpSysPackage(packName);
	
			DataList ap = new DataList();
			ap.setPack(info);
	
			try {
				Parent sub = FXMLLoader.load(getClass().getResource("/com/glab/app/view/popUpView.fxml")); // 서브페이지 불러오고
				Stage stage = new Stage();
				stage.setTitle(packName);
				stage.setScene(new Scene(sub, 600, 600));
			    stage.setResizable(false);
				stage.show();
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 검색
	@FXML
	private void searchAction(ActionEvent event) {
		
		selectedIP = pref.get("selectedIP", null);
		controller.setOneIP(selectedIP);
		
		package_list.setItems(FXCollections.observableArrayList()); // 초기화
		String packName = searchval.getText().toString(); 
		Preferences pref =  Preferences.userNodeForPackage(this.getClass());
		String adb = pref.get("adbPath", null);
		controller.setAdbPath(adb);
		ArrayList<String> pack = null;
		pack = controller.searchPackage(packName);
		ObservableList<String> observableList = FXCollections.observableList(pack);
		package_list.setItems(FXCollections.observableArrayList());
		package_list.setItems(observableList);
			
	}

	// 전체 검색
	@FXML
	private void searchAllAction(ActionEvent event) {
		selectedIP = pref.get("selectedIP", null);
		controller.setOneIP(selectedIP);
		
		Preferences pref =  Preferences.userNodeForPackage(this.getClass());
		String adb = pref.get("adbPath", null);
		System.out.println(adb);
		controller.setAdbPath(adb); // 하드코딩
		
		package_list.setItems(FXCollections.observableArrayList()); // 초기화
		ArrayList<String> pack = null;
		pack = controller.loadPackage();
		ObservableList<String> observableList = FXCollections.observableList(pack);
		package_list.setItems(observableList);
		
	}
	


}
