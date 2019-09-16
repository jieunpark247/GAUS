package com.glab.app;
	
import java.io.IOException;
import java.util.prefs.Preferences;

import com.glab.app.controller.PathController;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	
	Preferences pref = Preferences.userNodeForPackage(PathController.class);
	String adb = pref.get("adbPath", null);
	
	@FXML
    private void pathSetAction() {
   
    	 try {
             Parent sub = FXMLLoader.load(getClass().getResource("/com/glab/app/view/pathSettingView.fxml")); // 서브페이지
             Stage stage = new Stage();
             stage.setTitle("Path Setting");
             stage.setScene(new Scene(sub,600,400));
 			 Image image = new Image("/img/logo.png");            
             stage.getIcons().add(image);
             stage.show();
             
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
	
	
	@Override
    public void init() throws Exception {
        System.out.println(Thread.currentThread().getName() + ": init() GAUS 호출");
    }


	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("view/main_View.fxml"));
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
			primaryStage.setTitle("Gigagenie ADB User Supporting-tool");
			primaryStage.setScene(scene);
			Image image = new Image("/img/logo.png");            
            primaryStage.getIcons().add(image);
			primaryStage.show();
			primaryStage.setResizable(false);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if (adb != null) {
				System.out.println(adb+" ----not null");
			}else {
				pathSetAction();
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
