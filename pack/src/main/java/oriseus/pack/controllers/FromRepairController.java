/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oriseus.pack.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import javafx.beans.property.SimpleStringProperty;
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
    private Text nameText;
    
    @FXML
    private TextField rubPriceTextField;
    @FXML
    private TextField kopPriceTextField;
    
    @FXML
    private Text warningText;
    
    @FXML
    private Button exitButton;
    @FXML
    private Button confirmButton;
    
    private StampView stampView;
    private WindowService windowService;
        
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
    	
    	if (rub == null || rub.isEmpty()) {
    		rub = "0";
    	}
    	
    	if (kop == null || kop.isEmpty()) {
    		kop = "00";
    	}
    	
    	if (kop.length() > 2) {
    		warningText.setText("Пожалуйста укажите коректную сумму");
    		return;
    	}
    	
    	try {
			Integer.parseInt(rub);
			Integer.parseInt(kop);
		} catch (Exception e) {
			warningText.setText("Пожалуйста укажите коректную сумму");
			return;
		}

    	Long price = Long.parseLong(rub + kop);
    	
    	stampView.setAvailability(new SimpleStringProperty("Да"));
    	stampView.setDamaged(new SimpleStringProperty("Нет"));
    	
    	StampDTO stampDTO = ConvertService.convertToStampDTO(stampView);
    	
    	stampDTO.setDamaged(false);
    	stampDTO.setAvailability(true);
    	
    	StampRepairHistoryDTO stampRepairHistoryDTO = new StampRepairHistoryDTO();
    	stampRepairHistoryDTO.setRepairDate(LocalDateTime.now());
    	stampRepairHistoryDTO.setRepairPrice(price);
    	stampRepairHistoryDTO.setStampDTO(stampDTO);
    	
    	HttpService.sendObject(PropertiesService.getProperties("ServerUrl") + "/stamps/update", stampDTO);
    	HttpService.sendObject(PropertiesService.getProperties("ServerUrl") + "/stampRepairHistory/addNewRepairHistory", 
    			stampRepairHistoryDTO);
        
    	HttpService.sendGetRequest(PropertiesService.getProperties("ServerUrl") + "/file/fromRepair", 
    			stampView.getTechnologicalMapName() + PropertiesService.getProperties("TechnicalMapImagesSuffix"), stampView.getName());
    	
        windowService.closeWindow(confirmButton);
    }
    
    @FXML
    private void exit() throws IOException {
	windowService.closeWindow(exitButton);
    }
}
