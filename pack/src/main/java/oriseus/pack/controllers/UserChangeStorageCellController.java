/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

/**
 *
 * @author oriseus
 */
public class UserChangeStorageCellController {
    
    @FXML
    private Text nameText;
    
    @FXML
    private TextField storageCellTextField;
    @FXML
    private Text warningText;
    
    @FXML
    private Button exitButton;
    @FXML
    private Button confirmButton;
    
    private WindowService windowService;
    private StampView stampView;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = UserMainController.stampView;
        
        nameText.setText(stampView.getName());
        storageCellTextField.setText(stampView.getStorageCell());
    }
    
    @FXML
    private void confirm() {
    	
    	if (storageCellTextField.getText().isEmpty()) {
    		warningText.setText("Пожалуйста, введите новый номер ячейки");
    		return;
    	}
    	try {
			Integer.parseInt(storageCellTextField.getText());
		} catch (Exception e) {
			warningText.setText("Пожалуйста, введите коректный номер ячейки");
			return;
		}
    	
        stampView.setStorageCell(new SimpleStringProperty(storageCellTextField.getText()));
        
        HttpService.sendObject(PropertiesService.getProperties("ServerUrl") + "/stamps/update", ConvertService.convertToStampDTO(stampView));
        
        windowService.closeWindow(confirmButton);
    }
    
    @FXML
    private void exit() throws IOException {
        windowService.closeWindow(exitButton);
    }
}
