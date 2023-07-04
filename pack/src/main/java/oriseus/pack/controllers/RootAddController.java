package oriseus.pack.controllers;

import java.io.IOException;
import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import oriseus.pack.modelsViews.*;
import oriseus.pack.service.ConvertService;
import oriseus.pack.service.HttpService;
import oriseus.pack.service.PropertiesService;
import oriseus.pack.service.WindowService;

public class RootAddController {

    @FXML
    Button addButton;	
    @FXML
    Button exitButton;
	
	
    @FXML
    TextField nameField;
    @FXML
    TextField storageCellField;
    @FXML
    TextField technologicalMapNameField;
    @FXML
    TextField rubPriceTextField;
    @FXML
    TextField kopPriceTextField;
    
    @FXML
    CheckBox isDamageCheckBox;
    @FXML
    CheckBox isRepairPackCheckBox;
    @FXML
    CheckBox isAvailabilityCheckBox;
    @FXML
    CheckBox isDisposalCheckBox;
    
    @FXML
    TextArea notesTextArea;
    
    @FXML
    Text warningText;
        
    WindowService windowService;
        
    @FXML
    private void initialize() {
        windowService = new WindowService();
    }
	
    @FXML
    private void add() throws IOException {
    	String rub = rubPriceTextField.getText();
    	String kop = kopPriceTextField.getText();
    	
    	if (nameField.getText().isEmpty()) {
    		warningText.setText("Пожалуйста, введите номер клише");
    		return;
    	}
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
    	
        StampView stampView = new StampView();
        stampView.setName(new SimpleStringProperty(nameField.getText()));
        stampView.setStorageCell(new SimpleStringProperty(storageCellField.getText()));
        stampView.setTechnologicalMapName(new SimpleStringProperty(technologicalMapNameField.getText()));
        stampView.setDamaged(new SimpleStringProperty(ConvertService.convertBooleanToString(isDamageCheckBox.isSelected())));
        stampView.setRepairPack(new SimpleStringProperty(ConvertService.convertBooleanToString(isRepairPackCheckBox.isSelected())));
        stampView.setAvailability(new SimpleStringProperty(ConvertService.convertBooleanToString(isAvailabilityCheckBox.isSelected())));
        stampView.setDisposal(new SimpleStringProperty(ConvertService.convertBooleanToString(isDisposalCheckBox.isSelected())));
        stampView.setPrice(new SimpleStringProperty(ConvertService.convertLongToString(price)));
        stampView.setAddingDate(new SimpleStringProperty(ConvertService.convertLocalDateTimeStringToString(LocalDateTime.now())));
        stampView.setNotes(new SimpleStringProperty(notesTextArea.getText()));
		
        HttpService.sendObject(ConvertService.convertToStampDTO(stampView), PropertiesService.getProperties("ServerUrl") + "/stamps/addNewStamp", "POST");
        
        windowService.closeWindow(addButton);
    }
	
    @FXML
    private void exit() throws IOException {
    	windowService.closeWindow(exitButton);
    }
}
