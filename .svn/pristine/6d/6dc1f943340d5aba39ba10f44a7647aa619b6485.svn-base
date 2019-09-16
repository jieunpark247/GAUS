package com.glab.app.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.glab.app.model.DataList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;

public class PopUpController implements Initializable{
	
	@FXML TextArea packInfo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		DataList ap = new DataList();
		ArrayList<String> info = ap.getPack();

		info.forEach(str -> {
			packInfo.appendText(str + "\n");
		});

		packInfo.setEditable(false);
		packInfo.setBackground(Background.EMPTY);
		packInfo.setFocusTraversable(false);
		packInfo.setEditable(false);
	}
}
