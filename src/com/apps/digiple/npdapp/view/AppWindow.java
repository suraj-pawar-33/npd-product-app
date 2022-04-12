package com.apps.digiple.npdapp.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppWindow extends Application{

	@Override
	   public void start(Stage stage) {    
	      
	      Scene scene = new Scene(new MainPanel()); 
	      
	      //Setting title to the Stage 
	      stage.setTitle("NPD Data"); 
//	      stage.setFullScreen(true);
	      //Adding scene to the stage 
	      stage.setScene(scene);  
	      
	      //Displaying the contents of the stage 
	      stage.show(); 
	   }
	public void launchApp(String args[]){
		launch(args);
	}
	    
	


}
