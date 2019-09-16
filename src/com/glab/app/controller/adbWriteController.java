package com.glab.app.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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


public class adbWriteController implements Initializable{
	AdbController controller = new AdbController();

	@FXML
	private TextField input_adb_cmd;
	@FXML
	private Button input_adb;
	@FXML
	private Button clear_adb;
	@FXML
	private TextArea input_result;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		System.out.println("package loading ...");
		Preferences pref =  Preferences.userNodeForPackage(this.getClass());;
		String adb = pref.get("adbPath", null);
		System.out.println(adb);
		controller.setAdbPath(adb); 
		input_result.setEditable(false);

	}

	@FXML
	public void inputAction(ActionEvent e) {
		String input = input_adb_cmd.getText().toString();
		controller.inputAdb(input);
		input_result.setText(controller.inputAdb(input));
	}
	
	@FXML
	public void clearAction(ActionEvent e) {
		System.out.println("---");
		input_result.setText("");
	}
}
