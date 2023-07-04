/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oriseus.pack.dto.StampDTO;
import oriseus.pack.dto.StampDamageHistoryDTO;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

/**
 *
 * @author oriseus
 */
public class UserSetDamageController {
    
    @FXML
    Button exitButton;
    @FXML
    Button confirmButton;
    
    @FXML
    TextField shiftNumberTextField;
    
    @FXML
    CheckBox isDamageCheckBox;
    
    @FXML
    TextArea damageTextArea;
    
    @FXML
    Text nameText;
    @FXML
    Text warningText;
    
    WindowService windowService;
    StampView stampView;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = UserMainController.stampView;
        
        nameText.setText(stampView.getName());
        isDamageCheckBox.setSelected(ConvertService.convertStringToBoolean(stampView.getDamaged()));
    }
    
    @FXML
    private void confirm() throws IOException {
    	
    	if (!isDamageCheckBox.isSelected() || shiftNumberTextField.getText().isEmpty() || damageTextArea.getText().isEmpty()) {
    		warningText.setText("Пожалуйста заполните все поля");
    		return;
    	}
    	try {
    		Integer.parseInt(shiftNumberTextField.getText());
    	} catch (Exception e) {
			warningText.setText("Вы ввели некоректный номер смены");
			return;
		}
            
        StampDamageHistoryView stampDamageHistoryView = new StampDamageHistoryView();
        stampDamageHistoryView.setDateOfDamageDetection(new SimpleStringProperty(ConvertService.convertLocalDateTimeStringToString(LocalDateTime.now())));
        stampDamageHistoryView.setShift(new SimpleStringProperty(shiftNumberTextField.getText()));
        stampDamageHistoryView.setDescriptionOfDamage(new SimpleStringProperty(damageTextArea.getText())); 
        
    	StampDTO stampDTO = ConvertService.convertToStampDTO(stampView);
    	stampDTO.setDamaged(isDamageCheckBox.isSelected());
    	StampDamageHistoryDTO stampDamageHistoryDTO = ConvertService.convertToStampDamageHistoryDTO(stampDamageHistoryView);
    	stampDamageHistoryDTO.setStampDTO(stampDTO);
    	    	
    	HttpService.sendObject(stampDamageHistoryDTO, PropertiesService.getProperties("ServerUrl") + "/stampDamageHistory/addNewDamageHistory", "POST");

        windowService.closeWindow(confirmButton);
    }
    
    @FXML
    private void exit() throws IOException {
        windowService.closeWindow(exitButton);
    }
}
