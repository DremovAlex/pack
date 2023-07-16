package oriseus.pack.service;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class AlertService {
	
	private AlertService() {}

	public static void showAlertException(String header, String content) {	
		Alert alert = new Alert(AlertType.ERROR);
		
		alert.setWidth(600);
		alert.setHeight(300);
		alert.setTitle("Ошибка");
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();		
	}
	
}
