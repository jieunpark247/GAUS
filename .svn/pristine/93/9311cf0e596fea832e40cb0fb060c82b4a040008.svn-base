package com.glab.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.prefs.Preferences;

import com.glab.app.model.DataList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.stage.Stage;




public class RpaController implements Initializable{
	AdbController controller = new AdbController();
	Preferences pref;
	
	@FXML TextField rpa_utterFld;
	@FXML TextField rpa_wordTime;
	@FXML Button rpa_utterBtn;
	@FXML Button rpa_wordBtn;
	@FXML TextArea rpa_utterReArea;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다
		System.out.println("rpa loading ...");
		//path setting 값 받아오기
        pref = Preferences.userNodeForPackage(this.getClass());
        String adb = pref.get("adbPath", null);
        controller.setAdbPath(adb);
        System.out.println("adb path set : "+adb);
     
	}
	
	@FXML
	private void utterAction(ActionEvent event) throws UnknownHostException {
		String result = null;
		String input = null;
		String shotPath = null;
		input = rpa_utterFld.getText().toString();
		input = input.replace(" ", "\\ ");
    	System.out.println("변환 "+input);
		result = controller.utter(input);
		if(result.equals("")) {
			// 스크린 샷
		    shotPath = pref.get("shotPath", " ");
			String resultScreenshot = controller.screenShot(shotPath);
			File file = new File(resultScreenshot);
			String localUrl;
			try {
				localUrl = file.toURI().toURL().toString();
				//Image img = new Image(localUrl);
				//screenShot_imgView.setImage(img);
				result = localUrl;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			
			
			
		}
		rpa_utterReArea.clear();
		rpa_utterReArea.appendText(result);
		rpa_utterReArea.setWrapText(true);
		//TextArea 직접입력 방지
		rpa_utterReArea.setEditable(false);
		rpa_utterReArea.setBackground(Background.EMPTY);
		rpa_utterReArea.setFocusTraversable(false);
	}
	
	@FXML
	private void wordAction(ActionEvent event) {
		ArrayList<String> words;
	//	System.out.println(rpa_wordTime.toString());
		String inputTime = rpa_wordTime.getText().toString();
		if(inputTime.equals("")) {
			System.out.println("--------------------null");
			inputTime="9";
		}
		words = controller.parsingKeyword(inputTime);

	    DataList ap = new DataList();
	    ap.setPack(words);
		try {
            Parent sub = FXMLLoader.load(getClass().getResource("/com/glab/app/view/popUpView.fxml")); // 서브페이지 불러오고
            Stage stage = new Stage();
            stage.setTitle("실시간 검색어 NAVER,DAUM,GOOGLE ");
            stage.setScene(new Scene(sub,600,600));
            stage.setResizable(false);
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
