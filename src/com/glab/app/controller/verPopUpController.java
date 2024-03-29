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

public class verPopUpController implements Initializable{
	
	@FXML TextArea verInfo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) { // 컨트롤러 로딩 될때 마다

		ArrayList<String> info= new ArrayList<String>();
		
		info.add("19.07.01 윈도우 어플리케이션 주요 기능 개발");
		info.add("19.07.03 윈도우 어플리케이션 Beta version 개발 완료");
		info.add("19.07.08 UI 수정");
		info.add("19.07.09 기기 다중연결 방지기능 추가");
		info.add("19.07.12 버디 로그 개발 추가");
		info.add("19.07.15 GiGA Genie UI수정 , kill기능 추가");
		info.add("19.07.16 mic,개발자모드 해제 버튼 추가, log기능 수정, 아이콘 추가 ");
		info.add("19.07.17 UI수정, 기기별 옵션 지정, 새로고침 버튼추가");
		info.add("19.07.18 LTE2 기능 추가 ");
		info.add("19.09~10 adb path 강제 C:/로 추가");
		info.add("19.11.25 발화 enter시 mic버튼과 같은 기능 추가");
		info.add("20.02.04 로그 실행시 가우스 실행 가능하게 추가");
		info.add("20.03.16 AppID K로 시작할때 바로가기 수정 , 개발자모드 해제 수정 ");
		info.add("20.03.18 여러기기 제어 할 수 있는 기능 추가 , 추가 기능 + (한글 변환 , MC패딩서버, app개발자모드  )  ");
		info.forEach(str -> {
			verInfo.appendText(str + "\n");
			System.out.println(str);
		});

		verInfo.setEditable(false);
		verInfo.setBackground(Background.EMPTY);
		verInfo.setFocusTraversable(false);
	}
}
