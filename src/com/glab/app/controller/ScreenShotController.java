package com.glab.app.controller;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;

public class ScreenShotController implements Initializable {
	AdbController controller = new AdbController();
	Preferences pref;
	String shotPath = null;
	String selectedIP = null;
	@FXML Button screenShot_shotBtn;
	@FXML ImageView screenShot_imgView;
	@FXML TextArea screenShot_imgpathArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		
		System.out.println("sshot loading ...");
		//path setting 값 받아오기
        pref = Preferences.userNodeForPackage(this.getClass());
        String adb = pref.get("adbPath", null);
        controller.setAdbPath(adb);
        shotPath = pref.get("shotPath", " ");
        System.out.println("adb path set : "+adb);

		Image img = new Image("/com/glab/app/view/defaultImg.png");
		System.out.println("이미지" + img);
		screenShot_imgView.setImage(img);
		screenShot_imgpathArea.setEditable(false);
	}
	
	@FXML private void screenShotAction(ActionEvent event) throws UnknownHostException {
		
		selectedIP = pref.get("selectedIP", null);
		controller.setOneIP(selectedIP);
		
		String result = controller.screenShot(shotPath);
		
		screenShot_imgpathArea.clear();
		screenShot_imgpathArea.appendText(result);
		//TextArea 직접입력 방지
		
		screenShot_imgpathArea.setEditable(false);
		screenShot_imgpathArea.setBackground(Background.EMPTY);
		screenShot_imgpathArea.setFocusTraversable(false);
		
		File file = new File(result);
		String localUrl;
		try {
			localUrl = file.toURI().toURL().toString();
			Image img = new Image(localUrl);
			screenShot_imgView.setImage(img);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}
}
