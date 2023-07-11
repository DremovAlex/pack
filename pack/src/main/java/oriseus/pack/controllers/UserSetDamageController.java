/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import oriseus.pack.App;
import oriseus.pack.dto.StampDTO;
import oriseus.pack.dto.StampDamageHistoryDTO;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.FilesService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

/**
 *
 * @author oriseus
 */
public class UserSetDamageController {
    
    @FXML
    private Button exitButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Button imageButton;
    
    @FXML
    private TextField shiftNumberTextField;
    
    @FXML
    private CheckBox isDamageCheckBox;
    
    @FXML
    private TextArea damageTextArea;
    
    @FXML
    private Text nameText;
    @FXML
    private Text warningText;
    
    private File damagedImage;
    private File tempFile;
    private WindowService windowService;
    private StampView stampView;
    private App app;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = UserMainController.stampView;
        app = new App();
        
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
        LocalDateTime localDateTime = LocalDateTime.now();
        stampDamageHistoryView.setDateOfDamageDetection(new SimpleStringProperty(ConvertService.convertLocalDateTimeStringToString(localDateTime)));
        stampDamageHistoryView.setShift(new SimpleStringProperty(shiftNumberTextField.getText()));
        stampDamageHistoryView.setDescriptionOfDamage(new SimpleStringProperty(damageTextArea.getText())); 
        stampDamageHistoryView.setNameOfTechnicalMap(stampView.getTechnologicalMapName() + ":" + localDateTime);
        
    	StampDTO stampDTO = ConvertService.convertToStampDTO(stampView);
    	stampDTO.setDamaged(isDamageCheckBox.isSelected());
    	StampDamageHistoryDTO stampDamageHistoryDTO = ConvertService.convertToStampDamageHistoryDTO(stampDamageHistoryView);
    	stampDamageHistoryDTO.setStampDTO(stampDTO);
    	
    	
    	
    	HttpService.sendObject(PropertiesService.getProperties("ServerUrl") + "/stampDamageHistory/addNewDamageHistory", 
    			stampDamageHistoryDTO);
        HttpService.sendFile(PropertiesService.getProperties("ServerUrl") + "/file/damagedImageOfTechnicalMap", 
        		tempFile, stampView.getName());
    	
        tempFile.delete();
        
        windowService.closeWindow(confirmButton);
    }
    
    @FXML
    private void exit() throws IOException {
        windowService.closeWindow(exitButton);
    }
    
    @FXML
    private void openImage() throws IOException {
    	damagedImage = HttpService.getFile(PropertiesService.getProperties("ServerUrl") + "/file/damagedImageOfTechnicalMap", 
        		stampView.getTechnologicalMapName() + PropertiesService.getProperties("TechnicalMapImagesSuffix"), stampView.getName());
        
    	tempFile = new File(PropertiesService.getProperties("TempReportFileLocation") + damagedImage.getName());
    	Files.copy(damagedImage.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    	
        app.getHostServices().showDocument(tempFile.toString());
        
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    }
}
