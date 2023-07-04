/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oriseus.pack.dto.StampDTO;
import oriseus.pack.dto.StampRepairHistoryDTO;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

/**
 *
 * @author oriseus
 */
public class FromRepairController {
    
    @FXML
    Text nameText;
    
    @FXML
    TextField rubPriceTextField;
    @FXML
    TextField kopPriceTextField;
    
    @FXML
    Text warningText;
    
    @FXML
    Button exitButton;
    @FXML
    Button confirmButton;
    
    StampView stampView;
    WindowService windowService;
    
    @FXML
    private void initialize() {
        windowService = new WindowService();
        stampView = RootMainController.stampView;
        nameText.setText(stampView.getName());
    }
    
    @FXML
    private void confirm() {
    	String rub = rubPriceTextField.getText();
    	String kop = kopPriceTextField.getText();
    	
    	try {
			Integer.parseInt(rub);
			Integer.parseInt(kop);
		} catch (Exception e) {
			warningText.setText("Пожалуйста укажите коректную сумму");
			return;
		}
    	
    	if (rub == null || rub.isEmpty()) {
    		rub = "0";
    	}
    	
    	if (kop == null || kop.isEmpty()) {
    		kop = "00";
    	}
    	
    	Long price = Long.parseLong(rub + kop);
    	
    	stampView.setAvailability(new SimpleStringProperty("Да"));
    	stampView.setDamaged(new SimpleStringProperty("Нет"));
    	
    	StampDTO stampDTO = ConvertService.convertToStampDTO(stampView);
    	
    	StampRepairHistoryDTO stampRepairHistoryDTO = new StampRepairHistoryDTO();
    	stampRepairHistoryDTO.setRepairDate(LocalDateTime.now());
    	stampRepairHistoryDTO.setRepairPrice(price);
    	stampRepairHistoryDTO.setStampDTO(stampDTO);
    	
    	try {
			HttpService.sendObject(stampRepairHistoryDTO, PropertiesService.getProperties("ServerUrl") + "/stampRepairHistory/addNewRepairHistory", "POST");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        windowService.closeWindow(confirmButton);
    }
    
    @FXML
    private void exit() throws IOException {
	windowService.closeWindow(exitButton);
    }
}
